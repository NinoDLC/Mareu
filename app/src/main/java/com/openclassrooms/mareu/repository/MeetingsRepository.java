package com.openclassrooms.mareu.repository;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.openclassrooms.mareu.model.Meeting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;


public class MeetingsRepository {

    private int mNextMeetingId;
    private final List<Meeting> mMeetings = new ArrayList<>();
    private final MutableLiveData<List<Meeting>> mMeetingListMutableLiveData = new MutableLiveData<>();

    @NonNull
    public LiveData<List<Meeting>> getMeetings() {
        return mMeetingListMutableLiveData;
    }

    public int getNextMeetingId() {
        return ++mNextMeetingId;
    }

    // Todo à retirer
    @Nullable
    public Meeting getMeetingById(int id) {
        for (Meeting meeting : mMeetings) {
            if (meeting.getId() == id)
                return meeting;
        }
        return null;
    }

    // Todo à retirer
    @Nullable
    public LiveData<Meeting> getMeetingLiveDataById(int id) {
        for (Meeting meeting : mMeetings) {
            if (meeting.getId() == id)
                return meeting;
        }
        return null;
    }

    public void createMeeting(@NonNull Meeting meeting) {
        mMeetings.add(meeting);
        // TODO arnaud à bouger dans le VM
        Collections.sort(mMeetings, (o1, o2) -> o1.getStart().compareTo(o2.getStart()));
        mMeetingListMutableLiveData.setValue(mMeetings);
    }

    // not using getMeetingById() to be gentler on resource and use that iterator for loop.
    public void removeMeetingById(int meetingId) {
        for (Iterator<Meeting> iterator = mMeetings.iterator(); iterator.hasNext(); ) {
            Meeting meeting = iterator.next();
            if (meeting.getId() == meetingId)
                iterator.remove();
        }
        mMeetingListMutableLiveData.setValue(mMeetings);
    }
}
