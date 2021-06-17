package com.openclassrooms.mareu.ui.main;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.ViewModelFactory;
import com.openclassrooms.mareu.ui.add.AddMeetingActivity;
import com.openclassrooms.mareu.ui.show.ShowMeetingActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        setSupportActionBar(findViewById(R.id.toolbar));

        if (savedInstanceState == null)
            getSupportFragmentManager()
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.master, MainFragment.newInstance(), null)
                    .commit();

        MainActivityViewModel viewModel = ViewModelFactory.getInstance().create(MainActivityViewModel.class);

        viewModel.getShowMeeting().observe(this, bool -> {
            Intent intent = new Intent(MainActivity.this, bool ? ShowMeetingActivity.class : AddMeetingActivity.class);
            startActivity(intent);
        });
    }

    // todo: actually not so great design on big tablets in portrait mode
    // todo: play with addToBackstack in transactions.
    //  (when coming back from detailFragment, filters are discarded... meh.)
}

