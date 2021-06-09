package com.openclassrooms.mareu.ui.add;

import android.text.Editable;

public class AddMeetingFragmentViewState {
    private final String mId;
    private final String mOwner;
    private final String mSubject;
    private final String mStartAsText;
    private final String mEndAsText;
    private final String mRoomName;
    private final Editable mParticipant;
    private final String[] mParticipants;
    private final int mStartHour;
    private final int mStartMinute;
    private final int mEndHour;
    private final int mEndMinute;
    private final CharSequence[] mFreeMeetingRoomNames;
    private final String mParticipantError;
    private final String mSubjectError;
    private final String mGeneralError;

    // todo : lesquels sont nullable ?
    public AddMeetingFragmentViewState(String id, String owner, String subject, String startAsText, String endAsText, String roomName, Editable participant,
                                       String[] participants, int startHour, int startMinute, int endHour, int endMinute, CharSequence[] freeMeetingRoomNames,
                                       String participantError, String subjectError, String generalError) {
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
        mParticipantError = participantError;
        mSubjectError = subjectError;
        mGeneralError = generalError;
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

    public Editable getParticipant() {
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

    public String getParticipantError() {
        return mParticipantError;
    }

    public String getSubjectError() {
        return mSubjectError;
    }

    public String getGeneralError() {
        return mGeneralError;
    }
}
