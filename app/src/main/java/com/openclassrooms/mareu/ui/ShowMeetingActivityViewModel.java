package com.openclassrooms.mareu.ui;

import androidx.lifecycle.ViewModel;

import com.openclassrooms.mareu.di.DependencyInjection;
import com.openclassrooms.mareu.model.Meeting;
import com.openclassrooms.mareu.repository.MeetingsRepository;

public class ShowMeetingActivityViewModel extends ViewModel {

    private final MeetingsRepository mRepository = DependencyInjection.getMeetingsRepository();

    public Meeting getMeetingById(int id){
        return mRepository.getMeetingById(id);
    }


}
