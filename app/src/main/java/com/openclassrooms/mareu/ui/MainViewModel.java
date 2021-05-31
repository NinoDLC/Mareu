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
import java.util.List;

public class MainViewModel extends ViewModel {

    private final MeetingsRepository mRepository;
    // todo : repo is to be injected, for this I must customize my factory
    //  but it will only hand me the repo, and I made a DI that can return the instance...
    //  then remove all setvalues()

    private final MutableLiveData<List<Meeting>> mMutableMeetingsLiveData = new MutableLiveData<>();

    private final HashMap<Integer, MeetingRoom> mMeetingRooms;

    private final boolean[] mSelected;


    public MainViewModel(@NonNull MeetingsRepository meetingsRepository) {
        mRepository = meetingsRepository;

        mMeetingRooms = mRepository.getMeetingRooms();
        mSelected = new boolean[mMeetingRooms.size()];
        mMutableMeetingsLiveData.setValue(mRepository.getMeetings());
        resetFilter();
    }

    public LiveData<List<Meeting>> getMeetingsLiveData() {
        return mMutableMeetingsLiveData;
    }

    public HashMap<Integer, MeetingRoom> getMeetingRooms() {
        return mMeetingRooms;
    }

    public CharSequence[] getMeetingRoomNames() {
        List<CharSequence> meetingRoomNames = new ArrayList<CharSequence>();
        for (MeetingRoom meetingRoom : mMeetingRooms.values())
            meetingRoomNames.add(meetingRoom.getName());
        return meetingRoomNames.toArray(new CharSequence[0]);
    }

    public boolean[] getSelectedRooms() {
        return mSelected;
    }

    public void toggleRoomSelection(int position) {
        mSelected[position] = !mSelected[position];
        // todo apply filter
    }

    public void resetFilter() {
        Arrays.fill(mSelected, true);
    }

    protected void deleteButtonClicked(int id) {
        mRepository.removeMeetingById(id);
        // TODO new ArrayList no longer necessary
        mMutableMeetingsLiveData.setValue(new ArrayList<>(mRepository.getMeetings()));
    }

}
