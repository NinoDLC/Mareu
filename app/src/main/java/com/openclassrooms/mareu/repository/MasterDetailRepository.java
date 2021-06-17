package com.openclassrooms.mareu.repository;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.openclassrooms.mareu.ui.main.MainFragment;

public class MasterDetailRepository {

    private final MutableLiveData<Integer> currentDetailIdMutableLiveData = new MutableLiveData<>();

    @NonNull
    public LiveData<Integer> getCurrentDetailIdLiveData() {
        return currentDetailIdMutableLiveData;
    }

    public void setCurrentId(int id) {
        currentDetailIdMutableLiveData.setValue(id);
    }

}
