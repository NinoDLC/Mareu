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
    private String mParticipant;
    private HashSet<String> mParticipants;
    private int mRoomid;
    List<Integer> mFreeMeetingRoomIDs;
    private CharSequence[] mFreeMeetingRoomNames;

    public AddMeetingFragmentViewModel(
            @NonNull MeetingsRepository meetingRepository,
            @NonNull CurrentMeetingIdRepository currentMeetingIdRepository) {
        mRepository = meetingRepository;
        mMeetingRooms = mRepository.getMeetingRooms();

        mId = mRepository.getNextMeetingId();
        LocalDateTime roundedNow = LocalDateTime.now().withSecond(0);
        mStart = roundedNow.withMinute(roundedNow.getMinute() / 15 * 15).plusMinutes(15);
        mStop = mStart.plusMinutes(30);
        mParticipants = new HashSet<>(0);
        mRoomid = mFreeMeetingRoomIDs.size() == 0 ? -1 : mFreeMeetingRoomIDs.get(0);
        mFreeMeetingRoomIDs = mRepository.getFreeRooms(qsdmflkjsqml);

        updateFreeRoomNames();

        mAddMeetingFragmentItemMutableLiveData.setValue(toViewState());

    }

    private Meeting toMeeting() {
        return new Meeting(mId,PHONE_OWNER_EMAIL,new HashSet<String>(mParticipants),mSubject,mStart,mStop,mRoomid);
    }

    private AddMeetingFragmentViewState toViewState() {
        MeetingRoom meetingRoom = mMeetingRooms.get(mRoomid);
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

    private void updateFreeRoomNames() {
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
        mMeetingBuilder.setMeetingRoomId(meetingRoom.getId());
        toViewState();
    }

    public void setTime(boolean startButton, int hour, int minute) {
        if (startButton)
            mMeetingBuilder.setStart(LocalDateTime.now().withHour(hour).withMinute(minute));
        else mMeetingBuilder.setStop(LocalDateTime.now().withHour(hour).withMinute(minute));
        updateFreeRoomNames();
        toViewState();
    }

    public void setSubject(Editable editable) {
        if (editable == null) return;
        String string = editable.toString();
        mMeetingBuilder.setSubject(string);
        toViewState();
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
        toViewState();
    }

    public void removeParticipant(String participant) {
        HashSet<String> participants = new HashSet<>(mMeetingBuilder.getParticipants());
        participants.remove(participant);
        mMeetingBuilder.setParticipants(participants);
        updateFreeRoomNames();
        toViewState();
    }

    public boolean validate() {
        if (mRepository.isValidMeeting(mMeetingBuilder.build())) {
            mRepository.createMeeting(mMeetingBuilder.build());
            return true;
        }
        return false;
    }
}
