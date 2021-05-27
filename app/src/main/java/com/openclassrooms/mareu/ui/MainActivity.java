package com.openclassrooms.mareu.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.openclassrooms.mareu.R;

public class MainActivity extends AppCompatActivity {

    private FragmentManager mFragmentManager;
    private int mViewMain;
    private int mViewRight;
    private boolean mLandscapeTablet;

    private final Fragment mEmptyFragment = new Fragment() {
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_empty, container, true);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        mFragmentManager.beginTransaction().replace(mViewMain, new MainFragment(), null).commit();
        /* todo : pourquoi je ne peux pas faire ça ?
        if (mLandscapeTablet)
            mFragmentManager.beginTransaction().replace(mViewRight, mEmptyFragment, null).commit();
        */
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

        if (id == R.id.action_settings) {
            //mViewModel.buttonClicked();
            System.out.println("coucou");
        }

        return super.onOptionsItemSelected(item);
    }
}