package com.openclassrooms.mareu.repository;

import androidx.annotation.Nullable;

import com.openclassrooms.mareu.model.Meeting;
import com.openclassrooms.mareu.model.MeetingRoom;

import java.util.HashMap;
import java.util.List;

public interface MeetingsRepository {

    HashMap<Integer, MeetingRoom> getMeetingRooms();

    // TODO : use livedata's in repo !!
    List<Meeting> getMeetings();

    int getNextMeetingId();

    @Nullable
    Meeting getMeetingById(int id);

    /*
     * returns false if meeting could not be created
     * (meetings are only added if valid)
     */
    boolean createMeeting(Meeting meeting);

    void removeMeetingById(int meetingId);

    // todo : do those last two have their place in the repo?
    List<Integer> getFreeRooms(Meeting meeting);

    boolean isValidMeeting(Meeting meeting);
}
