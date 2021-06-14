package com.openclassrooms.mareu.ui.show;

import androidx.annotation.NonNull;

public class ShowMeetingFragmentViewState {
    private final String mId;
    private final String mOwner;
    private final String mTopic;
    private final String mStartText;
    private final String mEndText;
    private final String mRoomName;
    private final String[] mParticipants;

    public ShowMeetingFragmentViewState(
            @NonNull String id,
            @NonNull String owner,
            @NonNull String topic,
            @NonNull String startText,
            @NonNull String endText,
            @NonNull String roomName,
            @NonNull String[] participants) {
        mId = id;
        mOwner = owner;
        mTopic = topic;
        mStartText = startText;
        mEndText = endText;
        mRoomName = roomName;
        mParticipants = participants;
    }

    @NonNull
    public String getId() {
        return mId;
    }

    @NonNull
    public String getOwner() {
        return mOwner;
    }

    @NonNull
    public String getTopic() {
        return mTopic;
    }

    @NonNull
    public String getStartText() {
        return mStartText;
    }

    @NonNull
    public String getEndText() {
        return mEndText;
    }

    @NonNull
    public String getRoomName() {
        return mRoomName;
    }

    @NonNull
    public String[] getParticipants() {
        return mParticipants;
    }
}
