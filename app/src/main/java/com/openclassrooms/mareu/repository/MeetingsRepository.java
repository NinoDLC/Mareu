package com.openclassrooms.mareu.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.openclassrooms.mareu.model.Meeting;
import com.openclassrooms.mareu.model.MeetingRoom;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class MeetingsRepository {

    private int mNextMeetingId;

    private final List<Meeting> mMeetings = new ArrayList<>();

    private final MutableLiveData<List<Meeting>> mMeetingListMutableLiveData = new MutableLiveData<>();

    public String tolog(Meeting meeting) {
        return "new Meeting(" + meeting.getId() +
                ", \"" + meeting.getOwner() + '"' +
                ", " + fromHashSet(meeting.getParticipants()) +// todo replace this with print hashset
                ", \"" + meeting.getSubject() + '"' +
                ", " + fromLocaldateTime(meeting.getStart()) +
                ", " + fromLocaldateTime(meeting.getStop()) +
                ", MeetingRoom.values()[" + meeting.getRoom().ordinal() + "]" +
                ");";
    }

    public String fromLocaldateTime(LocalDateTime ldt){
        return "LocalDateTime.of(" +
                ldt.getYear() +
                ", " + ldt.getMonthValue() +
                ", " + ldt.getDayOfMonth() +
                ", " + ldt.getHour() +
                ", " + ldt.getMinute() +
                ", " + ldt.getSecond() +
                ')';
    }

    public String fromHashSet(Set<String> set){
        if (set.isEmpty()) return "new HashSet<>()";
        String string = "new HashSet<>(Arrays.asList(";
        for (String sub : set)
            string = string.concat('"' + sub + "\", ");
        return string.substring(0, string.length() - 2).concat("))");
    }


    public MeetingsRepository() {
        while (mMeetings.size() < 20) {
            // createMeeting() runs isValidMeeting(), returns boolean
            createMeeting(DummyMeetingGenerator.generateMeeting());
            // todo: this loop updates the livedata 20+ times at launch
        }
        Collections.sort(mMeetings, (o1, o2) -> Integer.compare(o1.getId(), o2.getId()));

        for (Meeting meeting : mMeetings)
            Log.e("Arnaud", tolog(meeting));

        mNextMeetingId = mMeetings.get(mMeetings.size() - 1).getId();
        sortMeetings();
    }

    private void sortMeetings() {
        Collections.sort(mMeetings, Meeting::compareTo);
    }

    public LiveData<List<Meeting>> getMeetings() {
        return mMeetingListMutableLiveData;
    }

    public int getNextMeetingId() {
        return ++mNextMeetingId;
    }

    @Nullable
    public Meeting getMeetingById(int id) {
        for (Meeting meeting : mMeetings) {
            if (meeting.getId() == id)
                return meeting;
        }
        return null;
    }

    public boolean createMeeting(@NonNull Meeting meeting) {
        if (!isValidMeeting(meeting)) return false;
        if (getMeetingById(meeting.getId()) != null) return false;
        if (!mMeetings.add(meeting)) return false;
        sortMeetings();
        mMeetingListMutableLiveData.setValue(mMeetings);
        return true;
    }

    // not using getMeetingById to be gentler and use that iterator for loop.
    public void removeMeetingById(int meetingId) {
        for (Iterator<Meeting> iterator = mMeetings.iterator(); iterator.hasNext(); ) {
            Meeting meeting = iterator.next();
            if (meeting.getId() == meetingId)
                iterator.remove();
        }
        mMeetingListMutableLiveData.setValue(mMeetings);
    }

    // Opinionated choice: return Meeting objects, not ids
    private List<Meeting> getRoomMeetings(MeetingRoom room) {
        List<Meeting> meetings = new ArrayList<>();
        for (Meeting meeting : mMeetings) {
            if (meeting.getRoom() == room)
                meetings.add(meeting);
        }
        return meetings;
    }

    private boolean isRoomFree(MeetingRoom room, Meeting meeting) {
        for (Meeting m : getRoomMeetings(room)) {
            if (m.getStart().isBefore(meeting.getStop())
                    && m.getStop().isAfter(meeting.getStart())
                    && meeting.getId() != m.getId()
            ) return false;
        }
        return true;
    }

    public List<MeetingRoom> getValidRooms(@NonNull Meeting meeting) {
        int seats = meeting.getParticipants().size() + 1;  // account for owner also
        int smallestFittingCapacity = Integer.MAX_VALUE;
        List<MeetingRoom> list = new ArrayList<>();

        for (MeetingRoom room : MeetingRoom.values()) {
            if (isRoomFree(room, meeting) && room.getCapacity() >= seats)
                if (room.getCapacity() < smallestFittingCapacity) {
                    smallestFittingCapacity = room.getCapacity();
                    list.add(0, room);
                } else list.add(room);
        }
        return list;
    }

    public boolean isValidMeeting(@NonNull Meeting meeting) {
        if (meeting.getStop().isBefore(meeting.getStart())) {
            Log.e("Utils", "isValidMeeting(): can't stop before starting");
            return false;
        }
        if (meeting.getRoom() == null) {
            Log.e("Utils", "isValidMeeting(): no meeting room set");
            return false;
        }
        if (!isRoomFree(meeting.getRoom(), meeting)) {
            Log.e("Utils", "isValidMeeting(): room is not free");
            return false;
        }
        for (String email : meeting.getParticipants()) {
            if (email.equals(meeting.getOwner())) {
                Log.e("Utils", "isValidMeeting(): meeting owner also in participants");
                return false;
            }
        }
        if (meeting.getRoom() == null) {
            Log.e("Utils", "isValidMeeting(): nonexistent meeting room");
            return false;
        }
        if (meeting.getParticipants().size() > meeting.getRoom().getCapacity()) {
            Log.e("Utils", "isValidMeeting(): meeting room is too small");
            return false;
        }
        return true;
    }
}
