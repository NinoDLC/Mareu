package com.openclassrooms.mareu.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class CurrentIdRepository {

    private final MutableLiveData<Integer> currentIdMutableLiveData = new MutableLiveData<>();

    @NonNull
    public LiveData<Integer> getCurrentIdLiveData() {
        return currentIdMutableLiveData;
    }

    // special values: 0 => create
    public void setCurrentId(int id) {
        currentIdMutableLiveData.setValue(id);
    }

}
