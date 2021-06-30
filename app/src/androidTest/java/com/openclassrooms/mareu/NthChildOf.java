package com.openclassrooms.mareu;

import android.view.View;
import android.view.ViewGroup;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class NthChildOf extends TypeSafeMatcher<View> {

    private final Matcher<View> mParentMatcher;
    private final int mChildPosition;

    public NthChildOf(final Matcher<View> parentMatcher, final int childPosition) {
        mParentMatcher = parentMatcher;
        mChildPosition = childPosition;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("with " + mChildPosition + " child view of type parentMatcher");
    }

    @Override
    public boolean matchesSafely(View view) {
        if (!(view.getParent() instanceof ViewGroup)) {
            return mParentMatcher.matches(view.getParent());
        }

        ViewGroup group = (ViewGroup) view.getParent();
        return mParentMatcher.matches(view.getParent()) && group.getChildAt(mChildPosition).equals(view);
    }

}
