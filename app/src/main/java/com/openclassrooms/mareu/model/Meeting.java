package com.openclassrooms.mareu.model;

import androidx.annotation.NonNull;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Meeting implements Comparable<Meeting> {

    private final int mId;
    private final String mOwner;
    private final Set<String> mParticipants;
    private final String mSubject;
    private final LocalDateTime mStart;
    private final LocalDateTime mStop;
    private final int mMeetingRoomId;

    public Meeting(int id, String owner, HashSet<String> participants, String subject, LocalDateTime start, LocalDateTime stop, int meetingRoomId) {
        this.mId = id;
        this.mOwner = owner;
        this.mParticipants = Collections.unmodifiableSet(participants);
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

    public Set<String> getParticipants() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meeting meeting = (Meeting) o;
        return mId == meeting.mId &&
                mMeetingRoomId == meeting.mMeetingRoomId &&
                Objects.equals(mOwner, meeting.mOwner) &&
                Objects.equals(mParticipants, meeting.mParticipants) &&
                Objects.equals(mSubject, meeting.mSubject) &&
                Objects.equals(mStart, meeting.mStart) &&
                Objects.equals(mStop, meeting.mStop);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mId, mOwner, mParticipants, mSubject, mStart, mStop, mMeetingRoomId);
    }

    @NonNull
    @Override
    public String toString() {
        return "Meeting{" +
                "mId=" + mId +
                ", mOwner='" + mOwner + '\'' +
                ", mParticipants=" + mParticipants +
                ", mSubject='" + mSubject + '\'' +
                ", mStart=" + mStart +
                ", mStop=" + mStop +
                ", mMeetingRoomId=" + mMeetingRoomId +
                '}';
    }
}
