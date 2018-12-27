package com.example.ioutd.bakingapp;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.ioutd.bakingapp.ui.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    private static final String NUTELLA_PIE = "Nutella Pie";
    private static final String BROWNIES = "Brownies";
    private static final String YELLOW_CAKE = "Yellow Cake";
    private static final String CHEESECAKE = "Cheesecake";

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void nutellaPieDisplayed() {
        onView(withText(NUTELLA_PIE))
                .check(matches(isDisplayed()));

    }

    @Test
    public void browniesIsDisplayed() {
        onView(withText(BROWNIES))
                .check(matches(isDisplayed()));

    }

    @Test
    public void yellowCakeIsDisplayed() {
        onView(withText(YELLOW_CAKE))
                .check(matches(isDisplayed()));

    }

    @Test
    public void cheesecakeIsDisplayed() {
        onView(withText(CHEESECAKE))
                .check(matches(isDisplayed()));

    }

    @Test
    public void clickFirstRecipe() {
        onView(withId(R.id.rv_recipes))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
    }
}
