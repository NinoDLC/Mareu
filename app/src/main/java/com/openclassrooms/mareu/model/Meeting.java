package com.openclassrooms.mareu.model;

import android.provider.ContactsContract.CommonDataKinds.Email;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

public class Meeting {

    private final int mId;
    private final Email mOwner;
    private final HashSet<Email> mParticipants;
    private final String mSubject;
    private final Date mStart;
    private final Date mStop;
    private final int mMeetingRoomId;

    private Meeting(int id, Email owner, HashSet<Email> participants, String subject, Date start, Date stop, int meetingRoomId) {
        this.mId = id;
        this.mOwner = owner;
        this.mParticipants = participants;
        this.mSubject = subject;
        this.mStart = start;
        this.mStop = stop;
        this.mMeetingRoomId = meetingRoomId;
    }

    public int getId() {
        return mId;
    }

    public Email getOwner() {
        return mOwner;
    }

    public HashSet<Email> getParticipants() {
        return mParticipants;
    }

    public String getSubject() {
        return mSubject;
    }

    public Date getStart() {
        return mStart;
    }

    public Date getStop() {
        return mStop;
    }

    public int getMeetingRoomId() {
        return mMeetingRoomId;
    }
}
