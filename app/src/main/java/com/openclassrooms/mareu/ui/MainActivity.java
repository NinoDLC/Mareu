package com.openclassrooms.mareu.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.model.Meeting;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    private MainViewModel mViewModel;

    private final MeetingsRecyclerViewAdapter.Listener mListener = new MeetingsRecyclerViewAdapter.Listener() {
        @Override
        public void itemClicked(int id) {
            Log.e("Arnaud", "itemClicked: " + id);
        }

        @Override
        public void deleteButtonClicked(int id) {
            mViewModel.deleteButtonClicked(id);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        setSupportActionBar(findViewById(R.id.toolbar));

        //mViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(MainViewModel.class);
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        findViewById(R.id.fab).setOnClickListener(
                    view -> startActivity(new Intent(MainActivity.this, ShowMeetingActivity.class))
        );

        RecyclerView recyclerView = findViewById(R.id.meeting_list);
        // todo bellow line is overkill (XML): remove it
        //  recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MeetingsRecyclerViewAdapter adapter = new MeetingsRecyclerViewAdapter(mListener, mViewModel.getMeetingRooms());
        recyclerView.setAdapter(adapter);

        mViewModel.getMeetingsLiveData().observe(this, new Observer<List<Meeting>>() {
            @Override
            public void onChanged(List<Meeting> meetings) {
                Log.e("Livedata", "onChanged(), " + meetings.size());
                adapter.submitList(meetings);
            }
        });
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