package com.openclassrooms.mareu.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.openclassrooms.mareu.R;

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
        mViewDetail = mLandscapeTabletLayout ?R.id.detail:mViewMaster;

        // todo: actually not so great design on big tablets in portrait mode

        setFragmentsInitialState();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        // todo: rotation is not handled yet
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        setFragmentsInitialState();
    }

    private void setFragmentsInitialState() {
        // todo: play with addToBackstack in transactions.
        // todo: MainFragment is created from scratch each time : scroll position and filters are discarded... meh.
        // todo: the moment I stop reloading master when detail exits, I dan't see added meetings anymore...
        mFragmentManager.beginTransaction().replace(mViewMaster, new MainFragment(), null).commit();
        if (mLandscapeTabletLayout)
            mFragmentManager.beginTransaction().replace(mViewDetail, new Fragment(R.layout.fragment_empty), null).commit();
    }

    public void setDetailedViewContent() {
        mFragmentManager.beginTransaction().replace(mViewDetail, new ShowMeetingFragment(), null).commit();
    }
}

