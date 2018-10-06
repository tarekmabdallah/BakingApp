package com.gmail.tarekmabdallah91.bakingapp;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.gmail.tarekmabdallah91.bakingapp.activities.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.hasChildCount;
import static android.support.test.espresso.matcher.ViewMatchers.hasContentDescription;
import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.TWO;
import static com.gmail.tarekmabdallah91.bakingapp.utils.BakingConstants.ZERO;

@RunWith(AndroidJUnit4.class)
public class TestMainActivityRecyclerView {


    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);


    /**
     * check when click in the 1st item in rv_recipes
     * it should open DetailsActivity
     * then there are 2 recycler view
     * 1- image_recipes and the image hasContentDescription.
     * 2- steps_rv and it has 2 childes (ingredients and steps)
     * TODO to check the toolbar text onView(withId(R.id.toolbar)).check(matches(withText("Nutella Pie"))); doesn't work !
     */
    @Test
    public void clickRecyclerViewItem_openDetailsActivity() {
        onView(withId(R.id.rv_recipes)).perform(actionOnItemAtPosition(ZERO, click()));

        onView(withId(R.id.image_recipes_item))
                .check(matches(hasContentDescription()));

        onView((withId(R.id.steps_rv))).check(matches(hasChildCount(TWO)));

        // onView(withId(R.id.toolbar)).check(matches(withText("Nutella Pie"))); doesn't work !
    }


}
