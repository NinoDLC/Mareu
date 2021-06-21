package com.openclassrooms.mareu.ui.add;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.model.Meeting;
import com.openclassrooms.mareu.model.MeetingRoom;
import com.openclassrooms.mareu.repository.MeetingsRepository;
import com.openclassrooms.mareu.utils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class AddMeetingFragmentViewModel extends ViewModel {

    @NonNull
    private final Application application;
    @NonNull
    private final MeetingsRepository mMeetingRepo;

    private final MutableLiveData<AddMeetingFragmentViewState> mAddMeetingFragmentItemMutableLiveData = new MutableLiveData<>();

    // data to populate new meeting
    private final int mId;
    private String mTopic;
    private LocalDateTime mStart;
    private LocalDateTime mEnd;
    private final HashSet<String> mParticipants;
    private MeetingRoom mRoom;

    private List<MeetingRoom> mValidRooms;
    private String mParticipantError;
    private String mTopicError;
    private String mGeneralError;

    public AddMeetingFragmentViewModel(@NonNull Application application, @NonNull MeetingsRepository meetingRepository) {
        this.application = application;
        mMeetingRepo = meetingRepository;

        mId = mMeetingRepo.getNextMeetingId();  // not testing duplicate ids...
        LocalDateTime roundedNow = LocalDateTime.now().withSecond(0);
        mStart = roundedNow.withMinute(roundedNow.getMinute() / 15 * 15).plusMinutes(15);
        mEnd = mStart.plusMinutes(30);
        mParticipants = new HashSet<>(0);
        mValidRooms = getValidRooms(toMeeting());
        if (!mValidRooms.isEmpty()) mRoom = mValidRooms.get(0);
        mGeneralError = null;
        mTopicError = null;
        mAddMeetingFragmentItemMutableLiveData.setValue(toViewState());
    }

    @Nullable
    private Meeting toMeeting() {
        mGeneralError = null;
        if (mEnd.isBefore(mStart)) {
            mGeneralError = application.getString(R.string.stop_before_start);
            return null;
        }
        if (mRoom == null) mGeneralError = application.getString(R.string.no_meeting_room);
        else if (mParticipants.size() > mRoom.getCapacity())
            mGeneralError = application.getString(R.string.room_too_small);
        Meeting meeting = new Meeting(
                mId,
                application.getString(R.string.phone_owner_email),
                new HashSet<>(mParticipants),
                mTopic,
                mStart,
                mEnd,
                mRoom);
        if (!isRoomFree(meeting)) mGeneralError = application.getString(R.string.room_not_free);
        if (mTopic == null || mTopic.isEmpty())
            mTopicError = application.getString(R.string.topic_error);
        return meeting;
    }

    @NonNull
    private AddMeetingFragmentViewState toViewState() {
        return new AddMeetingFragmentViewState(
                String.valueOf(mId),
                application.getString(R.string.phone_owner_email),
                utils.niceTimeFormat(mStart),
                utils.niceTimeFormat(mEnd),
                mRoom != null ? mRoom.getName() : application.getString(R.string.select_room),
                mParticipants.toArray(new String[0]),
                mStart.getHour(),
                mStart.getMinute(),
                mEnd.getHour(),
                mEnd.getMinute(),
                validRoomNames(),
                mParticipantError,
                mTopicError,
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
        else mEnd = LocalDateTime.now().withHour(hour).withMinute(minute);
        mValidRooms = getValidRooms(toMeeting());
        mAddMeetingFragmentItemMutableLiveData.setValue(toViewState());
    }

    public void setTopic(@NonNull String string) {
        mTopic = string;
        if (!string.isEmpty()) mTopicError = null;
        mAddMeetingFragmentItemMutableLiveData.setValue(toViewState());
    }

    public boolean addParticipant(@NonNull String string) {
        mParticipantError = null;
        if (string.isEmpty()) return true;
        if (!utils.isValidEmail(string))
            mParticipantError = application.getString(R.string.email_error);
        if (string.equals(application.getString(R.string.phone_owner_email)))
            mParticipantError = application.getString(R.string.do_not_add_yourself);
        if (mParticipants.contains(string))
            mParticipantError = application.getString(R.string.already_an_attendee);
        if (mParticipantError != null) {
            mAddMeetingFragmentItemMutableLiveData.setValue(toViewState());
            return false;
        }
        mParticipants.add(string);
        mValidRooms = getValidRooms(toMeeting());
        mAddMeetingFragmentItemMutableLiveData.setValue(toViewState());
        return true;
    }

    public void removeParticipant(@NonNull String participant) {
        mParticipants.remove(participant);
        mValidRooms = getValidRooms(toMeeting());
        mAddMeetingFragmentItemMutableLiveData.setValue(toViewState());
    }

    private boolean isRoomFree(@NonNull Meeting meeting) {
        for (Meeting m : getRoomMeetings(meeting.getRoom())) {
            if (m.getStart().isBefore(meeting.getEnd()) &&
                    m.getEnd().isAfter(meeting.getStart())
            ) return false;
        }
        return true;
    }

    @NonNull
    private List<Meeting> getRoomMeetings(@NonNull MeetingRoom room) {
        List<Meeting> repoMeetings = mMeetingRepo.getMeetings().getValue();
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
            if (isRoomFree(meeting) && room.getCapacity() >= seats) {
                if (room.getCapacity() < smallestFittingCapacity) {
                    smallestFittingCapacity = room.getCapacity();
                    list.add(0, room);
                } else {
                    list.add(room);
                }
            }
        }
        return list;
    }

    public boolean validate() {
        Meeting meeting = toMeeting();
        if (mGeneralError != null || mParticipantError != null || mTopicError != null) {
            mAddMeetingFragmentItemMutableLiveData.setValue(toViewState());
            return false;
        }
        if (meeting == null) return false;
        mMeetingRepo.createMeeting(meeting);
        return true;
    }
}
