package com.openclassrooms.mareu.ui;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.ViewModelFactory;

public class MainFragment extends Fragment {

    private final MainActivity mMainActivity;
    private MainFragmentViewModel mViewModel;

    private boolean[] mSelectedRooms;

    private final MeetingsRecyclerViewAdapter.Listener mListener = new MeetingsRecyclerViewAdapter.Listener() {
        @Override
        public void itemClicked(int id) {
            // todo : replace my mMainActivity with an interface with setDetailedViewContent ?
            mMainActivity.setDetailedViewContent(id);
        }

        @Override
        public void deleteButtonClicked(int id) {
            mViewModel.deleteButtonClicked(id);
        }
    };

    public MainFragment(MainActivity mainActivity) {
        mMainActivity = mainActivity;
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        view.findViewById(R.id.fab).setOnClickListener(v -> mMainActivity.setDetailedViewContent(0));

        mViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(MainFragmentViewModel.class);

        RecyclerView recyclerView = view.findViewById(R.id.meeting_list);
        MeetingsRecyclerViewAdapter adapter = new MeetingsRecyclerViewAdapter(mListener, mViewModel.getMeetingRooms());
        recyclerView.setAdapter(adapter);

        mViewModel.getMeetingsLiveData().observe(requireActivity(), adapter::submitList);

        mViewModel.getSelectedRooms().observe(
                getViewLifecycleOwner(), booleans -> {
                    mSelectedRooms = booleans;
                    mMainActivity.supportInvalidateOptionsMenu();
                }
        );
        return view;
    }

    // todo tint filter icons if a filter is active ?

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.filter_date).setOnMenuItemClickListener(
                item -> {
                    new DatePickerDialog(requireContext()).show();
                    return false;
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
