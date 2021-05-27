package com.openclassrooms.mareu.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.openclassrooms.mareu.R;

public class MainActivity extends AppCompatActivity {

    private FragmentManager mFragmentManager;
    private int mViewMain;
    private int mViewRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        setSupportActionBar(findViewById(R.id.toolbar));
        mFragmentManager = getSupportFragmentManager();

        boolean landscapeTablet = findViewById(R.id.left) != null;

        if (landscapeTablet){
            mViewMain = R.id.left;
            mViewRight = R.id.right;
        } else {
            mViewMain = R.id.main_container;
            // todo init them !
            //  if (MainActivity.this.mSavedInstanceState != null)
            mViewRight = mViewMain;
        }
        mFragmentManager.beginTransaction().replace(mViewMain, new MainFragment(), null).commit();

    }

    public void setDetailedViewContent(int id) {
        //todo j'ai une recursion ? je file un DetailViewListener à un fragment depuis mon DetailViewListener...?
        mFragmentManager.beginTransaction().replace(mViewRight, ShowMeetingFragment.newInstance(id), null).commit();
    }

    public void endDetailedView() {
        mFragmentManager.beginTransaction().replace(mViewMain, new MainFragment(), null).commit();
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