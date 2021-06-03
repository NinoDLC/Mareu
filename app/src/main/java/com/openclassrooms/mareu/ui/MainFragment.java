package com.openclassrooms.mareu.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.ViewModelFactory;

public class MainFragment extends Fragment {

    private MainActivity mMainActivity;
    private MainFragmentViewModel mViewModel;
    private MeetingsRecyclerViewAdapter mAdapter;
    private boolean[] mSelectedRooms;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMainActivity = (MainActivity) requireActivity();
        mViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(MainFragmentViewModel.class);
        mAdapter = new MeetingsRecyclerViewAdapter(
                new MeetingsRecyclerViewAdapter.Listener() {
                    @Override
                    public void itemClicked(int id) {
                        mMainActivity.setDetailedViewContent(id);
                    }

                    @Override
                    public void deleteButtonClicked(int id) {
                        mViewModel.deleteButtonClicked(id);
                    }
                }
        );

        // todo can't use getViewLifecycleOwner() from onCreate() !
        mViewModel.getMeetingsLiveData().observe(mMainActivity, items -> mAdapter.submitList(items));
        mViewModel.getSelectedRooms().observe(
                mMainActivity, booleans -> {
                    mSelectedRooms = booleans;
                    mMainActivity.supportInvalidateOptionsMenu();
                }
        );
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        setHasOptionsMenu(true);
        view.findViewById(R.id.fab).setOnClickListener(v -> mMainActivity.setDetailedViewContent(0));

        RecyclerView recyclerView = view.findViewById(R.id.meeting_list);
        recyclerView.setAdapter(mAdapter);

        return view;
    }

    // todo tint filter icons if a filter is active ?

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        menu.findItem(R.id.filter_date).setOnMenuItemClickListener(
                item -> {
                    new TimePickerDialog(  // staying away from dates and DatePickerDialog()
                            requireContext(),
                            new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(@NonNull TimePicker view, int hourOfDay, int minute) {
                                    mViewModel.setTimeFilter(hourOfDay, minute);
                                }
                            },
                            8, 30, true).show();    // todo remove magic numbers
                    return false;
                    // todo handle reset
                }
        );

        menu.findItem(R.id.filter_room).setOnMenuItemClickListener(
                item -> {
                    onCreateDialog().show();
                    return false;
                }
        );
    }

    public Dialog onCreateDialog() {
        return new AlertDialog.Builder(requireContext())
                .setTitle(R.string.filter_by_room)
                .setMultiChoiceItems(
                        mViewModel.getMeetingRoomNames(),
                        mSelectedRooms,
                        (dialog, which, isChecked) -> mViewModel.toggleRoomSelection(which)
                )
                .setPositiveButton("Apply", (dialog, which) -> {
                })
                .setNegativeButton("Reset", (dialog, id) -> mViewModel.resetRoomFilter())
                .create();
    }
}
