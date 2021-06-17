package com.openclassrooms.mareu.model;

import androidx.annotation.NonNull;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Meeting {

    private final int mId;

    @NonNull
    private final String mOwner;

    @NonNull
    private final Set<String> mParticipants;

    @NonNull
    private final String mTopic;

    @NonNull
    private final LocalDateTime mStart;

    @NonNull
    private final LocalDateTime mEnd;

    @NonNull
    private final MeetingRoom mRoom;

    public Meeting(int id, @NonNull String owner, @NonNull HashSet<String> participants, @NonNull String topic,
                   @NonNull LocalDateTime start, @NonNull LocalDateTime end, @NonNull MeetingRoom room) {
        this.mId = id;
        this.mOwner = owner;
        this.mParticipants = Collections.unmodifiableSet(participants);
        this.mTopic = topic;
        this.mStart = start;
        this.mEnd = end;
        this.mRoom = room;
    }

    public int getId() {
        return mId;
    }

    @NonNull
    public String getOwner() {
        return mOwner;
    }

    @NonNull
    public Set<String> getParticipants() {
        return mParticipants;
    }

    @NonNull
    public String getTopic() {
        return mTopic;
    }

    @NonNull
    public LocalDateTime getStart() {
        return mStart;
    }

    @NonNull
    public LocalDateTime getEnd() {
        return mEnd;
    }

    @NonNull
    public MeetingRoom getRoom() {
        return mRoom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meeting meeting = (Meeting) o;
        return mId == meeting.mId &&
                mRoom == meeting.mRoom &&
                Objects.equals(mOwner, meeting.mOwner) &&
                Objects.equals(mParticipants, meeting.mParticipants) &&
                Objects.equals(mTopic, meeting.mTopic) &&
                Objects.equals(mStart, meeting.mStart) &&
                Objects.equals(mEnd, meeting.mEnd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mId, mOwner, mParticipants, mTopic, mStart, mEnd, mRoom);
    }

    @NonNull
    @Override
    public String toString() {
        return "Meeting{" +
                "mId=" + mId +
                ", mOwner='" + mOwner + '\'' +
                ", mParticipants=" + mParticipants +
                ", mTopic='" + mTopic + '\'' +
                ", mStart=" + mStart +
                ", mEnd=" + mEnd +
                ", mMeetingRoomId=" + mRoom +
                '}';
    }
}
