package com.openclassrooms.mareu.ui.add;

import android.app.TimePickerDialog;
import android.os.Bundle;
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
import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.ViewModelFactory;

public class AddMeetingFragment extends Fragment {
    private AddMeetingFragmentViewModel mViewModel;

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

    void bindAndInitView(View view, AddMeetingFragmentViewState item, LayoutInflater inflater) {

        TextView owner = view.findViewById(R.id.add_meeting_owner);
        TextInputEditText subject = view.findViewById(R.id.add_meeting_subject_field);
        ChipGroup participantsGroup = view.findViewById(R.id.add_meeting_participants_group);
        TextInputEditText participantsField = view.findViewById(R.id.add_meeting_participants_field);
        Button start = view.findViewById(R.id.add_meeting_start);
        Button end = view.findViewById(R.id.add_meeting_end);
        TextView id = view.findViewById(R.id.add_meeting_id);
        FloatingActionButton create = view.findViewById(R.id.add_meeting_create);
        Button room = view.findViewById(R.id.add_meeting_room);

        id.setText(item.getId());
        owner.setText(item.getOwner());
        subject.setText(item.getSubject());
        start.setText(item.getStartAsText());
        end.setText(item.getEndAsText());
        room.setText(item.getRoomName());

        // todo use addTextChangedListener(), en overridant seulement afterTextChanged()
        // todo setOnFocusChangeListener() is not triggered if I click a button bellow
        subject.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        subject.setOnEditorActionListener((v, actionId, event) -> {
            mViewModel.setSubject(subject.getText());
            return true;
        });

        participantsGroup.removeAllViews();
        for (String participant : item.getParticipants()) {
            Chip chip = (Chip) inflater.inflate(R.layout.chip_participant_add, participantsGroup, false);
            chip.setText(participant);
            participantsGroup.addView(chip, participantsGroup.getChildCount());
            chip.setOnCloseIconClickListener(v -> mViewModel.removeParticipant(participant));
        }

        participantsField.setText(item.getParticipant());
        participantsField.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        participantsField.setOnEditorActionListener((v, actionId, event) -> {
            mViewModel.addParticipant(participantsField.getText());
            return true;
        });

        bindTimeButton(start, item.getStartHour(), item.getStartMinute(), true);
        bindTimeButton(end, item.getEndHour(), item.getEndMinute(), false);
        bindRoomButton(room, item.getFreeMeetingRoomNames());

        create.setOnClickListener(v -> {
            if (mViewModel.validate()) requireActivity().onBackPressed();
        });
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
