package com.openclassrooms.mareu;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.openclassrooms.mareu.model.MeetingRoom;
import com.openclassrooms.mareu.ui.main.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.swipeUp;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class InstrumentedTest {

    public static final String TOPIC = "Topic";
    private ActivityScenario<MainActivity> activityScenario;

    @Before
    public void setUp() {
        activityScenario = ActivityScenario.launch(MainActivity.class);
    }

    @After
    public void tearDown(){
        activityScenario.close();
    }

    @Test
    public void nominalCase() {
        onView(withId(R.id.fragment_main_fab_button)).perform(click());
        onView(withId(R.id.add_meeting_topic_field)).perform(replaceText(TOPIC), closeSoftKeyboard());
        onView(withId(R.id.add_meeting_create)).perform(click());
    }

    @Test
    public void veryLongTopic() {
        onView(withId(R.id.fragment_main_fab_button)).perform(click());
        onView(withId(R.id.add_meeting_topic_field)).perform(
                replaceText("This is a very long topic that should be cropped before printing"),
                closeSoftKeyboard()
        );
        onView(withId(R.id.add_meeting_create)).perform(click());
    }

    @Test
    public void createAndFilter() {
        createMeetingAtEightFifteen(MeetingRoom.ROOM_0);
        createMeetingAtEightFifteen(MeetingRoom.ROOM_1);
        createMeetingAtEightFifteen(MeetingRoom.ROOM_2);
        createMeetingAtEightFifteen(MeetingRoom.ROOM_3);
    }

    @Test
    public void shouldPrintRoomError() {
        createMeetingAtEightFifteen(MeetingRoom.ROOM_3);  // Caniche, the default room
        onView(withId(R.id.fragment_main_fab_button)).perform(click());
        onView(withId(R.id.add_meeting_topic_field)).perform(replaceText(TOPIC), closeSoftKeyboard());

        // make meeting happen an other time so we can select Caniche room
        onView(withId(R.id.add_meeting_start)).perform(click());
        new PuppetMaterialTP(PuppetMaterialTP.Hours.HOUR10, PuppetMaterialTP.Minutes.MINUTES10);
        onView(withId(R.id.add_meeting_end)).perform(click());
        new PuppetMaterialTP(PuppetMaterialTP.Hours.HOUR6, PuppetMaterialTP.Minutes.MINUTES40);
        onView(withId(R.id.time_error)).check(matches(withText(R.string.stop_before_start)));

        // suppress stop before start error
        onView(withId(R.id.add_meeting_end)).perform(click());
        new PuppetMaterialTP(PuppetMaterialTP.Hours.HOUR12, PuppetMaterialTP.Minutes.MINUTES5);
        onView(withId(R.id.time_error)).check(matches(withText("")));

        onView(withId(R.id.add_meeting_start)).perform(click());
        new PuppetMaterialTP(PuppetMaterialTP.Hours.HOUR8, PuppetMaterialTP.Minutes.MINUTES15);
        onView(withId(R.id.add_meeting_end)).perform(click());
        new PuppetMaterialTP(PuppetMaterialTP.Hours.HOUR8, PuppetMaterialTP.Minutes.MINUTES45);
        onView(withId(R.id.add_meeting_end)).perform(swipeUp(), swipeUp());

        onView(withId(R.id.add_meeting_create)).perform(click());
        onView(withId(R.id.room_error)).check(matches(withText(R.string.room_not_free)));

        // suppress room error
        onView(withId(R.id.add_meeting_room)).perform(click());
        onView(withText(MeetingRoom.ROOM_1.getName())).perform(scrollTo(), click());
        onView(withId(R.id.add_meeting_create)).perform(click());
    }

    void createMeetingAtEightFifteen(MeetingRoom room) {
        onView(withId(R.id.fragment_main_fab_button)).perform(click());
        onView(withId(R.id.add_meeting_topic_field)).perform(replaceText(TOPIC), closeSoftKeyboard());
        onView(withId(R.id.add_meeting_start)).perform(click());
        new PuppetMaterialTP(PuppetMaterialTP.Hours.HOUR8, PuppetMaterialTP.Minutes.MINUTES15);
        onView(withId(R.id.add_meeting_end)).perform(click());
        new PuppetMaterialTP(PuppetMaterialTP.Hours.HOUR8, PuppetMaterialTP.Minutes.MINUTES45);
        onView(withId(R.id.add_meeting_end)).perform(swipeUp(), swipeUp());
        onView(withId(R.id.add_meeting_room)).perform(click());
        // scrollTo() won't work with NestedScrollView.
        // https://stackoverflow.com/questions/35272953/espresso-scrolling-not-working-when-nestedscrollview-or-recyclerview-is-in-coor
        onView(withText(room.getName())).perform(scrollTo(), click());
        onView(withId(R.id.add_meeting_create)).perform(click());
    }
}
