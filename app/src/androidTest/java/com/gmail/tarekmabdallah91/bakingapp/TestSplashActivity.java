package com.gmail.tarekmabdallah91.bakingapp;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.gmail.tarekmabdallah91.bakingapp.activities.SplashActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;

@RunWith(AndroidJUnit4.class)
public class TestSplashActivity {

    @Rule
    public ActivityTestRule<SplashActivity> splashActivityActivityTestRule = new ActivityTestRule<>(SplashActivity.class);

    /**
     * to check when click on login btn
     * it should open MainActivity
     */
    @Test
    public void clickLoginBtn_openMainActivity() {
        //check is that the view is visible, the view visibility matches visible (displayed state)
        onView(withId(R.id.login))
                .check(matches(isDisplayed()))
                .perform(click());
    }


}
