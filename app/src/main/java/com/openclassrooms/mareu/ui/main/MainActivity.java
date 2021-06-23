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

        setContentView(R.layout.basic_activity);
        setSupportActionBar(findViewById(R.id.toolbar));

        if (savedInstanceState == null)
            getSupportFragmentManager()
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.master, MainFragment.newInstance(), null)
                    .commit();

        MainActivityViewModel viewModel = ViewModelFactory.getInstance().create(MainActivityViewModel.class);

        //todo : problème, là, c'est que mon observer (viewModel)
        // change avec le lifecycle android. Le nouveau récupère donc la valeur.
        // @Override onInactive()

        viewModel.eventIsShowMeeting().observe(this, bool -> {
            Intent intent = new Intent(MainActivity.this, bool ? ShowMeetingActivity.class : AddMeetingActivity.class);
            startActivity(intent);
        });
    }
}

