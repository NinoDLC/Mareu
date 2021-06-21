package com.openclassrooms.mareu.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.openclassrooms.mareu.model.Meeting;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class MeetingsRepository {

    private int mNextMeetingId;
    private final List<Meeting> mMeetings = new ArrayList<>();
    private final MutableLiveData<List<Meeting>> mMeetingListMutableLiveData = new MutableLiveData<>(mMeetings);

    @NonNull
    public LiveData<List<Meeting>> getMeetings() {
        return mMeetingListMutableLiveData;
    }

    // in fact, createMeeting() should not receive a meeting as argument,
    //  rather every field but the id. And call the Meeting constructor on itself.
    public int getNextMeetingId() {
        return ++mNextMeetingId;
    }

    public void createMeeting(@NonNull Meeting meeting) {
        mMeetings.add(meeting);
        mMeetingListMutableLiveData.setValue(mMeetings);
    }

    public void removeMeetingById(int meetingId) {
        for (Iterator<Meeting> iterator = mMeetings.iterator(); iterator.hasNext(); ) {
            Meeting meeting = iterator.next();
            if (meeting.getId() == meetingId)
                iterator.remove();
        }
        mMeetingListMutableLiveData.setValue(mMeetings);
    }
}
