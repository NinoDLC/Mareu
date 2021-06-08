package com.openclassrooms.mareu.ui.Add;

public class AddMeetingFragmentViewState {
    private final String mId;
    private final String mOwner;
    private final String mSubject;
    private final String mStartAsText;
    private final String mEndAsText;
    private final String mRoomName;
    private final String mParticipant;
    private final String[] mParticipants;
    private final int mStartHour;
    private final int mStartMinute;
    private final int mEndHour;
    private final int mEndMinute;
    private final CharSequence[] mFreeMeetingRoomNames;

    public AddMeetingFragmentViewState(String id, String owner, String subject, String startAsText, String endAsText, String roomName, String participant,
                                       String[] participants, int startHour, int startMinute, int endHour, int endMinute, CharSequence[] freeMeetingRoomNames) {
        mId = id;
        mOwner = owner;
        mSubject = subject;
        mStartAsText = startAsText;
        mEndAsText = endAsText;
        mRoomName = roomName;
        mParticipant = participant;
        mParticipants = participants;
        mStartHour = startHour;
        mStartMinute = startMinute;
        mEndHour = endHour;
        mEndMinute = endMinute;
        mFreeMeetingRoomNames = freeMeetingRoomNames;
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

    public String getStartAsText() {
        return mStartAsText;
    }

    public String getEndAsText() {
        return mEndAsText;
    }

    public String getRoomName() {
        return mRoomName;
    }

    public String getParticipant() {
        return mParticipant;
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
}
