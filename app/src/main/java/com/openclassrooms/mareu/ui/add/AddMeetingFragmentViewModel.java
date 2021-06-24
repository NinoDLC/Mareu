package com.openclassrooms.mareu.ui.add;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.model.Meeting;
import com.openclassrooms.mareu.model.MeetingRoom;
import com.openclassrooms.mareu.repository.MeetingsRepository;
import com.openclassrooms.mareu.utils;

import java.time.Clock;
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

    @NonNull
    private String mTopic = "";
    private LocalDateTime mStart;
    private LocalDateTime mEnd;
    private final HashSet<String> mParticipants = new HashSet<>(0);
    private MeetingRoom mRoom;

    private List<MeetingRoom> mValidRooms;
    private String mParticipantError;
    private String mTopicError;
    private String mTimeError;
    private String mRoomError;

    public AddMeetingFragmentViewModel(
            @NonNull Application application,
            @NonNull MeetingsRepository meetingRepository,
            @NonNull Clock clock) {
        this.application = application;
        mMeetingRepo = meetingRepository;

        LocalDateTime now = LocalDateTime.now(clock);
        mStart = utils.ARBITRARY_DAY.withHour(now.getHour()).withMinute(now.getMinute() / 15 * 15).plusMinutes(15).withSecond(0);
        mEnd = mStart.plusMinutes(30);
        mId = mMeetingRepo.getNextMeetingId();  // not testing duplicate ids...

        mValidRooms = getValidRooms();  // depends on meetingRepository
        if (!mValidRooms.isEmpty()) mRoom = mValidRooms.get(0);
        viewState.setValue(toViewState());
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
                mTimeError,
                mRoomError
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
    public LiveData<AddMeetingFragmentViewState> getViewState() {
        return viewState;
    }

    public void setRoom(int which) {
        mRoomError = null;
        mRoom = mValidRooms.get(which);
        viewState.setValue(toViewState());
    }

    public void setTime(boolean startButton, int hour, int minute) {
        mRoomError = null;

        if (startButton) mStart = utils.ARBITRARY_DAY.withHour(hour).withMinute(minute);
        else mEnd = utils.ARBITRARY_DAY.withHour(hour).withMinute(minute);

        if (mStart.isBefore(mEnd)) mTimeError = null;
        else mTimeError = application.getString(R.string.stop_before_start);

        mValidRooms = getValidRooms();
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
        mValidRooms = getValidRooms();
        viewState.setValue(toViewState());
        return true;
    }

    public void removeParticipant(@NonNull String participant) {
        mParticipants.remove(participant);
        mValidRooms = getValidRooms();
        viewState.setValue(toViewState());
    }

    @NonNull
    private List<MeetingRoom> getValidRooms() {
        if (mTimeError != null) return new ArrayList<>();  // end before start
        List<MeetingRoom> validRooms = new ArrayList<>(Arrays.asList(MeetingRoom.values()));

        // Ok for this project, but won't work when meetings are on the internet
        List<Meeting> plannedMeetings = mMeetingRepo.getMeetings().getValue();

        if (plannedMeetings == null)
            throw new IllegalStateException("got null instead of meetings list");
        for (Meeting plannedMeeting : plannedMeetings)
            if (!plannedMeeting.getStart().isAfter(mEnd) && !plannedMeeting.getEnd().isBefore(mStart))
                validRooms.remove(plannedMeeting.getRoom());
        for (Iterator<MeetingRoom> iterator = validRooms.iterator(); iterator.hasNext(); ) {
            MeetingRoom meetingRoom = iterator.next();
            if (meetingRoom.getCapacity() < mParticipants.size() + 1) // account for owner also
                iterator.remove();
        }
        Collections.sort(validRooms, (o1, o2) -> Integer.compare(o1.getCapacity(), o2.getCapacity()));
        return validRooms;
    }

    public boolean validate() {
        if (mTopic.isEmpty())
            mTopicError = application.getString(R.string.topic_error);
        if (mRoom == null)
            mRoomError = application.getString(R.string.no_meeting_room);
        else if (mParticipants.size() > mRoom.getCapacity())
            mRoomError = application.getString(R.string.room_too_small);
        else if (!mValidRooms.contains(mRoom))
            mRoomError = application.getString(R.string.room_not_free);

        if (mRoomError != null || mParticipantError != null || mTopicError != null || mTimeError != null) {
            viewState.setValue(toViewState());
            return false;
        }
        mMeetingRepo.createMeeting(new Meeting(
                mId,
                application.getString(R.string.phone_owner_email),
                new HashSet<>(mParticipants),
                mTopic,
                mStart,
                mEnd,
                mRoom
        ));
        return true;
    }
}
