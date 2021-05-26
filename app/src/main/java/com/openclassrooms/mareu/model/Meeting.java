package com.openclassrooms.mareu.model;

import java.time.LocalDateTime;
import java.util.HashSet;

public class Meeting implements Comparable<Meeting> {

    private final int mId;
    private final String mOwner;
    private final HashSet<String> mParticipants;
    private final String mSubject;
    private final LocalDateTime mStart;
    private final LocalDateTime mStop;
    private final int mMeetingRoomId;

    public Meeting(int id, String owner, HashSet<String> participants, String subject, LocalDateTime start, LocalDateTime stop, int meetingRoomId) {
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

    public String getOwner() {
        return mOwner;
    }

    public HashSet<String> getParticipants() {
        return mParticipants;
    }

    public String getSubject() {
        return mSubject;
    }

    public LocalDateTime getStart() {
        return mStart;
    }

    public LocalDateTime getStop() {
        return mStop;
    }

    public int getMeetingRoomId() {
        return mMeetingRoomId;
    }

    @Override
    public int compareTo(Meeting o) {
        return mStart.compareTo(o.mStart);
    }
}
