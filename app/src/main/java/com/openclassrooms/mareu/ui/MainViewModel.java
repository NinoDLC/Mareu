package com.openclassrooms.mareu.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.mareu.di.DependencyInjection;
import com.openclassrooms.mareu.model.Meeting;
import com.openclassrooms.mareu.model.MeetingRoom;
import com.openclassrooms.mareu.repository.MeetingsRepository;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainViewModel extends ViewModel {

    private final MeetingsRepository mRepository = DependencyInjection.getMeetingsRepository();
    // todo : repo is to be injected, for this I must customize my factory
    //  but it will only hand me the repo, and I made a DI that can return the instance...
    //  then remove all setvalues()

    private final MutableLiveData<List<Meeting>> mMutableMeetingsLiveData = new MutableLiveData<>();

    private final HashMap<Integer, MeetingRoom> mMeetingRooms = new HashMap<>();

    public MainViewModel() {
        mMutableMeetingsLiveData.setValue(mRepository.getMeetings());

        // todo this could have been a HashMap from the start, not a list
        for (MeetingRoom meetingRoom : mRepository.getMeetingRooms())
            mMeetingRooms.put(meetingRoom.getId(), meetingRoom);
    }

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
}
