package com.openclassrooms.mareu.ui;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.ViewModelFactory;
import com.openclassrooms.mareu.model.Meeting;
import com.openclassrooms.mareu.model.MeetingRoom;

public class ShowMeetingFragment extends Fragment {

    private static final String MEETING_ID = "MEETING_ID";
    private ShowMeetingFragmentViewModel mViewModel;

    private Button mStart;
    private Button mRoom;

    public static ShowMeetingFragment newInstance(int id) {
        ShowMeetingFragment fragment = new ShowMeetingFragment();
        Bundle args = new Bundle();
        args.putInt(MEETING_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(ShowMeetingFragmentViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_meeting, container, false);

        Meeting meeting = mViewModel.requireMeetingById(requireArguments().getInt(MEETING_ID));
        MeetingRoom meetingRoom = mViewModel.getMeetingRooms().get(meeting.getMeetingRoomId());
        bindAndInitView(view, meeting, meetingRoom);
        return view;
    }

    void bindAndInitView(View view, Meeting meeting, @Nullable MeetingRoom meetingRoom) {
        TextInputEditText owner = view.findViewById(R.id.show_meeting_owner);
        TextInputEditText subject = view.findViewById(R.id.show_meeting_subject);
        ChipGroup participantsGroup = view.findViewById(R.id.show_meeting_participants_group);
        TextInputEditText participantsField = view.findViewById(R.id.show_meeting_participants_field);
        mStart = view.findViewById(R.id.show_meeting_start);
        Button end = view.findViewById(R.id.show_meeting_end);
        mRoom = view.findViewById(R.id.show_meeting_room);
        TextView id = view.findViewById(R.id.show_meeting_id);
        FloatingActionButton create = view.findViewById(R.id.show_meeting_create);

        if (meeting.getId() == 0) {
            owner.setEnabled(false);
        }
        id.setText(String.valueOf(meeting.getId()));
        owner.setText(meeting.getOwner());
        subject.setText(meeting.getSubject());
        LayoutInflater layoutInflater = getLayoutInflater();
        for (String participant : meeting.getParticipants()) {
            Chip chip = (Chip) layoutInflater.inflate(R.layout.chip_participant, participantsGroup, false);
            chip.setText(participant);
            participantsGroup.addView(chip, participantsGroup.getChildCount());
        }
        mStart.setText(utils.niceTimeFormat(meeting.getStart()));
        end.setText(utils.niceTimeFormat(meeting.getStop()));
        mRoom.setText(meetingRoom != null ? meetingRoom.getName() : "");

        bindTimeButton(mStart, meeting.getStart().getHour(), meeting.getStart().getMinute());
        bindTimeButton(end, meeting.getStop().getHour(), meeting.getStop().getMinute());

        mViewModel.getFreeMeetingRooms().observe(
                requireActivity(),
                freeMeetingRooms -> mRoom.setOnClickListener(
                        v -> new AlertDialog.Builder(getActivity())
                                .setTitle("Free rooms list to pick from")
                                .setItems(
                                        freeMeetingRooms,
                                        (dialog, which) -> mRoom.setText(mViewModel.selectRoom(which))
                                ).create().show()
                )
        );

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // todo if is valid meeting, ...
                //  ShowMeetingFragment.this.finish();
                ((MainActivity) requireActivity()).onBackPressed();
            }
        });
    }

    void bindTimeButton(Button button, int hour, int minute) {
        button.setOnClickListener(view -> new TimePickerDialog(
                requireContext(),
                (v, hourOfDay, minute1) -> button.setText(
                        mViewModel.timeButtonChanged(button == mStart, hour, minute1)),
                hour, minute, true).show());
    }
}
