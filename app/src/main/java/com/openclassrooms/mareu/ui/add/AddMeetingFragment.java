package com.openclassrooms.mareu.ui.add;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.ViewModelFactory;

public class AddMeetingFragment extends Fragment {
    private AddMeetingFragmentViewModel mViewModel;

    public static AddMeetingFragment newInstance() {
        return new AddMeetingFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(AddMeetingFragmentViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_meeting, container, false);
        mViewModel.getAddMeetingFragmentItem().observe(requireActivity(), item -> bindAndInitView(view, item, inflater));
        return view;
    }

    void bindAndInitView(@NonNull View view, @NonNull AddMeetingFragmentViewState item, @NonNull LayoutInflater inflater) {

        TextView owner = view.findViewById(R.id.add_meeting_owner);
        TextInputEditText subject = view.findViewById(R.id.add_meeting_subject_field);
        ChipGroup participantsGroup = view.findViewById(R.id.add_meeting_participants_group);
        TextInputEditText participantsField = view.findViewById(R.id.add_meeting_participants_field);
        Button start = view.findViewById(R.id.add_meeting_start);
        Button end = view.findViewById(R.id.add_meeting_end);
        TextView id = view.findViewById(R.id.add_meeting_id);
        FloatingActionButton create = view.findViewById(R.id.add_meeting_create);
        Button room = view.findViewById(R.id.add_meeting_room);
        TextInputLayout subjectTil = view.findViewById(R.id.add_meeting_subject_til);
        TextInputLayout participantTil = view.findViewById(R.id.add_meeting_participants_til);
        TextView error = view.findViewById(R.id.general_error);

        id.setText(item.getId());
        owner.setText(item.getOwner());
        start.setText(item.getStartAsText());
        end.setText(item.getEndAsText());
        room.setText(item.getRoomName());
        error.setText(item.getGeneralError());

        subject.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        subjectTil.setError(item.getSubjectError());
        subject.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mViewModel.setSubject(s.toString().trim());
            }
        });

        participantsField.setImeOptions(EditorInfo.IME_ACTION_DONE);
        participantsField.setOnEditorActionListener(
                (v, actionId, event) -> {
                    Editable ed = participantsField.getText();
                    if (ed != null && mViewModel.addParticipant(ed.toString()))
                        participantsField.setText(null);
                    return true;
                });
        participantTil.setError(item.getParticipantError());

        participantsGroup.removeAllViews();
        for (String participant : item.getParticipants()) {
            Chip chip = (Chip) inflater.inflate(R.layout.chip_participant_add, participantsGroup, false);
            chip.setText(participant);
            participantsGroup.addView(chip, participantsGroup.getChildCount());
            chip.setOnCloseIconClickListener(v -> mViewModel.removeParticipant(participant));
        }

        bindTimeButton(start, item.getStartHour(), item.getStartMinute(), true);
        bindTimeButton(end, item.getEndHour(), item.getEndMinute(), false);
        bindRoomButton(room, item.getFreeMeetingRoomNames());

        create.setOnClickListener(v -> mViewModel.validate());
    }

    void bindRoomButton(Button room, CharSequence[] freeMeetingRooms) {
        room.setOnClickListener(
                view -> new AlertDialog.Builder(requireActivity())
                        .setTitle(R.string.free_rooms_dialog_title)
                        .setItems(
                                freeMeetingRooms,
                                (dialog, which) -> mViewModel.setRoom(which)
                        ).create().show()
        );
    }

    void bindTimeButton(Button button, int hour, int minute, boolean isStartButton) {
        button.setOnClickListener(view -> new TimePickerDialog(
                requireContext(),
                (v, h, m) -> mViewModel.setTime(isStartButton, h, m),
                hour,
                minute,
                true
        ).show());
    }
}
