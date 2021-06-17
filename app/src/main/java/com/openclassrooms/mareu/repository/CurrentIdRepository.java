package com.openclassrooms.mareu.repository;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.openclassrooms.mareu.ui.main.MainFragment;

public class CurrentIdRepository {

    // todo au final, ce repo sert a
    //  - s'épargner des arguments dans newInstance() et navigate()
    //  - l'injection de dépendance dans les tests, autant coté set() que coté get()
    //  mais on se tape quand meme les interfaces pour remonter l'info click

    private final MutableLiveData<Integer> currentDetailIdMutableLiveData = new MutableLiveData<>();

    @NonNull
    public LiveData<Integer> getCurrentDetailIdLiveData() {
        return currentDetailIdMutableLiveData;
    }

    // special values: 0 => create
    public void setCurrentId(int id) {
        currentDetailIdMutableLiveData.setValue(id);
    }

}
