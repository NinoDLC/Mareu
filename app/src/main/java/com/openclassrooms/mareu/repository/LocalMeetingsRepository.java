package com.openclassrooms.mareu.repository;

import android.util.Log;

import androidx.annotation.Nullable;

import com.openclassrooms.mareu.model.Meeting;
import com.openclassrooms.mareu.model.MeetingRoom;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LocalMeetingsRepository implements MeetingsRepository {

    private final List<MeetingRoom> mMeetingRooms = DummyMeetingRoomsGenerator.getDummyMeetingRooms();

    private int mNextMeetingId = 0;

    private List<Meeting> mMeetings = new ArrayList<>();

    public LocalMeetingsRepository(){
        while (mMeetings.size() < 20){
            createMeeting(DummyMeetingGenerator.generateMeeting());  // runs isValidMeeting()
        }
    }

    public List<MeetingRoom> getMeetingRooms(){
        return mMeetingRooms;
    }

    public List<Meeting> getMeetings(){
        return mMeetings;
    }

    public int getNextMeetingId(){
        mNextMeetingId++;
        return mNextMeetingId;
    }

    @Nullable
    public Meeting getMeetingById(int id){
        for (Meeting meeting : mMeetings) {
            if (meeting.getId() == id)
                return meeting;
        }
        return null;
    }

    @Nullable
    public MeetingRoom getMeetingRoomById(int id){
        for (MeetingRoom meetingRoom : mMeetingRooms) {
            if (meetingRoom.getId() == id)
                return meetingRoom;
        }
        return null;
    }

    // TODO add 'throws exception', or return boolean ?
    public void createMeeting(Meeting meeting){
        if(isValidMeeting(meeting))
            mMeetings.add(meeting);
    }

    public void removeMeetingById(int meetingId){
        // not using getMeetingById to be gentler and use that iterator for loop.
        for (Iterator<Meeting> iterator = mMeetings.iterator(); iterator.hasNext(); ) {
            Meeting meeting = iterator.next();
            if (meeting.getId() == meetingId) {
                mMeetings.remove(meeting);
            }
        }
    }

    // todo veut-on retourner des id de meetings ?
    public List<Meeting> getRoomMeetings(int roomId){
        List<Meeting> meetings = new ArrayList<>();
        for (Meeting meeting : mMeetings){
            if (meeting.getMeetingRoomId()==roomId)
                meetings.add(meeting);
        }
        return meetings;
    }

    public boolean isRoomFree(int meetingRoomId, LocalDateTime start, LocalDateTime stop){
        for (Meeting meeting : getRoomMeetings(meetingRoomId)){
            if (meeting.getStart().isBefore(stop) && meeting.getStop().isAfter(start))
                return false;
        }
        return true;
    }

    public List<Integer> getFreeRooms(LocalDateTime start, LocalDateTime stop){
        List<Integer> roomIds = new ArrayList<>();
        for (MeetingRoom room : mMeetingRooms){
            if (isRoomFree(room.getId(), start, stop))
                roomIds.add(room.getId());
        }
        return roomIds;
    }

    public boolean isValidMeeting(Meeting meeting){
        for (Meeting existentMeeting : mMeetings){
            if (existentMeeting.getId() == meeting.getId()){
                Log.e("Utils", "isValidMeeting(): duplicate id");
                return false;
            }
        }
        if (meeting.getStop().isBefore(meeting.getStart())) {
            Log.e("Utils", "isValidMeeting(): can't stop before starting");
            return false;
        }
        if (!isRoomFree(meeting.getMeetingRoomId(), meeting.getStart(), meeting.getStop())){
            Log.e("Utils", "isValidMeeting(): room is not free");
            return false;
        }
        for (String email : meeting.getParticipants()){
            if (email.equals(meeting.getOwner())){
                Log.e("Utils", "isValidMeeting(): meeting owner also in participants");
                return false;
            }
        }
        if (meeting.getParticipants().size() > meeting.getMeetingRoomId()) {
            Log.e("Utils", "isValidMeeting(): meeting room is too small");
            return false;
        }
        return true;
    }
}
