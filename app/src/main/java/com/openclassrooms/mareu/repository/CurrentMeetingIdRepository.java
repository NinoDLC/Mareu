package com.openclassrooms.mareu.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class CurrentMeetingIdRepository {

    /* todo: when making it a livedata, can't observe it from view model...
        so we push it up to the view, and back down to viewModel? meh.
        https://stackoverflow.com/questions/47515997/observing-livedata-from-viewmodel */
    private final MutableLiveData<Integer> currentIdMutableLiveData = new MutableLiveData<>();

    public LiveData<Integer> getCurrentIdMutableLiveData() {
        return currentIdMutableLiveData;
    }

    public void setCurrentId(int id) {
        currentIdMutableLiveData.setValue(id);
    }

}
