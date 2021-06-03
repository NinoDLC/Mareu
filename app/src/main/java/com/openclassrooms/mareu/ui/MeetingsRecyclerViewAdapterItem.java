package com.openclassrooms.mareu.ui;

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
}
