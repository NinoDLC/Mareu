package com.openclassrooms.mareu.repository;

import android.provider.ContactsContract;
import android.util.Log;

import androidx.annotation.Nullable;

import com.openclassrooms.mareu.model.Meeting;
import com.openclassrooms.mareu.model.MeetingRoom;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class LocalMeetingsRepository implements MeetingsRepository {

    private int mNextMeetingRoomId = 0;

    private int mNextMeetingId = 0;

    private List<MeetingRoom> mMeetingRooms = new ArrayList<MeetingRoom>();

    private List<Meeting> mMeetings = new ArrayList<Meeting>();


    public List<MeetingRoom> getMeetingRooms(){
        return mMeetingRooms;
    }

    public List<Meeting> getMeetings(){
        return mMeetings;
    }

    // TODO add 'throws exception', or return boolean ?
    public void createMeeting(Meeting meeting){
        if(isValidMeeting(meeting))
            mMeetings.add(meeting);
    }

    public void removeMeetingById(int meetingId){
        for (Iterator<Meeting> iterator = mMeetings.iterator(); iterator.hasNext(); ) {
            Meeting meeting = iterator.next();
            if (meeting.getId() == meetingId) {
                mMeetings.remove(meeting);
            }
        }
    }

    // todo veut-on retourner des id de meetings ?
    public List<Meeting> getRoomMeetings(int roomId){
        List<Meeting> meetings = new ArrayList<Meeting>();
        for (Meeting meeting : mMeetings){
            if (meeting.getMeetingRoomId()==roomId)
                meetings.add(meeting);
        }
        return meetings;
    }

    public boolean isRoomFree(int meetingRoomId, Date start, Date stop){
        for (Meeting meeting : getRoomMeetings(meetingRoomId)){
            if (meeting.getStart().before(stop) && meeting.getStop().before(start))
                return false;
        }
        return true;
    }

    public List<Integer> getFreeRooms(Date start, Date stop){
        List<Integer> roomIds = new ArrayList<Integer>();
        for (MeetingRoom room : mMeetingRooms){
            int roomId = room.getId();
            if (isRoomFree(roomId, start, stop))
                roomIds.add(roomId);
        }
        return roomIds;
    }

    boolean isValidMeeting(Meeting meeting){
        for (Meeting existentMeeting : mMeetings){
            if (existentMeeting.getId() == meeting.getId()){
                Log.e("Utils", "isValidMeeting(): duplicate id");
                return false;
            }
        }
        if (meeting.getStop().before(meeting.getStart())) {
            Log.e("Utils", "isValidMeeting(): can't stop before starting");
            return false;
        }
        if (!isRoomFree(meeting.getMeetingRoomId(), meeting.getStart(), meeting.getStop())){
            Log.e("Utils", "isValidMeeting(): room is not free");
            return false;
        }
        for (ContactsContract.CommonDataKinds.Email email : meeting.getParticipants()){
            if (email == meeting.getOwner()){
                Log.e("Utils", "isValidMeeting(): meeting owner also in participants");
                return false;
            }
        }
        return true;
    }




}
