package com.openclassrooms.mareu.ui.Add;

import android.text.Editable;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.model.Meeting;
import com.openclassrooms.mareu.model.MeetingRoom;
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

    private final int mId;
    private String mSubject;
    private LocalDateTime mStart;
    private LocalDateTime mStop;
    private String mParticipant;
    private HashSet<String> mParticipants;
    private int mRoomId;
    private List<Integer> mFreeMeetingRoomIDs;
    private CharSequence[] mFreeMeetingRoomNames;

    public AddMeetingFragmentViewModel(
            @NonNull MeetingsRepository meetingRepository) {
        mRepository = meetingRepository;
        mMeetingRooms = mRepository.getMeetingRooms();

        mId = mRepository.getNextMeetingId();
        LocalDateTime roundedNow = LocalDateTime.now().withSecond(0);
        mStart = roundedNow.withMinute(roundedNow.getMinute() / 15 * 15).plusMinutes(15);
        mStop = mStart.plusMinutes(30);
        mParticipants = new HashSet<>(0);
        mFreeMeetingRoomIDs = mRepository.getFreeRooms(toMeeting());
        mRoomId = mFreeMeetingRoomIDs.size() == 0 ? -1 : mFreeMeetingRoomIDs.get(0);

        updateFreeRooms();

        mAddMeetingFragmentItemMutableLiveData.setValue(toViewState());

    }

    private Meeting toMeeting() {
        return new Meeting(mId, PHONE_OWNER_EMAIL, new HashSet<>(mParticipants), mSubject, mStart, mStop, mRoomId);
    }

    private AddMeetingFragmentViewState toViewState() {
        MeetingRoom meetingRoom = mMeetingRooms.get(mRoomId);
        // get() indeed returns null when key is not mapped to something.

        return new AddMeetingFragmentViewState(
                String.valueOf(mId),
                PHONE_OWNER_EMAIL,
                mSubject,
                utils.niceTimeFormat(mStart),
                utils.niceTimeFormat(mStop),
                meetingRoom != null ? meetingRoom.getName() : String.valueOf(R.string.hint_meeting_room),
                mParticipant,
                mParticipants.toArray(new String[0]),
                mStart.getHour(),
                mStart.getMinute(),
                mStop.getHour(),
                mStop.getMinute(),
                mFreeMeetingRoomNames
        );
    }


    public LiveData<AddMeetingFragmentViewState> getAddMeetingFragmentItem() {
        return mAddMeetingFragmentItemMutableLiveData;
    }

    private void updateFreeRooms() {
        if (mStart.isAfter(mStop)) {
            mFreeMeetingRoomNames = new CharSequence[0];
            return;
        }
        mFreeMeetingRoomIDs = mRepository.getFreeRooms(toMeeting());
        mFreeMeetingRoomNames = new CharSequence[mFreeMeetingRoomIDs.size()];
        for (int id = 0; id < mFreeMeetingRoomIDs.size(); id++) {
            MeetingRoom meetingRoom = mMeetingRooms.get(mFreeMeetingRoomIDs.get(id));
            if (meetingRoom == null) throw new NullPointerException("Exceeded rooms number");
            mFreeMeetingRoomNames[id] = meetingRoom.getName();
        }
    }

    public void setRoom(int which) {
        MeetingRoom meetingRoom = mMeetingRooms.get(mFreeMeetingRoomIDs.get(which));
        if (meetingRoom == null) throw new NullPointerException("Requested non-existent room");
        mRoomId = meetingRoom.getId();
        mAddMeetingFragmentItemMutableLiveData.setValue(toViewState());
    }

    public void setTime(boolean startButton, int hour, int minute) {
        if (startButton) mStart = LocalDateTime.now().withHour(hour).withMinute(minute);
        else mStop = LocalDateTime.now().withHour(hour).withMinute(minute);
        updateFreeRooms();
        mAddMeetingFragmentItemMutableLiveData.setValue(toViewState());
    }

    public void setSubject(Editable editable) {
        if (editable == null) return;
        mSubject = editable.toString();
        mAddMeetingFragmentItemMutableLiveData.setValue(toViewState());
    }
    /* todo
        show useful error messages to user
        also implement restrict edit mode for meetings we don't own
     */

    public void addParticipant(Editable editable) {
        if (editable == null) return;
        String string = editable.toString();
        if (utils.isValidEmail(string)) {
            mParticipants = new HashSet<>(mParticipants);
            mParticipants.add(string);
            mParticipant = "";
            updateFreeRooms();
        } else {
            mParticipant = string;
        }
        mAddMeetingFragmentItemMutableLiveData.setValue(toViewState());
    }

    public void removeParticipant(String participant) {
        mParticipants = new HashSet<>(mParticipants);
        mParticipants.remove(participant);
        updateFreeRooms();
        mAddMeetingFragmentItemMutableLiveData.setValue(toViewState());
    }

    public boolean validate() {
        if (mRepository.isValidMeeting(toMeeting()))
            return mRepository.createMeeting(toMeeting());
        return false;
    }
}
