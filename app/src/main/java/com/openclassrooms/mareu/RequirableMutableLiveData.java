package com.openclassrooms.mareu;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

public class RequirableMutableLiveData<T> extends MutableLiveData<T> {

    public RequirableMutableLiveData(T value) {
        super(value);
    }

    public RequirableMutableLiveData() {
        super();
    }

    @NonNull
    public T requireValue() {
        T value = getValue();
        if (value == null) throw new NullPointerException("Value not set");
        else return value;
    }
}
