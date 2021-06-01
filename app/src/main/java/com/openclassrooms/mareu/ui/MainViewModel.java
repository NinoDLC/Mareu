package com.openclassrooms.mareu.ui;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.mareu.model.Meeting;
import com.openclassrooms.mareu.model.MeetingRoom;
import com.openclassrooms.mareu.repository.MeetingsRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class MainViewModel extends ViewModel {

    private final MeetingsRepository mRepository;
    // todo : repo is to be injected, for this I must customize my factory
    //  but it will only hand me the repo, and I made a DI that can return the instance...
    //  then remove all setvalues()

    private final MutableLiveData<List<Meeting>> mMutableMeetingsLiveData = new MutableLiveData<>();

    private final HashMap<Integer, MeetingRoom> mMeetingRooms;

    private final boolean[] mSelectedRooms;


    public MainViewModel(@NonNull MeetingsRepository meetingsRepository) {
        mRepository = meetingsRepository;

        mMeetingRooms = mRepository.getMeetingRooms();
        mSelectedRooms = new boolean[mMeetingRooms.size()];
        resetRoomFilter();
    }

    public LiveData<List<Meeting>> getMeetingsLiveData() {
        return mMutableMeetingsLiveData;
    }

    public HashMap<Integer, MeetingRoom> getMeetingRooms() {
        return mMeetingRooms;
    }

    public CharSequence[] getMeetingRoomNames() {
        List<CharSequence> meetingRoomNames = new ArrayList<>();
        for (MeetingRoom meetingRoom : mMeetingRooms.values()) {
            meetingRoomNames.add(meetingRoom.getName());
        }
        return meetingRoomNames.toArray(new CharSequence[0]);
    }

    public boolean[] getSelectedRooms() {
        return mSelectedRooms;
    }

    public void toggleRoomSelection(int position) {
        mSelectedRooms[position] = !mSelectedRooms[position];
        updateMeetingsList();
    }

    public void resetRoomFilter() {
        Arrays.fill(mSelectedRooms, true);
        updateMeetingsList();
    }

    protected void deleteButtonClicked(int id) {
        mRepository.removeMeetingById(id);
        updateMeetingsList();
    }

    private void updateMeetingsList(){
        // TODO new ArrayList no longer necessary
        List<Meeting> meetingList = new ArrayList<>(mRepository.getMeetings());
        for (Iterator<Meeting> iterator = meetingList.iterator(); iterator.hasNext(); ) {
            Meeting meeting = iterator.next();
            if (!mSelectedRooms[meeting.getMeetingRoomId()-1])
                meetingList.remove(meeting);
            else if (false) // todo i.e. DATE condition
                meetingList.remove(meeting);
        }
        mMutableMeetingsLiveData.setValue(new ArrayList<>(meetingList));
    }



}
