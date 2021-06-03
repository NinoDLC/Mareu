package com.openclassrooms.mareu.ui;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.model.Meeting;
import com.openclassrooms.mareu.model.MeetingRoom;
import com.openclassrooms.mareu.repository.CurrentMeetingIdRepository;
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
    private CharSequence[] mFreeRoomNames;
    private final MutableLiveData<Meeting> mMeetingMutableLiveData = new MutableLiveData<>();

    public ShowMeetingFragmentViewModel(
            @NonNull MeetingsRepository meetingRepository,
            @NonNull CurrentMeetingIdRepository currentMeetingIdRepository) {
        mRepository = meetingRepository;
        mMeetingRooms = mRepository.getMeetingRooms();

        initMeeting(currentMeetingIdRepository.getCurrentId());
    }

    public LiveData<Meeting> getMeeting() {
        return mMeetingMutableLiveData;
    }

    private void initMeeting(int id) {
        if (id == 0) {
            LocalDateTime roundedNow = LocalDateTime.now().withSecond(0);
            LocalDateTime start = roundedNow.withMinute(roundedNow.getMinute() / 15 * 15).plusMinutes(15);
            LocalDateTime stop = start.plusMinutes(30);
            mMeetingBuilder = new Meeting.MeetingBuilder()
                    .setId(0)  // todo do set ID at Meeting creation. mRepository.getNextMeetingId()
                    .setOwner(PHONE_OWNER_EMAIL)
                    .setParticipants(new HashSet<>(0))
                    // not setting subject
                    .setStart(start)
                    .setStop(stop)
                    .setMeetingRoomId(mRepository.getFreeRooms(start, stop).get(0));
            // todo this get(0) might throw an exception if all rooms are booked...
            //  also see getMeetingRoomName()
            //  also suggest the smallest room that fits

        } else {
            Meeting m = mRepository.getMeetingById(id);
            if (m == null) throw new NullPointerException("Inexistent meeting, id " + id);

            HashSet<String> participants = new HashSet<>(0);
            participants.addAll(m.getParticipants());

            mMeetingBuilder = new Meeting.MeetingBuilder()
                    .setId(m.getId())
                    .setOwner(m.getOwner())
                    .setParticipants(new HashSet<>(participants))
                    .setSubject(m.getSubject())
                    .setStart(m.getStart())
                    .setStop(m.getStop())
                    .setMeetingRoomId(m.getMeetingRoomId());
        }
        updateFreeRoomNames();
        mMeetingMutableLiveData.setValue(mMeetingBuilder.build());
    }

    public void setRoom(int which) {
        MeetingRoom meetingRoom = mMeetingRooms.get(mFreeRoomIds.get(which));
        if (meetingRoom == null) throw new NullPointerException("Requested inexistant room");
        mMeetingBuilder.setMeetingRoomId(meetingRoom.getId());
        mMeetingMutableLiveData.setValue(mMeetingBuilder.build());
    }

    public CharSequence[] getFreeMeetingRooms() {
        return mFreeRoomNames;
    }

    public void timeButtonChanged(boolean startButton, int hour, int minute) {
        if (startButton)
            mMeetingBuilder.setStart(LocalDateTime.now().withHour(hour).withMinute(minute));
        else mMeetingBuilder.setStop(LocalDateTime.now().withHour(hour).withMinute(minute));

        updateFreeRoomNames();
        mMeetingMutableLiveData.setValue(mMeetingBuilder.build());
    }

    private void updateFreeRoomNames() {
        if (mMeetingBuilder.getStart().isAfter(mMeetingBuilder.getStop())) {
            mFreeRoomNames = new CharSequence[0];
            return;
        }
        mFreeRoomIds = mRepository.getFreeRooms(mMeetingBuilder.getStart(), mMeetingBuilder.getStop());
        mFreeRoomNames = new CharSequence[mFreeRoomIds.size()];
        for (int id = 0; id < mFreeRoomIds.size(); id++) {
            MeetingRoom meetingRoom = mMeetingRooms.get(mFreeRoomIds.get(id));
            if (meetingRoom == null) throw new NullPointerException("Exceeded rooms number");
            mFreeRoomNames[id] = meetingRoom.getName();
        }
    }

    public String getMeetingRoomName() {
        MeetingRoom meetingRoom = mMeetingRooms.get(mMeetingBuilder.getMeetingRoomId());
        // todo warning : meeting room is possibly
        return meetingRoom != null ? meetingRoom.getName() : String.valueOf(R.string.hint_meeting_room);
    }

    public void newParticipant(String participant) {
        if (participant.isEmpty()) return;
        HashSet<String> participants = new HashSet<>(mMeetingBuilder.getParticipants());
        participants.add(participant);
        mMeetingBuilder.setParticipants(participants);
        mMeetingMutableLiveData.setValue(mMeetingBuilder.build());
    }

    public void deleteParticipant(String participant) {
        HashSet<String> participants = new HashSet<>(mMeetingBuilder.getParticipants());
        participants.remove(participant);
        mMeetingBuilder.setParticipants(participants);
        mMeetingMutableLiveData.setValue(mMeetingBuilder.build());
    }
}
