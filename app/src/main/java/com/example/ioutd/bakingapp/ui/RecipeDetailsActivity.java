package com.example.ioutd.bakingapp.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import com.example.ioutd.bakingapp.R;
import com.example.ioutd.bakingapp.data.AppViewModel;
import com.example.ioutd.bakingapp.model.Step;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailsActivity extends AppCompatActivity implements StepDetailsFragment.OnFragmentInteractionListener,
        RecipeDetailsFragment.OnFragmentInteractionListener{

    public static final String RECIPE_ID = "recipeID";
    public static final String RECIPE_NAME = "recipeName";
    public static final String STEP_ID = "stepID";

    @Nullable
    @BindView(R.id.steps_container)
    FrameLayout stepsContainer;

    FragmentManager manager;
    AppViewModel appViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.recipe_toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();

        Intent intent = getIntent();

        appViewModel = ViewModelProviders.of(this).get(AppViewModel.class);

        // Don't use 0 as default value because it may actually exist
        int recipeID = intent.getIntExtra(RECIPE_ID, -1);
        final int stepID = intent.getIntExtra(STEP_ID, -1);

        String recipeName = intent.getStringExtra(RECIPE_NAME);

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(recipeName);
        }

        RecipeDetailsFragment recipeDetailsFragment = RecipeDetailsFragment.newInstance(recipeID, recipeName);

        manager = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.recipe_details_fragment, recipeDetailsFragment)
                .commit();

        if (stepsContainer != null) {
            appViewModel.getStepByStepID(stepID).observe(this, new Observer<Step>() {
                @Override
                public void onChanged(@Nullable Step step) {
                    Fragment fragment = StepDetailsFragment.newInstance(step);
                    manager.beginTransaction()
                            .replace(R.id.steps_container, fragment)
                            .commit();
                    appViewModel.getStepByStepID(stepID).removeObservers(RecipeDetailsActivity.this);
                }
            });
        } else {


        }

    }

    @Override
    public void onNext() {

    }

    @Override
    public void onPrevious() {

    }

    @Override
    public void onFragmentInteraction(int stepID) {
        if (stepsContainer != null) {
            appViewModel.getStepByStepID(stepID).observe(this, new Observer<Step>() {
                @Override
                public void onChanged(@Nullable Step step) {
                    Fragment fragment = StepDetailsFragment.newInstance(step);
                    manager.beginTransaction()
                            .replace(R.id.steps_container, fragment)
                            .commit();
                }
            });
        } else {
            Intent intent = new Intent(this, StepDetailsActivity.class);
            intent.putExtra(STEP_ID, stepID);
            startActivity(intent);
        }
    }
}
