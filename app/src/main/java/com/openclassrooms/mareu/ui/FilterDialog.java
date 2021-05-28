package com.openclassrooms.mareu.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.openclassrooms.mareu.R;

import java.util.ArrayList;
import java.util.List;


public class FilterDialog extends DialogFragment {
    private final MainViewModel mMainViewModel;

    public FilterDialog(MainViewModel viewModel) {
        mMainViewModel = viewModel;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        List<Integer> selectedItems = new ArrayList<>();  // Where we track the selected items
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.filter_by_room)
                .setMultiChoiceItems(mMainViewModel.getMeetingRoomNames(), mMainViewModel.getSelectedRooms(),
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                if (isChecked) {
                                    selectedItems.add(which);
                                } else if (selectedItems.contains(which)) {
                                    selectedItems.remove(Integer.valueOf(which));
                                }
                            }
                        })
                .setPositiveButton("Apply", (dialog, which) -> {
                })
                .setNegativeButton("Reset", (dialog, id) -> mMainViewModel.resetFilter());
        return builder.create();
    }
}
