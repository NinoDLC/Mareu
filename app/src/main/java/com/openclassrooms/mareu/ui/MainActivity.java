package com.openclassrooms.mareu.ui;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.di.DependencyInjection;
import com.openclassrooms.mareu.model.MeetingRoom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FragmentManager mFragmentManager;
    private int mViewMain;
    private int mViewRight;
    private boolean mLandscapeTablet;
    private MainViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //mViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(MainViewModel.class);
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        setContentView(R.layout.activity_main);
        setSupportActionBar(findViewById(R.id.toolbar));
        mFragmentManager = getSupportFragmentManager();
        mLandscapeTablet = findViewById(R.id.left) != null;

        if (mLandscapeTablet) {
            mViewMain = R.id.left;
            mViewRight = R.id.right;
        } else {
            mViewMain = R.id.main_container;
            mViewRight = mViewMain;
        }
        setFragmentsInitialState();
    }

    @Override
    public void onBackPressed() {
        // or play with addToBackstack in transactions.
        setFragmentsInitialState();
    }

    private void setFragmentsInitialState() {
        mFragmentManager.beginTransaction().replace(mViewMain, new MainFragment(mViewModel), null).commit();
        // todo : pourquoi je ne peux pas faire ça ?
        if (mLandscapeTablet)
            mFragmentManager.beginTransaction().replace(mViewRight, new Fragment() {
            @Nullable
            @Override
            public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
                return inflater.inflate(R.layout.fragment_empty, container, true);
            }
        }, null).commit();
    }

    public void setDetailedViewContent(int id) {
        mFragmentManager.beginTransaction().replace(mViewRight, ShowMeetingFragment.newInstance(id), null).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.filter_date) {
            new DatePickerDialog(this).show();
        } else {
            new FilterDialog(mViewModel.getMeetingRoomNames(), mViewModel.getSelectedRoomsAtLaunch()).show(getSupportFragmentManager(), null);
        }

        return super.onOptionsItemSelected(item);
    }




}

