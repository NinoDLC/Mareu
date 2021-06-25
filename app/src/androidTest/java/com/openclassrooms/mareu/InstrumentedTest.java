package com.openclassrooms.mareu;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.openclassrooms.mareu.ui.main.MainActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class InstrumentedTest {

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

        new PuppetMaterialTP(PuppetMaterialTP.Hours.HOUR8, PuppetMaterialTP.Minutes.MINUTES15);
        //  TimePickerDialog view tree goes not down further than RadialTimePickerView
        //   so we use MaterialTimePicker, a bit better.
        onView(withId(R.id.add_meeting_create)).perform(click());

    }
}
