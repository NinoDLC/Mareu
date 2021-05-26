package com.openclassrooms.mareu.ui;

import androidx.lifecycle.ViewModel;

import com.openclassrooms.mareu.di.DependencyInjection;
import com.openclassrooms.mareu.model.Meeting;
import com.openclassrooms.mareu.model.MeetingRoom;
import com.openclassrooms.mareu.repository.MeetingsRepository;

import java.util.HashMap;

public class ShowMeetingActivityViewModel extends ViewModel {

    private final MeetingsRepository mRepository = DependencyInjection.getMeetingsRepository();

    private final HashMap<Integer, MeetingRoom> mMeetingRooms = mRepository.getMeetingRooms();

    public Meeting getMeetingById(int id){
        return mRepository.getMeetingById(id);
    }

    public HashMap<Integer, MeetingRoom> getMeetingRooms(){
        return mMeetingRooms;
    }


}
