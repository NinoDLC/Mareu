package com.openclassrooms.mareu.ui.add;

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
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
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
        mViewModel.getViewState().observe(getViewLifecycleOwner(), item -> bindAndInitView(view, item, inflater));
        return view;
    }

    void bindAndInitView(View view, @NonNull AddMeetingFragmentViewState item, LayoutInflater inflater) {

        TextView owner = view.findViewById(R.id.add_meeting_owner);
        TextInputEditText topic = view.findViewById(R.id.add_meeting_topic_field);
        ChipGroup participantsGroup = view.findViewById(R.id.add_meeting_participants_group);
        TextInputEditText participantsField = view.findViewById(R.id.add_meeting_participants_field);
        Button start = view.findViewById(R.id.add_meeting_start);
        Button end = view.findViewById(R.id.add_meeting_end);
        TextView id = view.findViewById(R.id.add_meeting_id);
        FloatingActionButton create = view.findViewById(R.id.add_meeting_create);
        Button room = view.findViewById(R.id.add_meeting_room);
        TextInputLayout topicTil = view.findViewById(R.id.add_meeting_topic_til);
        TextInputLayout participantTil = view.findViewById(R.id.add_meeting_participants_til);
        TextView timeError = view.findViewById(R.id.time_error);
        TextView roomError = view.findViewById(R.id.room_error);

        id.setText(item.getId());
        owner.setText(item.getOwner());
        start.setText(item.getStartAsText());
        end.setText(item.getEndAsText());
        room.setText(item.getRoomName());
        timeError.setText(item.getTimeError());
        roomError.setText(item.getRoomError());

        topic.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        topicTil.setError(item.getTopicError());
        topic.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mViewModel.setTopic(s.toString().trim());
            }
        });

        participantsField.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        participantsField.setOnEditorActionListener((v, actionId, event) -> {
            Editable ed = participantsField.getText();
            if (ed != null && mViewModel.addParticipant(ed.toString()))
                participantsField.getText().clear();
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

        create.setOnClickListener(v -> {
            if (mViewModel.validate()) requireActivity().onBackPressed();
        });
    }

    void bindRoomButton(@NonNull Button room, @NonNull CharSequence[] freeMeetingRooms) {
        room.setOnClickListener(
                view -> new AlertDialog.Builder(requireActivity())
                        .setTitle(R.string.free_rooms_dialog_title)
                        .setItems(
                                freeMeetingRooms,
                                (dialog, which) -> mViewModel.setRoom(which)
                        ).create().show()
        );
    }

    void bindTimeButton(@NonNull Button button, int hour, int minute, boolean isStartButton) {
        button.setOnClickListener(v -> {
            MaterialTimePicker picker = new MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_24H)
                    .setHour(hour)
                    .setMinute(minute)
                    .setTitleText(isStartButton ? getString(R.string.set_start_time) : getString(R.string.set_end_time))
                    .build();
            picker.addOnPositiveButtonClickListener(
                    view -> mViewModel.setTime(isStartButton, picker.getHour(), picker.getMinute())
            );
            picker.show(getParentFragmentManager(), "my tag");
        });
    }
}
