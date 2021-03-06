package com.openclassrooms.mareu.ui.main;

import android.app.AlertDialog;
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

import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.ViewModelFactory;

public class MainFragment extends Fragment {

    private MainFragmentViewModel mViewModel;
    private MainFragmentAdapter mAdapter;
    private boolean[] mSelectedRooms;

    @NonNull
    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        setHasOptionsMenu(true);

        mViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(MainFragmentViewModel.class);
        mAdapter = new MainFragmentAdapter(
                new MainFragmentAdapter.Listener() {
                    @Override
                    public void itemClicked(int id) {
                        mViewModel.setDetailId(id);
                    }

                    @Override
                    public void deleteButtonClicked(int id) {
                        mViewModel.deleteButtonClicked(id);
                    }
                }
        );

        view.findViewById(R.id.fragment_main_fab_button).setOnClickListener(v -> mViewModel.setDetailId(0));

        RecyclerView recyclerView = view.findViewById(R.id.fragment_main_meeting_list_rv);
        recyclerView.setAdapter(mAdapter);

        mViewModel.getViewStateListLiveData().observe(getViewLifecycleOwner(), items -> mAdapter.submitList(items));
        mViewModel.getRoomFilter().observe(getViewLifecycleOwner(), booleans -> {
                    mSelectedRooms = booleans;
                    requireActivity().invalidateOptionsMenu();
                }
        );

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
    }

    /*
    // code to tint tint filter icons if a filter is active. cumbersome.
    tintIcon(R.drawable.ic_baseline_location_on_24, mViewModel.getRoomFilterColor());
    public void tintIcon(int resId, int color) {
        Context context = requireContext();
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), resId, context.getTheme());
        assert drawable != null;
        drawable.setTint(ContextCompat.getColor(context, color));
    }
    */

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        menu.findItem(R.id.filter_date).setOnMenuItemClickListener(v -> {
                    MaterialTimePicker picker = new MaterialTimePicker.Builder()
                            .setTimeFormat(TimeFormat.CLOCK_24H)
                            .setHour(mViewModel.getTimeFilterHour())
                            .setMinute(mViewModel.getTimeFilterMinute())
                            .setTitleText(R.string.set_filter_time)
                            .build();
                    picker.addOnPositiveButtonClickListener(view -> mViewModel.setTimeFilter(picker.getHour(), picker.getMinute()));
                    picker.addOnNegativeButtonClickListener(view -> mViewModel.resetTimeFilter());
                    picker.show(getParentFragmentManager(), "my tag");
                    return true;
                });
        menu.findItem(R.id.filter_room).setOnMenuItemClickListener(
                item -> {
                    new AlertDialog.Builder(requireContext())
                            .setTitle(R.string.filter_by_room)
                            .setMultiChoiceItems(
                                    mViewModel.getMeetingRoomNames(),
                                    mSelectedRooms,
                                    (dialog, which, isChecked) -> mViewModel.setRoomFilter(which, isChecked)
                            )
                            .setPositiveButton(R.string.apply, (dialog, which) -> {
                            })
                            .setNegativeButton(R.string.reset, (dialog, id) -> mViewModel.resetRoomFilter())
                            .create().show();
                    return false;
                }
        );
    }
}
