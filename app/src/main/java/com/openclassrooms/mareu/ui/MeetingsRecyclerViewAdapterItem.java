package com.openclassrooms.mareu.ui;

import java.util.Objects;

// TODO this is a viewstate !
public class MeetingsRecyclerViewAdapterItem {
    private final int mId;
    private final String mUpLine;
    private final String mOwner;
    private final String mParticipantsNumber;
    private final String mMeetingRoomName;
    private final int mMeetingRoomColor;

    public MeetingsRecyclerViewAdapterItem(int id, String upLine, String owner, String participantsNumber, String meetingRoomName, int meetingRoomColor) {
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

    public String getUpLine() {
        return mUpLine;
    }

    public String getOwner() {
        return mOwner;
    }

    public String getParticipantsNumber() {
        return mParticipantsNumber;
    }

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
        MeetingsRecyclerViewAdapterItem that = (MeetingsRecyclerViewAdapterItem) o;
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
