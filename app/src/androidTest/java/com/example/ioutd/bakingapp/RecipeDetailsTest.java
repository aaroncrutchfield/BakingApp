package com.example.ioutd.bakingapp;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.Fragment;

import com.example.ioutd.bakingapp.ui.RecipeDetailsActivity;
import com.example.ioutd.bakingapp.ui.RecipeDetailsFragment;

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

    private static final String RECIPE_NAME = "recipeName";
    private static final String RECIPE_ID = "recipeID";
    private static final String STEP_ID = "stepID";
    private static final String CONTENT_POSITION = "contentPosition";

    private String recipeName = "Nutella Pie";
    private int recipeID = 1;
    private int introStepID = 100;
    private long contentPosition = 0;

    @Rule
    public ActivityTestRule<RecipeDetailsActivity> mActivityTestRule =
            new ActivityTestRule<RecipeDetailsActivity>(RecipeDetailsActivity.class){
                @Override
                protected Intent getActivityIntent() {
                    Intent intent = new Intent();
                    intent.putExtra(RECIPE_ID, recipeID);
                    intent.putExtra(RECIPE_NAME, recipeName);
                    intent.putExtra(STEP_ID, introStepID);
                    intent.putExtra(CONTENT_POSITION, contentPosition);

                    return intent;
                }
            };


    @Before
    public void init() {
        Fragment fragment = RecipeDetailsFragment.newInstance(recipeID, recipeName);

        mActivityTestRule.getActivity()
                .getSupportFragmentManager().beginTransaction()
                .replace(R.id.recipe_details_container, fragment)
                .commit();
    }

    @Test
    public void checkFirstIngredient() {
        onView(withText("5.0 TBLSP"))
                .check(matches(isDisplayed()));

        onView(withText("vanilla"))
                .check(matches(isDisplayed()));

    }

    @Test
    public void checkFirstStep() {
        onView(withText("Recipe Introduction"))
                .check(matches(isDisplayed()));
    }
}
