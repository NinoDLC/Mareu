package com.openclassrooms.mareu.ui.add;

import androidx.annotation.NonNull;

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
    private final String mGeneralError;

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
            String generalError) {
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
        mGeneralError = generalError;
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

    public String getGeneralError() {
        return mGeneralError;
    }
}
