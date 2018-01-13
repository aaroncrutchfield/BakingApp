package com.example.ioutd.bakingapp;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ioutd.bakingapp.ui.RecipeListFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();

        RecipeListFragment recipeFragment = new RecipeListFragment();

        fragmentManager.beginTransaction()
                .add(R.id.fragment_recipe_list, recipeFragment)
                .commit();


    }
}
