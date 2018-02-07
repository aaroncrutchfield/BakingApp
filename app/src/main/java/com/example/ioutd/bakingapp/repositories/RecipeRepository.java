package com.example.ioutd.bakingapp.repositories;

import android.arch.lifecycle.LiveData;

import com.example.ioutd.bakingapp.data.RecipeDao;
import com.example.ioutd.bakingapp.model.Recipe;

import java.util.List;

/**
 * Created by ioutd on 2/5/2018.
 */


public class RecipeRepository {
    private final RecipeDao recipeDao;

    public RecipeRepository(RecipeDao recipeDao) {
        this.recipeDao = recipeDao;
    }

    public LiveData<List<Recipe>> getAllRecipes() {
        return recipeDao.getAllRecipes();
    }

    public LiveData<Recipe> getRecipeWithId(long id) {
        return recipeDao.getRecipeWithId(id);
    }
}
