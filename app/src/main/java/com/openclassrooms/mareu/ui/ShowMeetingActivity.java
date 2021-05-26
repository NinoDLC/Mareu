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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.model.Meeting;
import com.openclassrooms.mareu.model.MeetingRoom;

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
        MeetingRoom meetingRoom = showMeetingActivityViewModel.getMeetingRooms().get(meeting.getMeetingRoomId());

        setContentView(R.layout.activity_show_meeting);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        bindAndInitView(meeting, meetingRoom);
    }

    void bindAndInitView(@Nullable Meeting meeting, MeetingRoom meetingRoom){
        mOwner = findViewById(R.id.show_meeting_owner);
        mSubject = findViewById(R.id.show_meeting_subject);
        mParticipantsGroup = findViewById(R.id.show_meeting_participants_group);
        mParticipantsField = findViewById(R.id.show_meeting_participants_field);
        mStart = findViewById(R.id.show_meeting_start);
        mEnd = findViewById(R.id.show_meeting_end);
        mRoom = findViewById(R.id.show_meeting_room);
        mId = findViewById(R.id.show_meeting_id);

        int startHour, startMinute, endHour, endMinute;

        if (meeting == null) {
            mOwner.setText("moi");
            mOwner.setActivated(false);
            startHour = 8; // todo replace with now()
            startMinute = 50;
            endHour = 9;
            endMinute = 40;
        }else{
            mId.setText(String.valueOf(meeting.getId()));
            mOwner.setText(meeting.getOwner());
            mSubject.setText(meeting.getSubject());
            for (String participant:meeting.getParticipants()){
                Chip chip = new Chip(this);
                chip.setText(participant);
                mParticipantsGroup.addView(chip,mParticipantsGroup.getChildCount());
            }
            mStart.setText(utils.niceTimeFormat(meeting.getStart()));
            mEnd.setText(utils.niceTimeFormat(meeting.getStop()));
            mRoom.setText(meetingRoom.getName());
            startHour = meeting.getStart().getHour();
            startMinute = meeting.getStart().getMinute();
            endHour = meeting.getStop().getHour();
            endMinute = meeting.getStop().getMinute();
        }

        bindButton(mStart, startHour, startMinute);
        bindButton(mEnd, endHour, endMinute);
    }

    void bindButton(Button button, int hour, int minute) {
        button.setOnClickListener(view -> {
            DialogFragment newFragment = new TimePickerFragment(button, hour, minute);
            newFragment.show(getSupportFragmentManager(), "timePicker");
        });
    }

    public static class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

        private final Button mButton;
        private final int mInitialHour;
        private final int mInitialMinute;

        public TimePickerFragment(Button button, int initialHour, int initialMinute) {
            mButton = button;
            mInitialHour = initialHour;
            mInitialMinute = initialMinute;
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return new TimePickerDialog(getActivity(), this, mInitialHour, mInitialMinute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // todo get TIME in storable format, and set text in nice-to-read format

            mButton.setText(hourOfDay + " : " + minute);
        }
    }

}
