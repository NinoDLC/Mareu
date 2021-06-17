package com.openclassrooms.mareu.ui.add;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.ui.show.ShowMeetingFragment;

public class AddMeetingActivity extends AppCompatActivity {


    //todo: Nino si t'as qu'un activity XML tout con mais que tu fais plusieurs activity java,
    // c'est pour peupler la back-stack?

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        setSupportActionBar(findViewById(R.id.toolbar));

        if (savedInstanceState == null)
            getSupportFragmentManager()
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.master, AddMeetingFragment.newInstance(), null)
                    .commit();
    }
}
