package com.openclassrooms.mareu.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class CurrentMeetingIdRepository {
    private final MutableLiveData<Integer> currentIdMutableLiveData = new MutableLiveData<>();

    public LiveData<Integer> getCurrentIdMutableLiveData() {
        return currentIdMutableLiveData;
    }

    public void setCurrentId(int id) {
        currentIdMutableLiveData.setValue(id);
    }
}
