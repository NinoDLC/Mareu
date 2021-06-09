package com.openclassrooms.mareu;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.openclassrooms.mareu.ui.add.AddMeetingFragment;
import com.openclassrooms.mareu.ui.main.MainFragment;
import com.openclassrooms.mareu.ui.show.ShowMeetingFragment;

public class MainActivity extends AppCompatActivity {

    private FragmentManager mFragmentManager;
    private boolean mLandscapeTabletLayout;
    private int mViewMaster;
    private int mViewDetail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        setSupportActionBar(findViewById(R.id.toolbar));

        mFragmentManager = getSupportFragmentManager();
        mLandscapeTabletLayout = findViewById(R.id.detail) != null;
        mViewMaster = R.id.master;
        mViewDetail = mLandscapeTabletLayout ? R.id.detail : mViewMaster;

        if (savedInstanceState == null) setFragmentsInitialState();
    }

    @Override
    public void onBackPressed() {
        setFragmentsInitialState();
    }
    // todo: actually not so great design on big tablets in portrait mode
    // todo: play with addToBackstack in transactions.
    //  (when coming back from detailFragment, filters are discarded... meh.)

    private void setFragmentsInitialState() {
        mFragmentManager.beginTransaction().replace(mViewMaster, new MainFragment(), null).commit();
        if (mLandscapeTabletLayout)
            mFragmentManager.beginTransaction().replace(mViewDetail, new Fragment(R.layout.fragment_empty), null).commit();
    }

    public void showInDetail() {
        mFragmentManager.beginTransaction().replace(mViewDetail, new ShowMeetingFragment(), null).commit();
    }

    public void newInDetail() {
        mFragmentManager.beginTransaction().replace(mViewDetail, new AddMeetingFragment(), null).commit();
    }

}

