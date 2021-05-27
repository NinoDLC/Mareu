package com.openclassrooms.mareu.ui;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.model.Meeting;
import com.openclassrooms.mareu.model.MeetingRoom;

import java.time.LocalDateTime;

public class ShowMeetingFragment extends Fragment {

    private static final String MEETING_ID = "MEETING_ID";

    private TextInputEditText mOwner;
    private TextInputEditText mSubject;
    private ChipGroup mParticipantsGroup;
    private TextInputEditText mParticipantsField;
    private Button mStart;
    private Button mEnd;
    private TextInputEditText mRoom;
    private TextView mId;

    public static ShowMeetingFragment newInstance(int id){
        ShowMeetingFragment fragment = new ShowMeetingFragment();
        Bundle args = new Bundle();
        args.putInt(MEETING_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ShowMeetingFragmentViewModel showMeetingActivityViewModel = new ViewModelProvider(this).get(ShowMeetingFragmentViewModel.class);
        View view = inflater.inflate(R.layout.fragment_show_meeting, container, false);

        int meetingId = getArguments().getInt(MEETING_ID, 0);

        Meeting meeting = showMeetingActivityViewModel.getMeetingById(meetingId);
        MeetingRoom meetingRoom = null;
        if (meetingId != 0)
            meetingRoom = showMeetingActivityViewModel.getMeetingRooms().get(meeting.getMeetingRoomId());
        bindAndInitView(view, meeting, meetingRoom);
        return view;
    }

    void bindAndInitView(View view, @Nullable Meeting meeting, @Nullable MeetingRoom meetingRoom) {
        mOwner = view.findViewById(R.id.show_meeting_owner);
        mSubject = view.findViewById(R.id.show_meeting_subject);
        mParticipantsGroup = view.findViewById(R.id.show_meeting_participants_group);
        mParticipantsField = view.findViewById(R.id.show_meeting_participants_field);
        mStart = view.findViewById(R.id.show_meeting_start);
        mEnd = view.findViewById(R.id.show_meeting_end);
        mRoom = view.findViewById(R.id.show_meeting_room);
        mId = view.findViewById(R.id.show_meeting_id);

        int startHour, startMinute, endHour, endMinute;

        if (meeting == null) {
            mOwner.setText("moi");
            mOwner.setActivated(false);
            LocalDateTime roundedNow = LocalDateTime.now().withSecond(0);
            roundedNow = roundedNow.withMinute(roundedNow.getMinute() / 15 * 15 + 15);
            startHour = roundedNow.getHour(); // todo replace with now()
            startMinute = roundedNow.getMinute();
            roundedNow = roundedNow.plusMinutes(30);
            endHour = roundedNow.getHour();
            endMinute = roundedNow.getMinute();
        } else {
            mId.setText(String.valueOf(meeting.getId()));
            mOwner.setText(meeting.getOwner());
            mSubject.setText(meeting.getSubject());
            LayoutInflater layoutInflater = getLayoutInflater();
            for (String participant : meeting.getParticipants()) {
                Chip chip = (Chip) layoutInflater.inflate(R.layout.chip_participant, mParticipantsGroup, false);
                chip.setText(participant);
                mParticipantsGroup.addView(chip, mParticipantsGroup.getChildCount());
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
            newFragment.show(getActivity().getSupportFragmentManager(), "timePicker");
        });
        button.setText(hour + " : " + minute);
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
            return new TimePickerDialog(getActivity(), this, mInitialHour, mInitialMinute, true);
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // todo get TIME in storable format, and set text in nice-to-read format

            mButton.setText(hourOfDay + " : " + minute);
        }
    }

}
