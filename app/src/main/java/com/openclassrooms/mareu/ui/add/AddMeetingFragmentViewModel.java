package com.openclassrooms.mareu.ui.add;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
    private static final String ALREADY_AN_ATTENDEE = "already an attendee";
    private static final String STOP_BEFORE_START = "stop before start";
    private static final String NO_MEETING_ROOM = "no meeting room";
    private static final String ROOM_TOO_SMALL = "room too small";
    private static final String ROOM_NOT_FREE = "room not free";

    private final MeetingsRepository mRepository;
    private final MutableLiveData<AddMeetingFragmentViewState> mAddMeetingFragmentItemMutableLiveData = new MutableLiveData<>();

    private final int mId;
    private String mSubject;
    private LocalDateTime mStart;
    private LocalDateTime mStop;
    private final HashSet<String> mParticipants;
    private MeetingRoom mRoom;
    private List<MeetingRoom> mValidRooms;
    private String mParticipantError;
    private String mSubjectError;
    private String mGeneralError;

    public AddMeetingFragmentViewModel(@NonNull MeetingsRepository meetingRepository) {
        mRepository = meetingRepository;

        mId = mRepository.getNextMeetingId();
        if (mRepository.getMeetingById(mId) != null)
            throw new IllegalStateException(String.format("Attributed id already in use %d", mId));

        LocalDateTime roundedNow = LocalDateTime.now().withSecond(0);
        mStart = roundedNow.withMinute(roundedNow.getMinute() / 15 * 15).plusMinutes(15);
        mStop = mStart.plusMinutes(30);
        mParticipants = new HashSet<>(0);
        mValidRooms = getValidRooms(toMeeting());
        if (!mValidRooms.isEmpty()) mRoom = mValidRooms.get(0);
        mGeneralError = null;
        mSubjectError = null;
        mAddMeetingFragmentItemMutableLiveData.setValue(toViewState());
    }

    @Nullable
    private Meeting toMeeting() {
        mGeneralError = null;
        if (mStop.isBefore(mStart)) {
            mGeneralError = STOP_BEFORE_START;
            return null;
        }
        if (mRoom == null) mGeneralError = NO_MEETING_ROOM;
        else if (mParticipants.size() > mRoom.getCapacity()) mGeneralError = ROOM_TOO_SMALL;
        Meeting meeting = new Meeting(mId, PHONE_OWNER_EMAIL, new HashSet<>(mParticipants), mSubject, mStart, mStop, mRoom);
        if (!isRoomFree(meeting)) mGeneralError = ROOM_NOT_FREE;
        if (mSubject == null || mSubject.isEmpty()) mSubjectError = SUBJECT_ERROR;
        return meeting;
    }

    @NonNull
    private AddMeetingFragmentViewState toViewState() {
        return new AddMeetingFragmentViewState(
                String.valueOf(mId),
                PHONE_OWNER_EMAIL,
                utils.niceTimeFormat(mStart),
                utils.niceTimeFormat(mStop),
                mRoom != null ? mRoom.getName() : SELECT_ROOM,
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

    @NonNull
    private CharSequence[] validRoomNames() {
        CharSequence[] names = new CharSequence[mValidRooms.size()];
        for (MeetingRoom room : mValidRooms)
            names[mValidRooms.indexOf(room)] = room.getName();
        return names;
    }

    @NonNull
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

    public void setSubject(@NonNull String string) {
        mSubject = string;
        if (!string.isEmpty()) mSubjectError = null;
        mAddMeetingFragmentItemMutableLiveData.setValue(toViewState());
    }

    public boolean addParticipant(@NonNull String string) {
        mParticipantError = null;
        if (string.isEmpty()) return true;
        if (!utils.isValidEmail(string)) mParticipantError = EMAIL_ERROR;
        if (inParticipants(string)) mParticipantError = ALREADY_AN_ATTENDEE;
        if (mParticipantError != null) {
            mAddMeetingFragmentItemMutableLiveData.setValue(toViewState());
            return false;
        }
        mParticipants.add(string);
        mValidRooms = getValidRooms(toMeeting());
        mAddMeetingFragmentItemMutableLiveData.setValue(toViewState());
        return true;
    }

    private boolean inParticipants(@NonNull String participant) {
        return (participant.equals(PHONE_OWNER_EMAIL) || mParticipants.contains(participant));
    }

    public void removeParticipant(@NonNull String participant) {
        mParticipants.remove(participant);
        mValidRooms = getValidRooms(toMeeting());
        mAddMeetingFragmentItemMutableLiveData.setValue(toViewState());
    }

    private boolean isRoomFree(@NonNull Meeting meeting) {
        for (Meeting m : getRoomMeetings(meeting.getRoom())) {
            if (m.getStart().isBefore(meeting.getStop()) &&
                    m.getStop().isAfter(meeting.getStart())
            ) return false;
        }
        return true;
    }

    @NonNull
    private List<Meeting> getRoomMeetings(@NonNull MeetingRoom room) {
        List<Meeting> repoMeetings = mRepository.getMeetings().getValue();
        List<Meeting> roomMeetings = new ArrayList<>();
        if (repoMeetings == null) return roomMeetings;
        for (Meeting meeting : repoMeetings) {
            if (meeting.getRoom() == room)
                roomMeetings.add(meeting);
        }
        return roomMeetings;
    }

    @NonNull
    private List<MeetingRoom> getValidRooms(@Nullable Meeting meeting) {
        List<MeetingRoom> list = new ArrayList<>();
        if (meeting == null) return list;
        int seats = meeting.getParticipants().size() + 1;  // account for owner also
        int smallestFittingCapacity = Integer.MAX_VALUE;

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
        Meeting meeting = toMeeting();
        if (mGeneralError != null || mParticipantError != null || mSubjectError != null) {
            mAddMeetingFragmentItemMutableLiveData.setValue(toViewState());
            return false;
        }
        if (meeting == null) return false;
        return mRepository.createMeeting(meeting);
    }
}
