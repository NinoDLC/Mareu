package com.openclassrooms.mareu;

import androidx.lifecycle.Lifecycle;
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
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.swipeUp;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
public class InstrumentedTest {

    public static final String TOPIC = "Topic";
    private ActivityScenario<MainActivity> activityScenario;

    @Before
    public void setUp() {
        activityScenario = ActivityScenario.launch(MainActivity.class);
    }

    @After
    public void tearDown() {
        activityScenario.close();
    }

    @Test
    public void nominalCase() {
        onView(withId(R.id.fragment_main_fab_button)).perform(click());
        onView(withId(R.id.add_meeting_topic_field)).perform(replaceText(TOPIC), closeSoftKeyboard());
        onView(withId(R.id.add_meeting_create)).perform(click());

        // no topic
        onView(withId(R.id.fragment_main_fab_button)).perform(click());
        onView(withId(R.id.add_meeting_create)).perform(click());
        onView(allOf(
                withId(R.id.textinput_error),
                withParent(withParent(withParent(withId(R.id.add_meeting_topic_til))))
        )).check(matches(withText(R.string.topic_error)));

        // very long topic
        onView(withId(R.id.add_meeting_topic_field)).perform(
                replaceText("This is a very long topic that should be cropped before printing"),
                closeSoftKeyboard()
        );

        // add participants
        onView(withId(R.id.add_meeting_participants_field)).perform(replaceText(getEmail(1)));
        onView(withId(R.id.add_meeting_participants_field)).perform(pressImeActionButton());
        onView(withId(R.id.add_meeting_participants_field)).perform(replaceText(getEmail(1)));
        onView(withId(R.id.add_meeting_participants_field)).perform(pressImeActionButton());

        // error displayed
        onView(allOf(
                withId(R.id.textinput_error),
                withParent(withParent(withParent(withId(R.id.add_meeting_participants_til))))
        )).check(matches(withText(R.string.already_an_attendee)));

        // add more participants
        onView(withId(R.id.add_meeting_participants_field)).perform(replaceText(getEmail(2)));
        onView(withId(R.id.add_meeting_participants_field)).perform(pressImeActionButton());
        onView(withId(R.id.add_meeting_participants_field)).perform(replaceText(getEmail(3)));
        onView(withId(R.id.add_meeting_participants_field)).perform(pressImeActionButton());
        onView(withId(R.id.add_meeting_participants_field)).perform(replaceText(getEmail(4)));
        onView(withId(R.id.add_meeting_participants_field)).perform(pressImeActionButton(), closeSoftKeyboard());

        onView(new NthChildOf( withId(R.id.add_meeting_participants_group), 2)).perform(new ClickOnChipEntry());

        //change room and validate
        onView(withId(R.id.add_meeting_start)).perform(swipeUp(), swipeUp(), swipeUp(), swipeUp(), swipeUp());
        onView(withId(R.id.add_meeting_room)).perform(click());
        onView(withText(MeetingRoom.ROOM_9.getName())).perform(scrollTo(), click());
        onView(withId(R.id.add_meeting_create)).perform(click());

        // click on item -> ShowMeetingFragment
        onView(withId(R.id.fragment_main_meeting_list_rv)).perform(actionOnItemAtPosition(1, click()));
        onView(withId(R.id.show_meeting_id)).check(matches(withText("2")));
        onView(withId(R.id.show_meeting_create)).perform(click());

        // delete meetings
        onView(withId(R.id.fragment_main_meeting_list_rv)).perform(actionOnItemAtPosition(0, new DeleteViewAction()));
        onView(withId(R.id.fragment_main_meeting_list_rv)).perform(actionOnItemAtPosition(0, new DeleteViewAction()));

        activityScenario.getState().compareTo(Lifecycle.State.CREATED);

        // createAndFilter
        createMeetingAtEightFifteen(MeetingRoom.ROOM_0);
        createMeetingAtEightFifteen(MeetingRoom.ROOM_1);
        createMeetingAtEightFifteen(MeetingRoom.ROOM_2);
        createMeetingAtEightFifteen(MeetingRoom.ROOM_3);  // Caniche, the default room

        // shouldPrintRoomError()
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
        onView(withText(MeetingRoom.ROOM_4.getName())).perform(scrollTo(), click());
        onView(withId(R.id.add_meeting_create)).perform(click());

        // filter by room
        onView(withId(R.id.filter_room)).perform(click());
        onView(withText(MeetingRoom.ROOM_0.getName())).perform(scrollTo(), click());
        onView(withText(MeetingRoom.ROOM_3.getName())).perform(scrollTo(), click());
        onView(withText(MeetingRoom.ROOM_4.getName())).perform(scrollTo(), click());
        onView(withText(R.string.apply)).perform(click());
        onView(withId(R.id.fragment_main_meeting_list_rv)).check(RecyclerViewItemCountAssertion.withItemCount(2));

        // filter by hour, no result
        onView(withId(R.id.filter_date)).perform(click());
        new PuppetMaterialTP(PuppetMaterialTP.Hours.HOUR14, PuppetMaterialTP.Minutes.MINUTES50);
        onView(withId(R.id.fragment_main_meeting_list_rv)).check(RecyclerViewItemCountAssertion.withItemCount(0));

        // filter by hour, two results
        onView(withId(R.id.filter_date)).perform(click());
        new PuppetMaterialTP(PuppetMaterialTP.Hours.HOUR8, PuppetMaterialTP.Minutes.MINUTES25);
        onView(withId(R.id.fragment_main_meeting_list_rv)).check(RecyclerViewItemCountAssertion.withItemCount(2));

        // reset filters
        onView(withId(R.id.filter_date)).perform(click());
        onView(withId(R.id.material_timepicker_cancel_button)).perform(click());
        onView(withId(R.id.filter_room)).perform(click());
        onView(withText(R.string.reset)).perform(click());
        onView(withId(R.id.fragment_main_meeting_list_rv)).check(RecyclerViewItemCountAssertion.withItemCount(5));
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

    private String getEmail(int x) {
        return String.format("participant%d@domain.com", x);
    }
}
