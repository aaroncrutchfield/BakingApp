package com.example.ioutd.bakingapp.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import com.example.ioutd.bakingapp.data.AppDatabase;
import com.example.ioutd.bakingapp.data.RecipeDao;
import com.example.ioutd.bakingapp.model.Recipe;

import java.util.List;

/**
 * Created by ioutd on 2/5/2018.
 */


public class RecipeRepository {
    private final RecipeDao recipeDao;

    public RecipeRepository(Application application) {
        AppDatabase appDatabase = AppDatabase.getAppDatabase(application);
        recipeDao = appDatabase.recipeDao();
    }

    public LiveData<List<Recipe>> getAllRecipes() {
        return recipeDao.getAllRecipes();
    }

    public LiveData<Recipe> getRecipeWithId(long id) {
        return recipeDao.getRecipeWithId(id);
    }
}
