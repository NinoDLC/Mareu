package com.openclassrooms.mareu.repository;

import android.util.Log;

import androidx.annotation.Nullable;

import com.openclassrooms.mareu.model.Meeting;
import com.openclassrooms.mareu.model.MeetingRoom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class LocalMeetingsRepository implements MeetingsRepository {

    private final HashMap<Integer, MeetingRoom> mMeetingRooms = DummyMeetingRoomsGenerator.getDummyMeetingRooms();

    private int mNextMeetingId;

    private final List<Meeting> mMeetings = new ArrayList<>();

    public LocalMeetingsRepository() {
        while (mMeetings.size() < 20) {
            // createMeeting() runs isValidMeeting(), returns boolean
            createMeeting(DummyMeetingGenerator.generateMeeting());
        }
        mNextMeetingId = mMeetings.get(mMeetings.size() - 1).getId();
        sortMeetings();
    }

    private void sortMeetings() {
        Collections.sort(mMeetings, Meeting::compareTo);
    }

    public HashMap<Integer, MeetingRoom> getMeetingRooms() {
        return mMeetingRooms;
    }

    public List<Meeting> getMeetings() {
        return mMeetings;
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

    /*
     * @Inherit
     */
    public boolean createMeeting(Meeting meeting) {
        if (!isValidMeeting(meeting)) return false;
        if (getMeetingById(meeting.getId()) != null) return false;
        if (!mMeetings.add(meeting)) return false;
        sortMeetings();
        return true;
    }

    public void removeMeetingById(int meetingId) {
        // not using getMeetingById to be gentler and use that iterator for loop.
        for (Iterator<Meeting> iterator = mMeetings.iterator(); iterator.hasNext(); ) {
            Meeting meeting = iterator.next();
            if (meeting.getId() == meetingId)
                iterator.remove();
        }
    }

    // Opinionated choice: return Meeting objects, not ids
    private List<Meeting> getRoomMeetings(int roomId) {
        List<Meeting> meetings = new ArrayList<>();
        for (Meeting meeting : mMeetings) {
            if (meeting.getMeetingRoomId() == roomId)
                meetings.add(meeting);
        }
        return meetings;
    }

    private boolean isRoomFree(int meetingRoomId, Meeting meeting) {
        for (Meeting m : getRoomMeetings(meetingRoomId)) {
            if (m.getStart().isBefore(meeting.getStop())
                    && m.getStop().isAfter(meeting.getStart())
                    && meeting.getId() != m.getId()
            ) return false;
        }
        return true;
    }

    public List<Integer> getFreeRooms(Meeting meeting) {
        List<Integer> roomIds = new ArrayList<>();
        int seats = meeting.getParticipants().size() + 1;  // to account for owner also
        for (MeetingRoom room : mMeetingRooms.values()) {
            if (isRoomFree(room.getId(), meeting) && room.getCapacity() >= seats)
                roomIds.add(room.getId());
        }
        return roomIds;
    }

    public boolean isValidMeeting(Meeting meeting) {
        if (meeting.getStop().isBefore(meeting.getStart())) {
            Log.e("Utils", "isValidMeeting(): can't stop before starting");
            return false;
        }
        if (!isRoomFree(meeting.getMeetingRoomId(), meeting)) {
            Log.e("Utils", "isValidMeeting(): room is not free");
            return false;
        }
        for (String email : meeting.getParticipants()) {
            if (email.equals(meeting.getOwner())) {
                Log.e("Utils", "isValidMeeting(): meeting owner also in participants");
                return false;
            }
        }
        MeetingRoom meetingRoom = mMeetingRooms.get(meeting.getMeetingRoomId());
        if (meetingRoom == null) {
            Log.e("Utils", "isValidMeeting(): nonexistent meeting room");
            return false;
        }
        if (meeting.getParticipants().size() > meetingRoom.getCapacity()) {
            Log.e("Utils", "isValidMeeting(): meeting room is too small");
            return false;
        }
        return true;
    }
}
