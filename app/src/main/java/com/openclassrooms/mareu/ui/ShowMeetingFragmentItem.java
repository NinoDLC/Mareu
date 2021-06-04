package com.openclassrooms.mareu.ui;

public class ShowMeetingFragmentItem {
    private final String mId;
    private final String mOwner;
    private final String mSubject;
    private final String mStartText;
    private final String mEndText;
    private final String mRoomName;
    private final String[] mParticipants;
    private final int mStartHour;
    private final int mStartMinute;
    private final int mEndHour;
    private final int mEndMinute;
    private final CharSequence[] mMeetingRooms;


    public ShowMeetingFragmentItem(String id, String owner, String subject, String startText, String endText, String roomName,
                                   String[] participants, int startHour, int startMinute, int endHour, int endMinute, CharSequence[] meetingRooms) {
        mId = id;
        mOwner = owner;
        mSubject = subject;
        mStartText = startText;
        mEndText = endText;
        mRoomName = roomName;
        mParticipants = participants;
        mStartHour = startHour;
        mStartMinute = startMinute;
        mEndHour = endHour;
        mEndMinute = endMinute;
        mMeetingRooms = meetingRooms;
    }

    public String getId() {
        return mId;
    }

    public String getOwner() {
        return mOwner;
    }

    public String getSubject() {
        return mSubject;
    }

    public String getStartText() {
        return mStartText;
    }

    public String getEndText() {
        return mEndText;
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

    public CharSequence[] getMeetingRooms() {
        return mMeetingRooms;
    }
}
