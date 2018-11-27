package com.example.ioutd.bakingapp.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import com.example.ioutd.bakingapp.data.AppDatabase;
import com.example.ioutd.bakingapp.data.IngredientDao;
import com.example.ioutd.bakingapp.model.Ingredient;

import java.util.List;

/**
 * Created by ioutd on 2/5/2018.
 */

public class IngredientRepository {
    private final IngredientDao ingredientDao;

    public IngredientRepository(Application application) {
        AppDatabase appDatabase = AppDatabase.getAppDatabase(application);
        ingredientDao = appDatabase.ingredientDao();
    }

    public LiveData<List<Ingredient>> getLiveIngredientsByRecipeID(long recipeID) {
        return ingredientDao.getLiveIngredientsByRecipeID(recipeID);
    }

    public List<Ingredient> getIngredientsByRecipeID(long recipeID) {
        return ingredientDao.getIngredientsByRecipeID(recipeID);
    }
}
