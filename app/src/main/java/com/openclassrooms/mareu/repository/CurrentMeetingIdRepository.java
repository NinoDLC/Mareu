package com.openclassrooms.mareu.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class CurrentMeetingIdRepository {
    private final MutableLiveData<Integer> currentIdMutableLiveData = new MutableLiveData<>();

    @NonNull
    public LiveData<Integer> getCurrentIdMutableLiveData() {
        return currentIdMutableLiveData;
    }

    public void setCurrentId(int id) {
        currentIdMutableLiveData.setValue(id);
    }
}
