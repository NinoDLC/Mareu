package com.openclassrooms.mareu.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.mareu.di.DependencyInjection;
import com.openclassrooms.mareu.model.Meeting;
import com.openclassrooms.mareu.model.MeetingRoom;
import com.openclassrooms.mareu.repository.MeetingsRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MainViewModel extends ViewModel {

    private final MeetingsRepository mRepository = DependencyInjection.getMeetingsRepository();
    // todo : repo is to be injected, for this I must customize my factory
    //  but it will only hand me the repo, and I made a DI that can return the instance...
    //  then remove all setvalues()

    private final MutableLiveData<List<Meeting>> mMutableMeetingsLiveData = new MutableLiveData<>();

    private final HashMap<Integer, MeetingRoom> mMeetingRooms = mRepository.getMeetingRooms();

    public MainViewModel() {
        mMutableMeetingsLiveData.setValue(mRepository.getMeetings());
    }

    // todo sort the meetings by starting time ?

    public LiveData<List<Meeting>> getMeetingsLiveData(){
        return mMutableMeetingsLiveData;
    }

    public HashMap<Integer, MeetingRoom> getMeetingRooms(){
        return mMeetingRooms;
    }

    protected void deleteButtonClicked(int id){
        mRepository.removeMeetingById(id);
        mMutableMeetingsLiveData.setValue(mRepository.getMeetings());
    }

    public CharSequence[] getMeetingRoomNames() {
        List<CharSequence> meetingRoomNames = new ArrayList<CharSequence>();
        for (MeetingRoom meetingRoom : mMeetingRooms.values())
            meetingRoomNames.add(meetingRoom.getName());
        return meetingRoomNames.toArray(new CharSequence[0]);
    }

    public boolean[] getSelectedRoomsAtLaunch(){
        boolean[] selected = new boolean[mMeetingRooms.size()];
        Arrays.fill(selected, true);
        return  selected;
    }

}
