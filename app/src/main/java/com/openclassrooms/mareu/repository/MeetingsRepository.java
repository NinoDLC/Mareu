package com.openclassrooms.mareu.repository;

import androidx.annotation.Nullable;

import com.openclassrooms.mareu.model.Meeting;
import com.openclassrooms.mareu.model.MeetingRoom;

import java.util.Date;
import java.util.List;

public interface MeetingsRepository {

    List<MeetingRoom> getMeetingRooms();

    List<Meeting> getMeetings();


    @Nullable
    public Meeting getMeetingById(int id);

    @Nullable
    public MeetingRoom getMeetingRoomById(int id);

    // TODO add 'throws esception' ?
    void createMeeting(Meeting meeting);

    void removeMeetingById(int meetingId);

    List<Meeting> getRoomMeetings(int roomId);

    boolean isRoomFree(int MeetingRoomId, Date start, Date stop);

    List<Integer> getFreeRooms(Date start, Date stop);

    boolean isValidMeeting(Meeting meeting);
}
