package com.openclassrooms.mareu.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.mareu.di.DependencyInjection;
import com.openclassrooms.mareu.model.Meeting;
import com.openclassrooms.mareu.repository.MeetingsRepository;

import java.util.List;

public class MainViewModel extends ViewModel {

    private final MeetingsRepository mRepo = DependencyInjection.getMeetingsRepository();
    // todo : repo is to be injected, for this I must customize my factory
    //  but it will only hand me the repo, and I made a DI that can return the instance...

    private final MutableLiveData<List<Meeting>> mMutableMeetingsLiveData = new MutableLiveData<>();

    public MainViewModel() {
        mMutableMeetingsLiveData.setValue(mRepo.getMeetings());
    }

    public LiveData<List<Meeting>> getViewLiveData(){
        return mMutableMeetingsLiveData;
    };


    protected void buttonClicked(){
        // todo : do something


        // mMutableMeetingsLiveData.setValue(mRepo.getMeetings());

    }




}
