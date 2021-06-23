package com.openclassrooms.mareu.ui.add;

import androidx.annotation.NonNull;

import java.util.Arrays;
import java.util.Objects;

public class AddMeetingFragmentViewState {
    private final String mId;
    private final String mOwner;
    private final String mStartAsText;
    private final String mEndAsText;
    private final String mRoomName;
    private final String[] mParticipants;
    private final int mStartHour;
    private final int mStartMinute;
    private final int mEndHour;
    private final int mEndMinute;
    private final CharSequence[] mFreeMeetingRoomNames;
    private final String mParticipantError;
    private final String mTopicError;
    private final String mTimeError;
    private final String mRoomError;

    public AddMeetingFragmentViewState(
            @NonNull String id,
            @NonNull String owner,
            @NonNull String startAsText,
            @NonNull String endAsText,
            String roomName,
            @NonNull String[] participants,
            int startHour,
            int startMinute,
            int endHour,
            int endMinute,
            CharSequence[] freeMeetingRoomNames,
            String participantError,
            String topicError,
            String timeError,
            String roomError) {
        mId = id;
        mOwner = owner;
        mStartAsText = startAsText;
        mEndAsText = endAsText;
        mRoomName = roomName;
        mParticipants = participants;
        mStartHour = startHour;
        mStartMinute = startMinute;
        mEndHour = endHour;
        mEndMinute = endMinute;
        mFreeMeetingRoomNames = freeMeetingRoomNames;
        mParticipantError = participantError;
        mTopicError = topicError;
        mTimeError = timeError;
        mRoomError = roomError;
    }

    public String getId() {
        return mId;
    }

    public String getOwner() {
        return mOwner;
    }

    public String getStartAsText() {
        return mStartAsText;
    }

    public String getEndAsText() {
        return mEndAsText;
    }

    public String getRoomName() {
        return mRoomName;
    }

    public String[] getParticipants() {
        return mParticipants;
    }

    public int getStartHour() {
        return mStartHour;
    }

    public int getStartMinute() {
        return mStartMinute;
    }

    public int getEndHour() {
        return mEndHour;
    }

    public int getEndMinute() {
        return mEndMinute;
    }

    public CharSequence[] getFreeMeetingRoomNames() {
        return mFreeMeetingRoomNames;
    }

    public String getParticipantError() {
        return mParticipantError;
    }

    public String getTopicError() {
        return mTopicError;
    }

    public String getTimeError() {
        return mTimeError;
    }

    public String getRoomError() {
        return mRoomError;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddMeetingFragmentViewState that = (AddMeetingFragmentViewState) o;
        return mStartHour == that.mStartHour &&
            mStartMinute == that.mStartMinute &&
            mEndHour == that.mEndHour &&
            mEndMinute == that.mEndMinute &&
            Objects.equals(mId, that.mId) &&
            Objects.equals(mOwner, that.mOwner) &&
            Objects.equals(mStartAsText, that.mStartAsText) &&
            Objects.equals(mEndAsText, that.mEndAsText) &&
            Objects.equals(mRoomName, that.mRoomName) &&
            Arrays.equals(mParticipants, that.mParticipants) &&
            Arrays.equals(mFreeMeetingRoomNames, that.mFreeMeetingRoomNames) &&
            Objects.equals(mParticipantError, that.mParticipantError) &&
            Objects.equals(mTopicError, that.mTopicError) &&
            Objects.equals(mTimeError, that.mTimeError) &&
            Objects.equals(mRoomError, that.mRoomError);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(mId, mOwner, mStartAsText, mEndAsText, mRoomName, mStartHour, mStartMinute, mEndHour, mEndMinute, mParticipantError, mTopicError, mTimeError, mRoomError);
        result = 31 * result + Arrays.hashCode(mParticipants);
        result = 31 * result + Arrays.hashCode(mFreeMeetingRoomNames);
        return result;
    }

    @Override
    public String toString() {
        return "AddMeetingFragmentViewState{" +
            "mId='" + mId + '\'' +
            ", mOwner='" + mOwner + '\'' +
            ", mStartAsText='" + mStartAsText + '\'' +
            ", mEndAsText='" + mEndAsText + '\'' +
            ", mRoomName='" + mRoomName + '\'' +
            ", mParticipants=" + Arrays.toString(mParticipants) +
            ", mStartHour=" + mStartHour +
            ", mStartMinute=" + mStartMinute +
            ", mEndHour=" + mEndHour +
            ", mEndMinute=" + mEndMinute +
            ", mFreeMeetingRoomNames=" + Arrays.toString(mFreeMeetingRoomNames) +
            ", mParticipantError='" + mParticipantError + '\'' +
            ", mTopicError='" + mTopicError + '\'' +
            ", mTimeError='" + mTimeError + '\'' +
            ", mRoomError='" + mRoomError + '\'' +
            '}';
    }
}
