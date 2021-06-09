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
import java.util.HashSet;
import java.util.List;

public class AddMeetingFragmentViewModel extends ViewModel {

    private static final String PHONE_OWNER_EMAIL = "chuck@buymore.com";
    private final MeetingsRepository mRepository;
    private final MutableLiveData<AddMeetingFragmentViewState> mAddMeetingFragmentItemMutableLiveData = new MutableLiveData<>();

    private final int mId;
    private String mSubject;
    private LocalDateTime mStart;
    private LocalDateTime mStop;
    private String mParticipant;
    private HashSet<String> mParticipants;
    private MeetingRoom mRoom;
    private List<MeetingRoom> mValidRooms;

    public AddMeetingFragmentViewModel(
            @NonNull MeetingsRepository meetingRepository) {
        mRepository = meetingRepository;

        mId = mRepository.getNextMeetingId();
        LocalDateTime roundedNow = LocalDateTime.now().withSecond(0);
        mStart = roundedNow.withMinute(roundedNow.getMinute() / 15 * 15).plusMinutes(15);
        mStop = mStart.plusMinutes(30);
        mParticipants = new HashSet<>(0);
        mValidRooms = mRepository.getValidRooms(toMeeting());

        mAddMeetingFragmentItemMutableLiveData.setValue(toViewState());

    }

    private Meeting toMeeting() {
        return new Meeting(mId, PHONE_OWNER_EMAIL, new HashSet<>(mParticipants), mSubject, mStart, mStop, mRoom);
    }

    private AddMeetingFragmentViewState toViewState() {
        return new AddMeetingFragmentViewState(
                String.valueOf(mId),
                PHONE_OWNER_EMAIL,
                mSubject,
                utils.niceTimeFormat(mStart),
                utils.niceTimeFormat(mStop),
                mRoom != null ? mRoom.getName() : String.valueOf(R.string.hint_meeting_room),
                mParticipant,
                mParticipants.toArray(new String[0]),
                mStart.getHour(),
                mStart.getMinute(),
                mStop.getHour(),
                mStop.getMinute(),
                validRoomNames()
        );
    }

    CharSequence[] validRoomNames(){
        CharSequence[] names = new CharSequence[mValidRooms.size()];
        for (MeetingRoom room : mValidRooms)
            names[mValidRooms.indexOf(room)] = room.getName();
        return names;
    }

    public LiveData<AddMeetingFragmentViewState> getAddMeetingFragmentItem() {
        return mAddMeetingFragmentItemMutableLiveData;
    }

    public void setRoom(int which) {
        mRoom = mValidRooms.get(which);
        mAddMeetingFragmentItemMutableLiveData.setValue(toViewState());
    }

    public void setTime(boolean startButton, int hour, int minute) {
        if (startButton) mStart = LocalDateTime.now().withHour(hour).withMinute(minute);
        else mStop = LocalDateTime.now().withHour(hour).withMinute(minute);
        mValidRooms = mRepository.getValidRooms(toMeeting());
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
            mValidRooms = mRepository.getValidRooms(toMeeting());
        } else {
            mParticipant = string;
        }
        mAddMeetingFragmentItemMutableLiveData.setValue(toViewState());
    }

    public void removeParticipant(String participant) {
        mParticipants = new HashSet<>(mParticipants);
        mParticipants.remove(participant);
        mValidRooms = mRepository.getValidRooms(toMeeting());
        mAddMeetingFragmentItemMutableLiveData.setValue(toViewState());
    }

    public boolean validate() {
        if (mRepository.isValidMeeting(toMeeting()))
            return mRepository.createMeeting(toMeeting());
        return false;
    }
}
