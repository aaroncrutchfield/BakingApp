package com.example.ioutd.bakingapp;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.ioutd.bakingapp.model.Ingredient;
import com.example.ioutd.bakingapp.model.Recipe;
import com.example.ioutd.bakingapp.model.Step;
import com.example.ioutd.bakingapp.ui.IngredientsAdapter;
import com.example.ioutd.bakingapp.ui.StepAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailsActivity extends AppCompatActivity {

    public static final String RECIPE = "recipe";

    @BindView(R.id.rv_ingredients)
    RecyclerView rvIngredients;

    @BindView(R.id.rv_steps)
    RecyclerView rvSteps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();

        Intent intent = getIntent();

        Recipe recipe = intent.getParcelableExtra(RECIPE);

        actionBar.setTitle(recipe.getName());

        ArrayList<Ingredient> ingredientArrayList = (ArrayList<Ingredient>) recipe.getIngredients();
        LinearLayoutManager ingredientLayoutManager = new LinearLayoutManager(this);
        IngredientsAdapter ingredientsAdapter = new IngredientsAdapter(this, ingredientArrayList);

        rvIngredients.setLayoutManager(ingredientLayoutManager);
        rvIngredients.setAdapter(ingredientsAdapter);

        ArrayList<Step> stepArrayList = (ArrayList<Step>) recipe.getSteps();
        LinearLayoutManager stepLayoutManager = new LinearLayoutManager(this);
        StepAdapter stepAdapter = new StepAdapter(this, stepArrayList);

        rvSteps.setLayoutManager(stepLayoutManager);
        rvSteps.setAdapter(stepAdapter);
    }
}
