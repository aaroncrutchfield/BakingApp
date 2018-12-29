package com.example.ioutd.bakingapp;

import android.content.Intent;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.ioutd.bakingapp.ui.RecipeDetailsActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class RecipeDetailsTest {

    public static final String RECIPE_NAME = "recipeName";
    public static final String RECIPE_ID = "recipeID";
    public static final String STEP_ID = "stepID";

    private String recipeName = "Nutella Pie";
    private int recipeID = 1;
    private int introStepID = 100;

    @Rule
    public IntentsTestRule<RecipeDetailsActivity> mIntentsTestRule = new IntentsTestRule<>(RecipeDetailsActivity.class);

    @Before
    public void setupIncomingIntent() {
        Intent intent = new Intent();
        intent.putExtra(RECIPE_ID, recipeID);
        intent.putExtra(RECIPE_NAME, recipeName);
        intent.putExtra(STEP_ID, introStepID);

        // java.lang.RuntimeException: Could not launch intent
        // is using a Fragment causing the issue?
        // TODO: 12/29/18 how to put extras inside of intent before launching the activity
        mIntentsTestRule.launchActivity(intent);
    }

    @Test
    public void vanillaIngredientDisplayed() {
        onView(withText("vanilla"))
                .check(matches(isDisplayed()));

    }
}
