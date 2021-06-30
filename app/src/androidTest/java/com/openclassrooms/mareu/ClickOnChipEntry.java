package com.openclassrooms.mareu;

import android.view.View;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.matcher.ViewMatchers;

import com.google.android.material.chip.Chip;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

import static java.util.regex.Pattern.matches;
import static org.hamcrest.Matchers.isA;

public class ClickOnChipEntry implements ViewAction {
    @Override
    public Matcher<View> getConstraints() {
        return ViewMatchers.isAssignableFrom(Chip.class);
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
