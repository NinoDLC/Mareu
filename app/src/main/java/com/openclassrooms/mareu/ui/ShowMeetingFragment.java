package com.openclassrooms.mareu.ui;

import android.app.Dialog;
import android.app.TimePickerDialog;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
    private FloatingActionButton mCreate;

    public static ShowMeetingFragment newInstance(int id){
        ShowMeetingFragment fragment = new ShowMeetingFragment();
        Bundle args = new Bundle();
        args.putInt(MEETING_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ShowMeetingFragmentViewModel showMeetingActivityViewModel = new ViewModelProvider(this).get(ShowMeetingFragmentViewModel.class);
        View view = inflater.inflate(R.layout.fragment_show_meeting, container, false);

        int meetingId =  requireArguments().getInt(MEETING_ID);

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
        mCreate = view.findViewById(R.id.show_meeting_create);

        int startHour, startMinute, endHour, endMinute;

        if (meeting == null || meetingRoom ==null) {
            mOwner.setText(R.string.user_email_adress);
            mOwner.setEnabled(false);

            LocalDateTime roundedNow = LocalDateTime.now().withSecond(0);
            roundedNow = roundedNow.withMinute(roundedNow.getMinute() / 15 * 15).plusMinutes(15);
            startHour = roundedNow.getHour();
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

        mCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // todo if is valid meeting, ...
                //  ShowMeetingFragment.this.finish();
                ((MainActivity) requireActivity()).onBackPressed();
            }
        });
    }

    void bindButton(Button button, int hour, int minute) {
        button.setOnClickListener(view -> {
            DialogFragment timePickerFragment = new TimePickerFragment(button, hour, minute);
            timePickerFragment.show(requireActivity().getSupportFragmentManager(), "timePicker");
        });
        button.setText(String.format("%02d", hour) + "h" + String.format("%02d", minute));
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

            mButton.setText(String.format("%02d", hourOfDay) + "h" + String.format("%02d", minute));
        }
    }
}
