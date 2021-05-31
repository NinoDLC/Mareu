package com.openclassrooms.mareu.ui;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.mareu.model.Meeting;
import com.openclassrooms.mareu.model.MeetingRoom;
import com.openclassrooms.mareu.repository.MeetingsRepository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class ShowMeetingFragmentViewModel extends ViewModel {

    private final MeetingsRepository mRepository;

    private final HashMap<Integer, MeetingRoom> mMeetingRooms;
    private LocalDateTime mStart;
    private LocalDateTime mStop;
    private List<Integer> mFreeRoomIds;
    private static final String PHONE_OWNER_EMAIL = "chuck@buymore.com";
    private static final String EMPTY_STRING = "";

    private final MutableLiveData<CharSequence[]> mFreeRoomNames = new MutableLiveData<>();

    public ShowMeetingFragmentViewModel(@NonNull MeetingsRepository meetingRepository) {
        mRepository = meetingRepository;
        mMeetingRooms = mRepository.getMeetingRooms();
    }

    public Meeting requireMeetingById(int id) {
        Meeting meeting;
        if (id == 0) {
            LocalDateTime roundedNow = LocalDateTime.now().withSecond(0);
            LocalDateTime start = roundedNow.withMinute(roundedNow.getMinute() / 15 * 15).plusMinutes(15);
            LocalDateTime stop = start.plusMinutes(30);
            meeting = new Meeting(
                    mRepository.getNextMeetingId(),
                    PHONE_OWNER_EMAIL,
                    new HashSet<>(0),
                    EMPTY_STRING, start, stop,
                    mRepository.getFreeRooms(start, stop).get(0));
        } else {
            meeting = mRepository.getMeetingById(id);
        }
        if (meeting == null) throw new NullPointerException("Inexistent meeting, id " + id);
        return meeting;
    }

    public HashMap<Integer, MeetingRoom> getMeetingRooms() {
        return mMeetingRooms;
    }

    public String selectRoom(int which) {
        MeetingRoom meetingRoom = mMeetingRooms.get(mFreeRoomIds.get(which));
        if (meetingRoom == null) throw new NullPointerException("Requested inexistant room");
        return meetingRoom.getName();
    }

    public LiveData<CharSequence[]> getFreeMeetingRooms() {
        return mFreeRoomNames;
    }

    public String timeButtonChanged(boolean startButton, int hour, int minute) {
        if (startButton) mStart = LocalDateTime.now().withHour(hour).withMinute(minute);
        else mStop = LocalDateTime.now().withHour(hour).withMinute(minute);

        mFreeRoomIds = mRepository.getFreeRooms(mStart, mStop);
        CharSequence[] freeRoomNames = new CharSequence[mFreeRoomIds.size()];
        for (Integer id : mFreeRoomIds) {
            MeetingRoom meetingRoom = mMeetingRooms.get(id);
            if (meetingRoom == null) throw new NullPointerException("Exceeded rooms number");
            freeRoomNames[id] = meetingRoom.getName();
        }
        mFreeRoomNames.setValue(freeRoomNames);
        return startButton ? utils.niceTimeFormat(mStart) : utils.niceTimeFormat(mStop);
    }
}
