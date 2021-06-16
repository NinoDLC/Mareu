package com.openclassrooms.mareu.repository;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.openclassrooms.mareu.ui.add.AddMeetingFragment;
import com.openclassrooms.mareu.ui.main.MainFragment;
import com.openclassrooms.mareu.ui.show.ShowMeetingFragment;

public class MasterDetailRepository {

    private final MutableLiveData<Integer> currentDetailIdMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<Fragment> masterFragmentMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<Fragment> detailFragmentMutableLiveData = new MutableLiveData<>();

    private final Fragment mMainFragment = MainFragment.newInstance();

    @NonNull
    public LiveData<Integer> getCurrentDetailIdLiveData() {
        return currentDetailIdMutableLiveData;
    }

    // special values :
    //   0 => create
    //  <0 => no detail view
    public void setCurrentId(int id) {
        if (id < 0) masterFragmentMutableLiveData.setValue(mMainFragment);
        else if (id == 0) detailFragmentMutableLiveData.setValue(AddMeetingFragment.newInstance());
        else {
            currentDetailIdMutableLiveData.setValue(id);
            detailFragmentMutableLiveData.setValue(ShowMeetingFragment.newInstance());
        }
    }

    @NonNull
    public LiveData<Fragment> getMasterFragment() {
        return masterFragmentMutableLiveData;
    }

    @NonNull
    public LiveData<Fragment> getDetailFragment() {
        return detailFragmentMutableLiveData;
    }


}
