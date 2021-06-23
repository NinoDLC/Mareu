package com.openclassrooms.mareu.repository;

import androidx.annotation.NonNull;

import com.openclassrooms.mareu.SingleLiveEvent;

public class CurrentIdRepository {

    private final SingleLiveEvent<Integer> currentIdMutableLiveData = new SingleLiveEvent<>();

    @NonNull
    public SingleLiveEvent<Integer> getCurrentIdLiveData() {
        return currentIdMutableLiveData;
    }

    // special values: 0 => create
    public void setCurrentId(int id) {
        currentIdMutableLiveData.setValue(id);
    }

}
