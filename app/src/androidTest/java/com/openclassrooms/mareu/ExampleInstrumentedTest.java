package com.openclassrooms.mareu;

import android.content.Context;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.google.android.material.timepicker.MaterialTimePicker;
import com.openclassrooms.mareu.ui.main.MainActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withResourceName;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    public static final String TOPIC = "Topic";


    @Before
    public void setUp() {
        ActivityScenario<MainActivity> activityScenario = ActivityScenario.launch(MainActivity.class);
    }

    @Test
    public void useAppContext() {
        // create a first meeting
        onView(withId(R.id.fragment_main_fab_button)).perform(click());
        onView(withId(R.id.add_meeting_topic_field)).perform(replaceText(TOPIC), closeSoftKeyboard());
        onView(withId(R.id.add_meeting_create)).perform(click());

        // create a second meeting
        onView(withId(R.id.fragment_main_fab_button)).perform(click());
        onView(withId(R.id.add_meeting_topic_field)).perform(replaceText(TOPIC), closeSoftKeyboard());

        onView(withId(R.id.add_meeting_start)).perform(click());

//        onView(withResourceName("radial_picker")).perform(click());

//        onView(withId(16909213))


        // onView(withText("XYZ")).perform(click());
        // RadialTimePickerView{id=16909213, res-name=radial_picker, visibility=VISIBLE, width=384, height=336, has-focus=false, has-focusable=false, has-window-focus=true, is-clickable=false, is-enabled=true, is-focused=false, is-focusable=false, is-layout-requested=false, is-selected=false, layout-params=android.widget.LinearLayout$LayoutParams@819f653, tag=null, root-is-layout-requested=false, has-input-connection=false, x=24.0, y=168.0}
        onView(
                allOf(
                        withText("" + 12)
                )
        ).perform(click());

        onView(withId(R.id.add_meeting_create)).perform(click());

    }
}
