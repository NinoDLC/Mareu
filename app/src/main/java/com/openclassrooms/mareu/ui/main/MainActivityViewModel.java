package com.openclassrooms.mareu.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.mareu.repository.CurrentIdRepository;

public class MainActivityViewModel extends ViewModel {

    private final LiveData<Boolean> showMeeting;

    public MainActivityViewModel(CurrentIdRepository currentIdRepository) {
        showMeeting = Transformations.map(
                currentIdRepository.getCurrentDetailIdLiveData(),
                input -> input != 0
        );
    }

    public LiveData<Boolean> getShowMeeting() {
        return showMeeting;
    }
}
