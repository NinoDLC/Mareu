package com.openclassrooms.mareu.ui.add;

import android.text.Editable;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.mareu.model.Meeting;
import com.openclassrooms.mareu.model.MeetingRoom;
import com.openclassrooms.mareu.repository.MeetingsRepository;
import com.openclassrooms.mareu.utils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class AddMeetingFragmentViewModel extends ViewModel {

    private static final String PHONE_OWNER_EMAIL = "chuck@buymore.com";
    private static final String SELECT_ROOM = "Select a room";
    private static final String EMAIL_ERROR = "Enter a valid email address";
    private static final String SUBJECT_ERROR = "Set a topic";

    private final MeetingsRepository mRepository;
    private final MutableLiveData<AddMeetingFragmentViewState> mAddMeetingFragmentItemMutableLiveData = new MutableLiveData<>();

    private final int mId;
    private String mSubject;
    private LocalDateTime mStart;
    private LocalDateTime mStop;
    private Editable mParticipant;
    private HashSet<String> mParticipants;
    private MeetingRoom mRoom;
    private List<MeetingRoom> mValidRooms;
    private String mParticipantError;
    private String mSubjectError;
    private String mGeneralError;

    public AddMeetingFragmentViewModel(@NonNull MeetingsRepository meetingRepository) {
        mRepository = meetingRepository;

        mId = mRepository.getNextMeetingId();
        LocalDateTime roundedNow = LocalDateTime.now().withSecond(0);
        mStart = roundedNow.withMinute(roundedNow.getMinute() / 15 * 15).plusMinutes(15);
        mStop = mStart.plusMinutes(30);
        mParticipants = new HashSet<>(0);
        mValidRooms = getValidRooms(toMeeting());
        if (mValidRooms.size() > 0) mRoom = mValidRooms.get(0);
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
                mRoom != null ? mRoom.getName() : SELECT_ROOM,
                mParticipant,
                mParticipants.toArray(new String[0]),
                mStart.getHour(),
                mStart.getMinute(),
                mStop.getHour(),
                mStop.getMinute(),
                validRoomNames(),
                mParticipantError,
                mSubjectError,
                mGeneralError);
    }

    CharSequence[] validRoomNames() {
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
        mValidRooms = getValidRooms(toMeeting());
        mAddMeetingFragmentItemMutableLiveData.setValue(toViewState());
    }

    public void setSubject(Editable editable) {
        if (editable == null) mSubjectError = SUBJECT_ERROR;
        else {
            mSubjectError = null;
            mSubject = editable.toString();
            mAddMeetingFragmentItemMutableLiveData.setValue(toViewState());
        }
    }

    public void addParticipant(@NonNull Editable editable) {
        String string = editable.toString().trim();
        if (utils.isValidEmail(string)) {
            mParticipants = new HashSet<>(mParticipants);
            mParticipants.add(editable.toString());
            mParticipant = null;
            mValidRooms = getValidRooms(toMeeting());
            mParticipantError = null;
        } else {
            mParticipantError = string.isEmpty() ? null : EMAIL_ERROR;
            mParticipant = editable;
        }
        mAddMeetingFragmentItemMutableLiveData.setValue(toViewState());
    }

    public void removeParticipant(String participant) {
        mParticipants = new HashSet<>(mParticipants);
        mParticipants.remove(participant);
        mValidRooms = getValidRooms(toMeeting());
        mAddMeetingFragmentItemMutableLiveData.setValue(toViewState());
    }

    // todo this could be a livedata
    private boolean isRoomFree(Meeting meeting) {
        for (Meeting m : getRoomMeetings(meeting.getRoom())) {
            if (m.getStart().isBefore(meeting.getStop())
                    && m.getStop().isAfter(meeting.getStart())
                    && meeting.getId() != m.getId()
            ) return false;
        }
        return true;
    }

    private List<Meeting> getRoomMeetings(MeetingRoom room) {
        List<Meeting> repoMeetings = mRepository.getMeetings().getValue();
        List<Meeting> roomMeetings = new ArrayList<>();
        if (repoMeetings == null) return roomMeetings;
        for (Meeting meeting : repoMeetings) {
            if (meeting.getRoom() == room)
                roomMeetings.add(meeting);
        }
        return roomMeetings;
    }

    private List<MeetingRoom> getValidRooms(@NonNull Meeting meeting) {
        int seats = meeting.getParticipants().size() + 1;  // account for owner also
        int smallestFittingCapacity = Integer.MAX_VALUE;
        List<MeetingRoom> list = new ArrayList<>();

        for (MeetingRoom room : MeetingRoom.values()) {
            if (isRoomFree(meeting) && room.getCapacity() >= seats)
                if (room.getCapacity() < smallestFittingCapacity) {
                    smallestFittingCapacity = room.getCapacity();
                    list.add(0, room);
                } else list.add(room);
        }
        return list;
    }

    public boolean validate() {
        if (mRepository.getMeetingById(mId) != null) {
            mGeneralError = "meeting id already in use";
            return false;
        }
        if (mStop.isBefore(mStart)) {
            mGeneralError = "can't stop before starting";
            return false;
        }
        if (mRoom == null) {
            mGeneralError = "no meeting room set";
            return false;
        }
        if (!isRoomFree(toMeeting())) {
            mGeneralError = "room is not free";
            return false;
        }
        for (String email : mParticipants) {
            if (email.equals(PHONE_OWNER_EMAIL)) {
                mGeneralError = "meeting owner also in participants";
                return false;
            }
        }
        if (mParticipants.size() > mRoom.getCapacity()) {
            mGeneralError = "meeting room is too small";
            return false;
        }
        return mRepository.createMeeting(toMeeting());
    }
}
