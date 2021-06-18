package com.openclassrooms.mareu.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class CurrentIdRepository {

    // todo: Nino au final, ce repo sert a
    //  - s'épargner des arguments dans newInstance() et navigate()
    //  - l'injection de dépendance dans les tests, autant coté set() que coté get()
    //  - éviter les interfaces pour remonter l'info click si on a le droit d'appeler tout repo depuis tout VM

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
