package com.openclassrooms.mareu.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.model.MeetingRoom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class FilterDialog extends DialogFragment {
    private final CharSequence[] mMeetingRoomsNames;
    private final boolean[] mSelectedRoomsAtLaunch;

    public FilterDialog(CharSequence[] meetingRoomsNames, boolean[] selectedRoomsAtLaunch) {
        mMeetingRoomsNames = meetingRoomsNames;
        mSelectedRoomsAtLaunch = selectedRoomsAtLaunch;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        List<Integer> selectedItems = new ArrayList<Integer>();  // Where we track the selected items
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.filter_by_room)
                .setMultiChoiceItems(mMeetingRoomsNames, mSelectedRoomsAtLaunch,
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
                .setPositiveButton("filter", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Log.e("coucou", "onClick: ");
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Log.e("coucou", "onClick: ");
                    }
                });

        return builder.create();
    }


}
