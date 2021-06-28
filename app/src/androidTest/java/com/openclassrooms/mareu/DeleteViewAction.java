package com.openclassrooms.mareu;

import android.view.View;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;

import org.hamcrest.Matcher;

public class DeleteViewAction implements ViewAction {
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
        View button = view.findViewById(R.id.meeting_delete_button);
        button.performClick();
    }
}
