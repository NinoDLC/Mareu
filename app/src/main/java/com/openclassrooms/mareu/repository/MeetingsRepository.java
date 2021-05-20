package com.openclassrooms.mareu.repository;

import androidx.annotation.Nullable;

import com.openclassrooms.mareu.model.Meeting;
import com.openclassrooms.mareu.model.MeetingRoom;

import java.time.LocalDateTime;
import java.util.List;

public interface MeetingsRepository {

    List<MeetingRoom> getMeetingRooms();

    List<Meeting> getMeetings();

    int getNextMeetingId();

    @Nullable
    Meeting getMeetingById(int id);

    @Nullable
    MeetingRoom getMeetingRoomById(int id);

    // TODO add 'throws esception' ?
    void createMeeting(Meeting meeting);

    void removeMeetingById(int meetingId);

    List<Meeting> getRoomMeetings(int roomId);

    boolean isRoomFree(int MeetingRoomId, LocalDateTime start, LocalDateTime stop);

    List<Integer> getFreeRooms(LocalDateTime start, LocalDateTime stop);

    boolean isValidMeeting(Meeting meeting);
}
