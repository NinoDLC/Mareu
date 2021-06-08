package com.openclassrooms.mareu.ui.Add;

import android.text.Editable;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.model.Meeting;
import com.openclassrooms.mareu.model.MeetingRoom;
import com.openclassrooms.mareu.repository.CurrentMeetingIdRepository;
import com.openclassrooms.mareu.repository.MeetingsRepository;
import com.openclassrooms.mareu.ui.utils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class AddMeetingFragmentViewModel extends ViewModel {

    private static final String PHONE_OWNER_EMAIL = "chuck@buymore.com";
    private final MeetingsRepository mRepository;
    private final HashMap<Integer, MeetingRoom> mMeetingRooms;
    private final MutableLiveData<AddMeetingFragmentViewState> mAddMeetingFragmentItemMutableLiveData = new MutableLiveData<>();

    private int mId;
    private String mSubject;
    private LocalDateTime mStart;
    private LocalDateTime mStop;
    private int mRoomid;
    private String mParticipant;
    private String[] mParticipants;
    private CharSequence[] mFreeMeetingRoomNames;

    public AddMeetingFragmentViewModel(
            @NonNull MeetingsRepository meetingRepository,
            @NonNull CurrentMeetingIdRepository currentMeetingIdRepository) {
        mRepository = meetingRepository;
        mMeetingRooms = mRepository.getMeetingRooms();
    }

    public LiveData<AddMeetingFragmentViewState> getAddMeetingFragmentItem() {
        return mAddMeetingFragmentItemMutableLiveData;
    }

    private void initMeeting() {
        LocalDateTime roundedNow = LocalDateTime.now().withSecond(0);
        LocalDateTime start = roundedNow.withMinute(roundedNow.getMinute() / 15 * 15).plusMinutes(15);
        LocalDateTime stop = start.plusMinutes(30);
        mMeetingBuilder = new Meeting.MeetingBuilder()
                .setId(0)
                .setOwner(PHONE_OWNER_EMAIL)
                .setParticipants(new HashSet<>(0))
                // not setting subject
                .setStart(start)
                .setStop(stop);
        List<Integer> freeMeetingRoomIDs = mRepository.getFreeRooms(mMeetingBuilder.build());
        mMeetingBuilder.setMeetingRoomId(freeMeetingRoomIDs.size() == 0 ? -1 : freeMeetingRoomIDs.get(0));

        updateFreeRoomNames();
    }

    private void meetingToViewState(Meeting meeting) {
        MeetingRoom meetingRoom = mMeetingRooms.get(meeting.getMeetingRoomId());
        // get() indeed returns null when key is not mapped to something.

        AddMeetingFragmentViewState item = new AddMeetingFragmentViewState(
                String.valueOf(meeting.getId()),
                meeting.getOwner(),
                meeting.getSubject(),
                utils.niceTimeFormat(meeting.getStart()),
                utils.niceTimeFormat(meeting.getStop()),
                meetingRoom != null ? meetingRoom.getName() : String.valueOf(R.string.hint_meeting_room),
                mParticipant,
                meeting.getParticipants().toArray(new String[0]),
                meeting.getStart().getHour(),
                meeting.getStart().getMinute(),
                meeting.getStop().getHour(),
                meeting.getStop().getMinute(),
                mFreeMeetingRoomNames
        );
        mAddMeetingFragmentItemMutableLiveData.setValue(item);
    }

    private void updateFreeRoomNames() {
        if (mMeetingBuilder.getStart().isAfter(mMeetingBuilder.getStop())) {
            mFreeRoomNames = new CharSequence[0];
            return;
        }
        mFreeRoomIds = mRepository.getFreeRooms(mMeetingBuilder.build());
        mFreeRoomNames = new CharSequence[mFreeRoomIds.size()];
        for (int id = 0; id < mFreeRoomIds.size(); id++) {
            MeetingRoom meetingRoom = mMeetingRooms.get(mFreeRoomIds.get(id));
            if (meetingRoom == null) throw new NullPointerException("Exceeded rooms number");
            mFreeRoomNames[id] = meetingRoom.getName();
        }
    }

    public void setRoom(int which) {
        MeetingRoom meetingRoom = mMeetingRooms.get(mFreeRoomIds.get(which));
        if (meetingRoom == null) throw new NullPointerException("Requested non-existent room");
        mMeetingBuilder.setMeetingRoomId(meetingRoom.getId());
        meetingToViewState();
    }

    public void setTime(boolean startButton, int hour, int minute) {
        if (startButton)
            mMeetingBuilder.setStart(LocalDateTime.now().withHour(hour).withMinute(minute));
        else mMeetingBuilder.setStop(LocalDateTime.now().withHour(hour).withMinute(minute));
        updateFreeRoomNames();
        meetingToViewState();
    }

    public void setSubject(Editable editable) {
        if (editable == null) return;
        String string = editable.toString();
        mMeetingBuilder.setSubject(string);
        meetingToViewState();
    }
    /* todo
        show useful error messages to user
        also implement restrict edit mode for meetings we don't own
     */

    public void addParticipant(Editable editable) {
        if (editable == null) return;
        String string = editable.toString();
        if (utils.isValidEmail(string)) {
            HashSet<String> participants = new HashSet<>(mMeetingBuilder.getParticipants());
            mMeetingBuilder.setParticipants(participants);
            participants.add(string);
            mParticipant = "";
            updateFreeRoomNames();
        } else {
            mParticipant = string;
        }
        meetingToViewState();
    }

    public void removeParticipant(String participant) {
        HashSet<String> participants = new HashSet<>(mMeetingBuilder.getParticipants());
        participants.remove(participant);
        mMeetingBuilder.setParticipants(participants);
        updateFreeRoomNames();
        meetingToViewState();
    }

    public boolean validate() {
        if (mRepository.isValidMeeting(mMeetingBuilder.build())) {
            int id = mMeetingBuilder.getId();
            if (id != 0) mRepository.removeMeetingById(id);
            mMeetingBuilder.setId(mRepository.getNextMeetingId());
            mRepository.createMeeting(mMeetingBuilder.build());
            return true;
        }
        return false;
    }
}
