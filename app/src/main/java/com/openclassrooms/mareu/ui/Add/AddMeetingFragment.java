package com.openclassrooms.mareu.ui.Add;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

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
        View view = inflater.inflate(R.layout.fragment_show_meeting, container, false);

        //mViewModel.getAddMeetingFragmentItem().observe(requireActivity(), item -> bindAndInitView(view, item));
        return view;
    }
/*
    void bindAndInitView(View view, AddMeetingFragmentItem item) {

        TextView owner = view.findViewById(R.id.show_meeting_owner);
        TextInputEditText subject = view.findViewById(R.id.show_meeting_subject);
        ChipGroup participantsGroup = view.findViewById(R.id.show_meeting_participants_group);
        TextInputEditText participantsField = view.findViewById(R.id.show_meeting_participants_field);
        Button start = view.findViewById(R.id.show_meeting_start);
        Button end = view.findViewById(R.id.show_meeting_end);
        TextView id = view.findViewById(R.id.show_meeting_id);
        FloatingActionButton create = view.findViewById(R.id.show_meeting_create);
        Button room = view.findViewById(R.id.show_meeting_room);

        id.setText(item.getId());
        owner.setText(item.getOwner());
        subject.setText(item.getSubject());
        start.setText(item.getStartText());
        end.setText(item.getEndText());
        room.setText(item.getRoomName());

        // todo use addTextChangedListener(), en overridant seulement afterTextChanged()
        // todo setOnFocusChangeListener() is not triggered if I click a button bellow
        subject.setOnEditorActionListener((v, actionId, event) -> {
            mViewModel.setSubject(subject.getText());
            return true;
        });

        LayoutInflater layoutInflater = getLayoutInflater();
        participantsGroup.removeAllViews();
        for (String participant : item.getParticipants()) {
            Chip chip = (Chip) layoutInflater.inflate(R.layout.chip_participant, participantsGroup, false);
            chip.setText(participant);
            participantsGroup.addView(chip, participantsGroup.getChildCount());
            chip.setOnCloseIconClickListener(v -> mViewModel.removeParticipant(participant));
        }

        participantsField.setText(item.getParticipant());
        participantsField.setImeOptions(EditorInfo.IME_ACTION_DONE);
        participantsField.setOnEditorActionListener((v, actionId, event) -> {
            mViewModel.addParticipant(participantsField.getText());
            return true;
        });

        bindTimeButton(start, item.getStartHour(), item.getStartMinute(), true);
        bindTimeButton(end, item.getEndHour(), item.getEndMinute(), false);
        bindRoomButton(room, item.getMeetingRooms());

        create.setOnClickListener(v -> {
            if (mViewModel.validate()) requireActivity().onBackPressed();
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

    void bindTimeButton(Button button, int hour, int minute, boolean isStartButton) {
        button.setOnClickListener(view -> new TimePickerDialog(
                requireContext(),
                (v, h, m) -> mViewModel.setTime(isStartButton, h, m),
                hour,
                minute,
                true
        ).show());
    }

 */
}
