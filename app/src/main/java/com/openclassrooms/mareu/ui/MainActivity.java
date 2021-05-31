package com.openclassrooms.mareu.ui;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.ViewModelFactory;

public class MainActivity extends AppCompatActivity {

    private FragmentManager mFragmentManager;
    private int mViewMain;
    private int mViewRight;
    private boolean mLandscapeTablet;
    private MainViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(MainViewModel.class);

        setContentView(R.layout.activity_main);
        setSupportActionBar(findViewById(R.id.toolbar));
        mFragmentManager = getSupportFragmentManager();
        mLandscapeTablet = findViewById(R.id.left) != null;

        if (mLandscapeTablet) {
            mViewMain = R.id.master;
            mViewRight = R.id.detail;
        } else {
            mViewMain = R.id.master;
            mViewRight = mViewMain;
        }
        setFragmentsInitialState();
    }

    @Override
    public void onBackPressed() {
        // todo play with addToBackstack in transactions.
        setFragmentsInitialState();
    }

    private void setFragmentsInitialState() {
        mFragmentManager.beginTransaction().replace(mViewMain, new MainFragment(this, mViewModel), null).commit();
        if (mLandscapeTablet)
            mFragmentManager.beginTransaction().replace(mViewRight, new Fragment(R.layout.fragment_empty), null).commit();
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
        if (item.getItemId() == R.id.filter_date) {
            new DatePickerDialog(this).show();
        } else {
            new FilterDialog(mViewModel).show(getSupportFragmentManager(), null);
        }
        return super.onOptionsItemSelected(item);
    }
}

