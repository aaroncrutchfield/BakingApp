package com.example.ioutd.bakingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.ioutd.bakingapp.model.Ingredient;
import com.example.ioutd.bakingapp.model.Recipe;
import com.example.ioutd.bakingapp.model.Step;
import com.example.ioutd.bakingapp.ui.IngredientsAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailsActivity extends AppCompatActivity {

    public static final String RECIPE = "recipe";
    @BindView(R.id.rv_ingredients)
    RecyclerView rvIngredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        ButterKnife.bind(this);

        Intent intent = getIntent();

        Recipe recipe = intent.getParcelableExtra(RECIPE);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        ArrayList<Ingredient> ingredientArrayList = (ArrayList<Ingredient>) recipe.getIngredients();
        IngredientsAdapter adapter = new IngredientsAdapter(this, ingredientArrayList);

        rvIngredients.setLayoutManager(layoutManager);
        rvIngredients.setAdapter(adapter);
    }
}
