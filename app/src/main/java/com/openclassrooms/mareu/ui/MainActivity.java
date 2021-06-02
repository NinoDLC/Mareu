package com.openclassrooms.mareu.ui;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.ViewModelFactory;

public class MainActivity extends AppCompatActivity {

    private FragmentManager mFragmentManager;
    private int mViewMaster;
    private int mViewDetail;
    private boolean mLandscapeTablet;
    private MainViewModel mViewModel;

    private boolean[] mSelectedRooms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(MainViewModel.class);

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

        mViewModel.getSelectedRooms().observe(
                this, booleans -> {
                    mSelectedRooms = booleans;
                    supportInvalidateOptionsMenu();
                }
        );
    }

    @Override
    public void onBackPressed() {
        // todo play with addToBackstack in transactions.
        setFragmentsInitialState();
    }

    private void setFragmentsInitialState() {
        mFragmentManager.beginTransaction().replace(mViewMaster, new MainFragment(this, mViewModel), null).commit();
        if (mLandscapeTablet)
            mFragmentManager.beginTransaction().replace(mViewDetail, new Fragment(R.layout.fragment_empty), null).commit();
    }

    public void setDetailedViewContent(int id) {
        mFragmentManager.beginTransaction().replace(mViewDetail, ShowMeetingFragment.newInstance(id), null).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public Dialog onCreateDialog() {
        return new AlertDialog.Builder(this)
                .setTitle(R.string.filter_by_room)
                .setMultiChoiceItems(
                        mViewModel.getMeetingRoomNames(),
                        mSelectedRooms,
                        (dialog, which, isChecked) -> mViewModel.toggleRoomSelection(which)
                )
                .setPositiveButton("Apply", (dialog, which) -> {
                })
                .setNegativeButton("Reset", (dialog, id) -> mViewModel.resetRoomFilter())
                .create();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.filter_date).setVisible(true);
        menu.findItem(R.id.filter_room).setVisible(true);
        // todo tint if filtered, use mLandscapeTablet
        menu.findItem(R.id.filter_date).setOnMenuItemClickListener(
                item -> {
                    new DatePickerDialog(MainActivity.this).show();
                    return false;
                }
        );

        menu.findItem(R.id.filter_room).setOnMenuItemClickListener(
                item -> {
                    onCreateDialog().show();
                    return false;
                }
        );

        return super.onPrepareOptionsMenu(menu);
    }
}

