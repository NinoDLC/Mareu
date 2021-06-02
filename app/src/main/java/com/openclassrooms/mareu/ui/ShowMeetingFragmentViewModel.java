package com.openclassrooms.mareu.ui;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.model.Meeting;
import com.openclassrooms.mareu.model.MeetingRoom;
import com.openclassrooms.mareu.repository.MeetingsRepository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class ShowMeetingFragmentViewModel extends ViewModel {

    private static final String PHONE_OWNER_EMAIL = "chuck@buymore.com";

    private final MeetingsRepository mRepository;
    private final HashMap<Integer, MeetingRoom> mMeetingRooms;
    private List<Integer> mFreeRoomIds;
    private Meeting.MeetingBuilder mMeetingBuilder;
    private final MutableLiveData<CharSequence[]> mFreeRoomNames = new MutableLiveData<>();

    public ShowMeetingFragmentViewModel(@NonNull MeetingsRepository meetingRepository) {
        mRepository = meetingRepository;
        mMeetingRooms = mRepository.getMeetingRooms();
    }

    public Meeting initMeeting(int id) {
        if (id == 0) {
            LocalDateTime roundedNow = LocalDateTime.now().withSecond(0);
            LocalDateTime start = roundedNow.withMinute(roundedNow.getMinute() / 15 * 15).plusMinutes(15);
            LocalDateTime stop = start.plusMinutes(30);
            mMeetingBuilder = new Meeting.MeetingBuilder()
                    .setId(0)  // todo only at creation do we set id mRepository.getNextMeetingId()
                    .setOwner(PHONE_OWNER_EMAIL)
                    .setParticipants(new HashSet<>(0))
                    // not setting subject
                    .setStart(start)
                    .setStop(stop)
                    .setMeetingRoomId(mRepository.getFreeRooms(start, stop).get(0));
            // todo this get(0) might throw an exception if all rooms are booked...
            //  also see getMeetingRoomName()
        } else {
            Meeting m = mRepository.getMeetingById(id);
            if (m == null) throw new NullPointerException("Inexistent meeting, id " + id);

            HashSet<String> participants = new HashSet<>(0);
            participants.addAll(m.getParticipants());

            mMeetingBuilder = new Meeting.MeetingBuilder()
                    .setId(m.getId())
                    .setOwner(m.getOwner())
                    .setParticipants(participants)
                    .setSubject(m.getSubject())
                    .setStart(m.getStart())
                    .setStop(m.getStop())
                    .setMeetingRoomId(m.getMeetingRoomId());
        }
        updateFreeRoomNames();
        return mMeetingBuilder.build();
    }

    public String setRoom(int which) {
        MeetingRoom meetingRoom = mMeetingRooms.get(mFreeRoomIds.get(which));
        if (meetingRoom == null) throw new NullPointerException("Requested inexistant room");
        mMeetingBuilder.setMeetingRoomId(meetingRoom.getId());
        return meetingRoom.getName();
    }

    public LiveData<CharSequence[]> getFreeMeetingRooms() {
        return mFreeRoomNames;
    }

    public String timeButtonChanged(boolean startButton, int hour, int minute) {
        if (startButton)
            mMeetingBuilder.setStart(LocalDateTime.now().withHour(hour).withMinute(minute));
        else mMeetingBuilder.setStop(LocalDateTime.now().withHour(hour).withMinute(minute));

        updateFreeRoomNames();
        return startButton ? utils.niceTimeFormat(mMeetingBuilder.getStart()) : utils.niceTimeFormat(mMeetingBuilder.getStop());
    }

    void updateFreeRoomNames() {
        mFreeRoomIds = mRepository.getFreeRooms(mMeetingBuilder.getStart(), mMeetingBuilder.getStop());
        CharSequence[] freeRoomNames = new CharSequence[mFreeRoomIds.size()];
        for (int id = 0; id < mFreeRoomIds.size(); id++) {
            MeetingRoom meetingRoom = mMeetingRooms.get(mFreeRoomIds.get(id));
            if (meetingRoom == null) throw new NullPointerException("Exceeded rooms number");
            freeRoomNames[id] = meetingRoom.getName();
        }
        mFreeRoomNames.setValue(freeRoomNames);
    }

    public String getMeetingRoomName() {
        MeetingRoom meetingRoom = mMeetingRooms.get(mMeetingBuilder.getMeetingRoomId());
        // todo warning : meeting room is possibly
        return meetingRoom != null ? meetingRoom.getName() : String.valueOf(R.string.hint_meeting_room);
    }
}
