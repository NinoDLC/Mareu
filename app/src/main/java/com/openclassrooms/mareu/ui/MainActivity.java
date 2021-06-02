package com.openclassrooms.mareu.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.openclassrooms.mareu.R;

public class MainActivity extends AppCompatActivity {

    private FragmentManager mFragmentManager;
    private int mViewMaster;
    private int mViewDetail;
    private boolean mLandscapeTablet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        setSupportActionBar(findViewById(R.id.toolbar));
        mFragmentManager = getSupportFragmentManager();
        mLandscapeTablet = findViewById(R.id.detail) != null;

        if (mLandscapeTablet) {
            mViewMaster = R.id.master;
            mViewDetail = R.id.detail;
        } else {
            mViewMaster = R.id.master;
            mViewDetail = mViewMaster;
        }
        setFragmentsInitialState();
    }

    @Override
    public void onBackPressed() {
        // todo play with addToBackstack in transactions.
        setFragmentsInitialState();
    }

    private void setFragmentsInitialState() {
        // todo : MainFragemnt is created from scratch each time : scrollposition and filters are discarded... meh.
        mFragmentManager.beginTransaction().replace(mViewMaster, new MainFragment(this), null).commit();
        if (mLandscapeTablet)
            mFragmentManager.beginTransaction().replace(mViewDetail, new Fragment(R.layout.fragment_empty), null).commit();
    }

    public void setDetailedViewContent(int id) {
        ShowMeetingFragment showMeetingFragment = ShowMeetingFragment.newInstance(id);
        mFragmentManager.beginTransaction().replace(mViewDetail, showMeetingFragment, null).commit();
    }
}

