package com.openclassrooms.mareu.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.di.DependencyInjection;
import com.openclassrooms.mareu.model.Meeting;
import com.openclassrooms.mareu.repository.MeetingsRepository;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    private MainViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        setSupportActionBar(findViewById(R.id.toolbar));

        findViewById(R.id.fab).setOnClickListener(
                view -> Snackbar.make(
                        view,
                        "Create a new meeting",
                        Snackbar.LENGTH_SHORT).show()
        );

        RecyclerView recyclerView = findViewById(R.id.meeting_list);
        MeetingsRecyclerViewAdapter adapter = new MeetingsRecyclerViewAdapter();
        recyclerView.setAdapter(adapter);

        mViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(MainViewModel.class);
        mViewModel.getViewLiveData().observe(this, new Observer<List<Meeting>>() {
            @Override
            public void onChanged(List<Meeting> meetings) {
                adapter.submitList(meetings);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            mViewModel.buttonClicked();
        }

        return super.onOptionsItemSelected(item);
    }

}