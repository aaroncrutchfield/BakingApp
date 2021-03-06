package com.example.ioutd.bakingapp.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.ioutd.bakingapp.R;
import com.example.ioutd.bakingapp.data.AppViewModel;
import com.example.ioutd.bakingapp.model.Step;
import com.example.ioutd.bakingapp.utilities.SharedPrefs;
import com.example.ioutd.bakingapp.utilities.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailsActivity extends AppCompatActivity implements StepDetailsFragment.OnFragmentInteractionListener,
        RecipeDetailsFragment.OnFragmentInteractionListener{

    public static final String RECIPE_ID = "recipeID";
    public static final String RECIPE_NAME = "recipeName";
    public static final String STEP_ID = "stepID";
    public static final String CONTENT_POSITION = "contentPosition";

    @Nullable
    @BindView(R.id.steps_container)
    FrameLayout stepsContainer;

    @Nullable
    @BindView(R.id.iv_recipe_image)
    ImageView ivRecipeImage;

    FragmentManager manager;
    AppViewModel appViewModel;
    String recipeName;
    long contentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.recipe_toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();

        int recipeID = intent.getIntExtra(RECIPE_ID, -1);
        final int stepID = intent.getIntExtra(STEP_ID, -1);
        recipeName = SharedPrefs.loadRecipeName(this);
        contentPosition = intent.getLongExtra(CONTENT_POSITION, 0);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(recipeName);
            loadRecipeImage();
        }

        appViewModel = ViewModelProviders.of(this).get(AppViewModel.class);
        manager = getSupportFragmentManager();

        loadRecipeDetailsFragment(recipeID, recipeName);

        if (stepsContainer != null) {
            loadStepDetailsFragment(stepID, contentPosition);
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
            loadStepDetailsFragment(stepID, contentPosition);
        } else {
            startStepDetailsActivity(stepID);
        }
    }


    private void loadRecipeImage() {
        if (recipeName != null) {
            Bitmap bitmap = Utils.loadAssetImage(this, recipeName);
            if (ivRecipeImage != null) {
                ivRecipeImage.setImageBitmap(bitmap);
            }
        }
    }

    private void loadRecipeDetailsFragment(int recipeID, String recipeName) {
        Fragment fragment = RecipeDetailsFragment.newInstance(recipeID, recipeName);

        manager.beginTransaction()
                .replace(R.id.recipe_details_container, fragment)
                .commit();
    }

    private void loadStepDetailsFragment(int stepID, final long contentPosition) {
        appViewModel.getStepByStepID(stepID).observe(this, new Observer<Step>() {
            @Override
            public void onChanged(@Nullable Step step) {
                Fragment fragment = StepDetailsFragment.newInstance(step, contentPosition);
                manager.beginTransaction()
                        .replace(R.id.steps_container, fragment)
                        .commit();
            }
        });
    }

    private void startStepDetailsActivity(int stepID) {
        Intent intent = new Intent(this, StepDetailsActivity.class);
        intent.putExtra(STEP_ID, stepID);
        startActivity(intent);
    }
}
