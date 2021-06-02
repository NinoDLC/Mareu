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

import static java.util.Arrays.copyOf;

public class MainFragmentViewModel extends ViewModel {

    private final MeetingsRepository mRepository;
    // todo : repo is to be injected, for this I must customize my factory
    //  but it will only hand me the repo, and I made a DI that can return the instance...
    //  then remove all setvalues()

    private final MutableLiveData<List<Meeting>> mMutableMeetingsLiveData = new MutableLiveData<>();

    private final HashMap<Integer, MeetingRoom> mMeetingRooms;

    private final boolean[] mSelectedRooms;

    private final MutableLiveData<boolean[]> mSelectedRoomsLiveData = new MutableLiveData<>();

    public MainFragmentViewModel(@NonNull MeetingsRepository meetingsRepository) {
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

    public LiveData<boolean[]> getSelectedRooms() {
        return mSelectedRoomsLiveData;
    }

    public void toggleRoomSelection(int position) {
        mSelectedRooms[position] = !mSelectedRooms[position];
        mSelectedRoomsLiveData.setValue(copyOf(mSelectedRooms, mSelectedRooms.length));
        updateMeetingsList();
    }

    public void resetRoomFilter() {
        Arrays.fill(mSelectedRooms, true);
        mSelectedRoomsLiveData.setValue(copyOf(mSelectedRooms, mSelectedRooms.length));
        updateMeetingsList();
    }

    protected void deleteButtonClicked(int id) {
        mRepository.removeMeetingById(id);
        updateMeetingsList();
    }

    private void updateMeetingsList(){
        // TODO new ArrayList no longer necessary when repo is liveData.
        List<Meeting> meetingList = new ArrayList<>();
        for (Meeting meeting : mRepository.getMeetings()) {
            if (mSelectedRooms[meeting.getMeetingRoomId()-1]) // todo add date condition
                meetingList.add(meeting);
        }
        mMutableMeetingsLiveData.setValue(new ArrayList<>(meetingList));
    }



}
