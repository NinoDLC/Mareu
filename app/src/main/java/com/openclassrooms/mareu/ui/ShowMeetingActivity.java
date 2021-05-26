package com.openclassrooms.mareu.ui;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.model.Meeting;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ShowMeetingActivity extends AppCompatActivity {

    private static final String MEETING_ID = "MEETING_ID";

    private TextInputEditText mOwner;
    private TextInputEditText mSubject;
    private ChipGroup mParticipantsGroup;
    private TextInputEditText mParticipantsField;
    private Button mStart;
    private Button mEnd;
    private TextInputEditText mRoom;
    private TextView mId;

    public ShowMeetingActivity() {
    }

    public static Intent navigate(Context context, int id) {
        Intent intent = new Intent(context, ShowMeetingActivity.class);
        intent.putExtra(MEETING_ID, id);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ShowMeetingActivityViewModel showMeetingActivityViewModel = new ViewModelProvider(this).get(ShowMeetingActivityViewModel.class);
        int meetingId = getIntent().getIntExtra(MEETING_ID, 0);
        Meeting meeting = showMeetingActivityViewModel.getMeetingById(meetingId);

        setContentView(R.layout.activity_show_meeting);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayShowTitleEnabled(true);

        bindAndInitView(meeting);
    }

    void bindAndInitView(@Nullable Meeting meeting){
        mOwner = findViewById(R.id.show_meeting_owner);
        mSubject = findViewById(R.id.show_meeting_subject);
        mParticipantsGroup = findViewById(R.id.show_meeting_participants_group);
        mParticipantsField = findViewById(R.id.show_meeting_participants_field);
        mStart = findViewById(R.id.show_meeting_start);
        mEnd = findViewById(R.id.show_meeting_end);
        mRoom = findViewById(R.id.show_meeting_room);
        mId = findViewById(R.id.show_meeting_id);

        if (meeting == null) {
            mOwner.setText("moi");
            mOwner.setActivated(false);
        }else{
            mId.setText(meeting.getId());
            mOwner.setText(meeting.getOwner());
            mSubject.setText(meeting.getSubject());
            for (String participant:meeting.getParticipants()){
                //Chip chip = new Chip(mParticipantsGroup);
                Log.e("Arnaud", "bindAndInitView");
            }
            mStart.setText(utils.niceTimeFormat(meeting.getStart()));
            mEnd.setText(utils.niceTimeFormat(meeting.getStop()));
        }
        bindButton(mStart, true);
        bindButton(mEnd, false);

    }

    void bindButton(Button button, boolean isStart) {
        button.setOnClickListener(view -> {
            DialogFragment newFragment = new TimePickerFragment(button);
            newFragment.show(getSupportFragmentManager(), "timePicker");
        });
    }

    public static class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

        private final Button mButton;

        public TimePickerFragment(Button button) {
            mButton = button;
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            int hour = 8;
            int minute = 50;

            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // todo get TIME in storable format, and set text in nice-to-read format

            mButton.setText(hourOfDay + " : " + minute);
        }
    }

}
