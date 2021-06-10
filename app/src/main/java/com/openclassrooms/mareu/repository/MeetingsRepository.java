package com.openclassrooms.mareu.repository;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.openclassrooms.mareu.model.Meeting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import static com.openclassrooms.mareu.utils.initMeetings;


public class MeetingsRepository {

    private int mNextMeetingId;
    private final List<Meeting> mMeetings = new ArrayList<>();
    private final MutableLiveData<List<Meeting>> mMeetingListMutableLiveData = new MutableLiveData<>();

    public MeetingsRepository() {
        initMeetings(this);  // Unsorted but valid Meetings list.
        mNextMeetingId = 21;
        sortMeetings();
    }

    private void sortMeetings() {
        Collections.sort(mMeetings, Meeting::compareTo);
    }

    @NonNull
    public LiveData<List<Meeting>> getMeetings() {
        return mMeetingListMutableLiveData;
    }

    public int getNextMeetingId() {
        return ++mNextMeetingId;
    }

    @Nullable
    public Meeting getMeetingById(int id) {
        for (Meeting meeting : mMeetings) {
            if (meeting.getId() == id)
                return meeting;
        }
        return null;
    }

    public boolean createMeeting(@NonNull Meeting meeting) {
        if (!mMeetings.add(meeting)) return false;
        sortMeetings();
        mMeetingListMutableLiveData.setValue(mMeetings);
        return true;
    }

    // not using getMeetingById to be gentler and use that iterator for loop.
    public void removeMeetingById(int meetingId) {
        for (Iterator<Meeting> iterator = mMeetings.iterator(); iterator.hasNext(); ) {
            Meeting meeting = iterator.next();
            if (meeting.getId() == meetingId)
                iterator.remove();
        }
        mMeetingListMutableLiveData.setValue(mMeetings);
    }
}
