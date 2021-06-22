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
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class AddMeetingFragmentViewModel extends ViewModel {

    @NonNull
    private final Application application;
    @NonNull
    private final MeetingsRepository mMeetingRepo;

    private final MutableLiveData<AddMeetingFragmentViewState> viewState = new MutableLiveData<>();

    // data to populate new meeting
    private final int mId;
    private String mTopic;
    private LocalDateTime mStart = utils.getNextRoundTime();
    private LocalDateTime mEnd = mStart.plusMinutes(30);
    private final HashSet<String> mParticipants = new HashSet<>(0);
    private MeetingRoom mRoom = MeetingRoom.ROOM_0;

    private List<MeetingRoom> mValidRooms;
    private String mParticipantError;
    private String mTopicError;
    private String mGeneralError;

    public AddMeetingFragmentViewModel(@NonNull Application application, @NonNull MeetingsRepository meetingRepository) {
        this.application = application;
        mMeetingRepo = meetingRepository;

        mId = mMeetingRepo.getNextMeetingId();  // not testing duplicate ids...
        mValidRooms = getValidRooms(toMeeting());
        if (!mValidRooms.isEmpty()) mRoom = mValidRooms.get(0);
        mGeneralError = null;  // cancel errors from toMeeting()
        mTopicError = null;    // cancel errors from toMeeting()
        viewState.setValue(toViewState());
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
                mRoom
        );
        if (!mValidRooms.contains(meeting.getRoom()))
            mGeneralError = application.getString(R.string.room_not_free);
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
                mGeneralError
        );
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
        return viewState;
    }

    public void setRoom(int which) {
        mRoom = mValidRooms.get(which);
        viewState.setValue(toViewState());
    }

    public void setTime(boolean startButton, int hour, int minute) {
        if (startButton) mStart = utils.ARBITRARY_DAY.withHour(hour).withMinute(minute);
        else mEnd = utils.ARBITRARY_DAY.withHour(hour).withMinute(minute);
        mValidRooms = getValidRooms(toMeeting());
        viewState.setValue(toViewState());
    }

    public void setTopic(@NonNull String string) {
        mTopic = string;
        if (!string.isEmpty()) mTopicError = null;
        viewState.setValue(toViewState());
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
            viewState.setValue(toViewState());
            return false;
        }
        mParticipants.add(string);
        mValidRooms = getValidRooms(toMeeting());
        viewState.setValue(toViewState());
        return true;
    }

    public void removeParticipant(@NonNull String participant) {
        mParticipants.remove(participant);
        mValidRooms = getValidRooms(toMeeting());
        viewState.setValue(toViewState());
    }

    @NonNull
    private List<MeetingRoom> getValidRooms(@Nullable Meeting meeting) {
        List<MeetingRoom> validRooms = new ArrayList<>(Arrays.asList(MeetingRoom.values()));
        if (meeting == null) return validRooms;  // null if mStart after mEnd
        List<Meeting> plannedMeetings = mMeetingRepo.getMeetings().getValue();
        if (plannedMeetings == null)
            throw new IllegalStateException("got null instead of meetings list");
        for (Meeting plannedMeeting : plannedMeetings)
            if (!plannedMeeting.getStart().isAfter(meeting.getEnd()) && !plannedMeeting.getEnd().isBefore(meeting.getStart()))
                validRooms.remove(plannedMeeting.getRoom());
        for (Iterator<MeetingRoom> iterator = validRooms.iterator(); iterator.hasNext(); ) {
            MeetingRoom meetingRoom = iterator.next();
            if (meetingRoom.getCapacity() < meeting.getParticipants().size() + 1) // account for owner also
                validRooms.remove(meetingRoom);
        }
        Collections.sort(validRooms, (o1, o2) -> Integer.compare(o1.getCapacity(), o2.getCapacity()));
        return validRooms;
    }

    public boolean validate() {
        Meeting meeting = toMeeting();
        if (mGeneralError != null || mParticipantError != null || mTopicError != null) {
            viewState.setValue(toViewState());
            return false;
        }
        if (meeting == null) return false;
        mMeetingRepo.createMeeting(meeting);
        return true;
    }
}
