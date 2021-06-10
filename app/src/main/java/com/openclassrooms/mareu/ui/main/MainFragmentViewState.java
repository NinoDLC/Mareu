package com.openclassrooms.mareu.ui.main;

import androidx.annotation.NonNull;

import java.util.Objects;

public class MainFragmentViewState {
    private final int mId;
    private final String mUpLine;
    private final String mOwner;
    private final String mParticipantsNumber;
    private final String mMeetingRoomName;
    private final int mMeetingRoomColor;

    public MainFragmentViewState(
            int id,
            @NonNull String upLine,
            @NonNull String owner,
            @NonNull String participantsNumber,
            @NonNull String meetingRoomName,
            int meetingRoomColor) {
        mId = id;
        mUpLine = upLine;
        mOwner = owner;
        mParticipantsNumber = participantsNumber;
        mMeetingRoomName = meetingRoomName;
        mMeetingRoomColor = meetingRoomColor;
    }

    public int getId() {
        return mId;
    }

    @NonNull
    public String getUpLine() {
        return mUpLine;
    }

    @NonNull
    public String getOwner() {
        return mOwner;
    }

    @NonNull
    public String getParticipantsNumber() {
        return mParticipantsNumber;
    }

    @NonNull
    public String getMeetingRoomName() {
        return mMeetingRoomName;
    }

    public int getMeetingRoomColor() {
        return mMeetingRoomColor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MainFragmentViewState that = (MainFragmentViewState) o;
        return mId == that.mId &&
                mMeetingRoomColor == that.mMeetingRoomColor &&
                Objects.equals(mUpLine, that.mUpLine) &&
                Objects.equals(mOwner, that.mOwner) &&
                Objects.equals(mParticipantsNumber, that.mParticipantsNumber) &&
                Objects.equals(mMeetingRoomName, that.mMeetingRoomName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mId, mUpLine, mOwner, mParticipantsNumber, mMeetingRoomName, mMeetingRoomColor);
    }

    @NonNull
    @Override
    public String toString() {
        return "MeetingsRecyclerViewAdapterItem{" +
                "mId=" + mId +
                ", mUpLine='" + mUpLine + '\'' +
                ", mOwner='" + mOwner + '\'' +
                ", mParticipantsNumber='" + mParticipantsNumber + '\'' +
                ", mMeetingRoomName='" + mMeetingRoomName + '\'' +
                ", mMeetingRoomColor=" + mMeetingRoomColor +
                '}';
    }
}
