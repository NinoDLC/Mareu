package com.openclassrooms.mareu.ui.show;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.ViewModelFactory;

public class ShowMeetingFragment extends Fragment {
    private ShowMeetingFragmentViewModel mViewModel;

    @NonNull
    public static ShowMeetingFragment newInstance() {
        return new ShowMeetingFragment();
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

        mViewModel.getShowMeetingFragmentItem().observe(requireActivity(), item -> bindAndInitView(view, item, inflater));
        return view;
    }

    void bindAndInitView(View view, @NonNull ShowMeetingFragmentViewState item, LayoutInflater inflater) {

        TextView owner = view.findViewById(R.id.show_meeting_owner);
        TextView subject = view.findViewById(R.id.show_meeting_subject);
        ChipGroup participantsGroup = view.findViewById(R.id.show_meeting_participants_group);
        TextView start = view.findViewById(R.id.show_meeting_start);
        TextView end = view.findViewById(R.id.show_meeting_end);
        TextView id = view.findViewById(R.id.show_meeting_id);
        FloatingActionButton create = view.findViewById(R.id.show_meeting_create);
        TextView room = view.findViewById(R.id.show_meeting_room);

        id.setText(item.getId());
        owner.setText(item.getOwner());
        subject.setText(item.getSubject());
        start.setText(item.getStartText());
        end.setText(item.getEndText());
        room.setText(item.getRoomName());

        participantsGroup.removeAllViews();
        for (String participant : item.getParticipants()) {
            Chip chip = (Chip) inflater.inflate(R.layout.chip_participant, participantsGroup, false);
            chip.setText(participant);
            participantsGroup.addView(chip, participantsGroup.getChildCount());
        }

        create.setOnClickListener(v -> requireActivity().onBackPressed());
    }
}
