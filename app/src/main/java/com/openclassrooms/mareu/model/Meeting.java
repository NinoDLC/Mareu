package com.openclassrooms.mareu.model;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
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


    public static class MeetingBuilder {
        private int mId;
        private String mOwner;
        private HashSet<String> mParticipants;  // beware the HashSet
        private String mSubject;
        private LocalDateTime mStart;
        private LocalDateTime mStop;
        private int mMeetingRoomId;

        public MeetingBuilder() {
        }

        public Meeting build() {
            return new Meeting(mId, mOwner, mParticipants, mSubject, mStart, mStop, mMeetingRoomId);
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

        public MeetingBuilder setId(int id) {
            mId = id;
            return this;
        }

        public MeetingBuilder setOwner(String owner) {
            mOwner = owner;
            return this;
        }

        public MeetingBuilder setParticipants(HashSet<String> participants) {
            mParticipants = participants;
            return this;
        }

        public MeetingBuilder setSubject(String subject) {
            mSubject = subject;
            return this;
        }

        public MeetingBuilder setStart(LocalDateTime start) {
            mStart = start;
            return this;
        }

        public MeetingBuilder setStop(LocalDateTime stop) {
            mStop = stop;
            return this;
        }

        public MeetingBuilder setMeetingRoomId(int meetingRoomId) {
            mMeetingRoomId = meetingRoomId;
            return this;
        }
    }
}
