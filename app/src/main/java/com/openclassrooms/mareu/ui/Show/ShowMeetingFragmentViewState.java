package com.openclassrooms.mareu.ui.Show;

public class ShowMeetingFragmentViewState {
    private final String mId;
    private final String mOwner;
    private final String mSubject;
    private final String mStartText;
    private final String mEndText;
    private final String mRoomName;
    private final String[] mParticipants;

    public ShowMeetingFragmentViewState(String id, String owner, String subject, String startText, String endText, String roomName, String[] participants) {
        mId = id;
        mOwner = owner;
        mSubject = subject;
        mStartText = startText;
        mEndText = endText;
        mRoomName = roomName;
        mParticipants = participants;
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
}
