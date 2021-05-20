package com.openclassrooms.mareu.repository;

import com.openclassrooms.mareu.model.Meeting;
import com.openclassrooms.mareu.model.MeetingRoom;

import java.util.Date;
import java.util.List;

public interface MeetingsRepository {

    List<MeetingRoom> getMeetingRooms();

    List<Meeting> getMeetings();

    List<Integer> getFreeRooms(Date start, Date stop);

    boolean isRoomFree(int MeetingRoomId, Date start, Date stop);

    public List<Meeting> getRoomMeetings(int roomId);

    // TODO add 'throws esception' ?
    void createMeeting(Meeting meeting);

}
