package com.openclassrooms.mareu;

import android.view.View;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;

import com.google.android.material.chip.Chip;

import org.hamcrest.Matcher;

public class ClickOnChipEntry implements ViewAction {
    @Override
    public Matcher<View> getConstraints() {
        return null;
    }

    @Override
    public String getDescription() {
        return "Click on delete button";
    }

    @Override
    public void perform(UiController uiController, View view) {
        try {
            Chip chip = (Chip) view;
            chip.performCloseIconClick();
        } catch (ClassCastException e) {
            e.printStackTrace();
            throw new AssertionError("View is not a Chip " + view.getClass());
        }
    }
}
