package com.openclassrooms.mareu;

import android.view.View;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;

public class PuppetMaterialTP {

    public static enum Hours {
        HOUR2(2),
        HOUR4(4),
        HOUR6(6),
        HOUR8(8),
        HOUR10(10),
        HOUR12(12),
        HOUR14(14),
        HOUR16(16),
        HOUR18(18),
        HOUR20(20),
        HOUR22(22),
        HOUR24(24);

        private final int hour;

        Hours(int hour) {
            this.hour = hour;
        }

        public int getHour() {
            return hour;
        }
    }

    public static enum Minutes {
        MINUTES0(0),
        MINUTES5(5),
        MINUTES10(10),
        MINUTES15(15),
        MINUTES20(20),
        MINUTES25(25),
        MINUTES30(30),
        MINUTES35(35),
        MINUTES40(40),
        MINUTES45(45),
        MINUTES50(50),
        MINUTES55(55);

        private final int minutes;

        Minutes(int minutes) {
            this.minutes = minutes;
        }

        public int getMinutes() {
            return minutes;
        }
    }

    public PuppetMaterialTP(PuppetMaterialTP.Hours hours, PuppetMaterialTP.Minutes minutes) {
        onView(
                allOf(
                        withText("" + hours.getHour()),
                        withParent(withId(R.id.material_clock_face))
                )
        ).perform(click());
        onView(
                allOf(
                        withText("" + minutes.getMinutes()),
                        withParent(withId(R.id.material_clock_face))
                )
        ).perform(click());
        onView(withId(R.id.material_timepicker_ok_button)).perform(click());
    }
}
