package com.openclassrooms.mareu.ui.show;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.openclassrooms.mareu.R;

public class ShowMeetingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.basic_activity);
        setSupportActionBar(findViewById(R.id.toolbar));

        if (savedInstanceState == null)
            getSupportFragmentManager()
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.master, ShowMeetingFragment.newInstance(), null)
                    .commit();
    }
}

