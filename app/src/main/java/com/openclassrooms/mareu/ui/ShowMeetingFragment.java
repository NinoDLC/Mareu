package com.openclassrooms.mareu.ui;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.KeyEvent;
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

public class ShowMeetingFragment extends Fragment {
    private ShowMeetingFragmentViewModel mViewModel;

    private Button mStart;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(ShowMeetingFragmentViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_meeting, container, false);

        mViewModel.getMeeting().observe(requireActivity(), meeting -> {
            // todo: this observation depends on mRoom view, can't go in onCreate()
            bindAndInitView(view, meeting);
        });
        return view;
    }

    void bindAndInitView(View view, Meeting meeting) {

        TextInputEditText owner = view.findViewById(R.id.show_meeting_owner);
        TextInputEditText subject = view.findViewById(R.id.show_meeting_subject);
        ChipGroup participantsGroup = view.findViewById(R.id.show_meeting_participants_group);
        TextInputEditText participantsField = view.findViewById(R.id.show_meeting_participants_field);
        mStart = view.findViewById(R.id.show_meeting_start);
        Button end = view.findViewById(R.id.show_meeting_end);
        TextView id = view.findViewById(R.id.show_meeting_id);
        FloatingActionButton create = view.findViewById(R.id.show_meeting_create);

        owner.setEnabled(meeting.getId() != 0);
        id.setText(String.valueOf(meeting.getId()));
        owner.setText(meeting.getOwner());
        subject.setText(meeting.getSubject());
        LayoutInflater layoutInflater = getLayoutInflater();
        participantsGroup.removeAllViews();
        for (String participant : meeting.getParticipants()) {
            Chip chip = (Chip) layoutInflater.inflate(R.layout.chip_participant, participantsGroup, false);
            chip.setText(participant);
            participantsGroup.addView(chip, participantsGroup.getChildCount());
            chip.setOnCloseIconClickListener(v -> mViewModel.deleteParticipant(participant));
        }

        participantsField.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_ENTER && participantsField.getText() != null) {
                // todo if is valid email?
                mViewModel.newParticipant(participantsField.getText().toString());
                participantsField.setText("");
                return true;
            }
            return false;
        });

        mStart.setText(utils.niceTimeFormat(meeting.getStart()));
        end.setText(utils.niceTimeFormat(meeting.getStop()));
        bindTimeButton(mStart, meeting.getStart().getHour(), meeting.getStart().getMinute());
        bindTimeButton(end, meeting.getStop().getHour(), meeting.getStop().getMinute());

        Button room = view.findViewById(R.id.show_meeting_room);
        room.setText(mViewModel.getMeetingRoomName());
        bindRoomButton(room, mViewModel.getFreeMeetingRooms());

        create.setOnClickListener(v -> {
            // todo if is valid meeting, ...
            ((MainActivity) requireActivity()).onBackPressed();
        });
    }

    void bindRoomButton(Button room, CharSequence[] freeMeetingRooms) {
        room.setOnClickListener(
                view -> new AlertDialog.Builder(getActivity())
                        .setTitle(R.string.free_rooms_dialog_title)
                        .setItems(
                                freeMeetingRooms,
                                (dialog, which) -> mViewModel.setRoom(which)
                        ).create().show()
        );
    }

    void bindTimeButton(Button button, int hour, int minute) {
        button.setOnClickListener(view -> new TimePickerDialog(
                requireContext(),
                (v, h, m) -> mViewModel.timeButtonChanged(button == mStart, h, m),
                hour,
                minute,
                true
        ).show());
        // todo : l'heure de lancement du TimePickerDialog n'est pas mise à jour...
    }
}
