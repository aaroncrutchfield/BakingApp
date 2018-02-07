package com.example.ioutd.bakingapp.repositories;

import android.arch.lifecycle.LiveData;

import com.example.ioutd.bakingapp.data.IngredientDao;
import com.example.ioutd.bakingapp.model.Ingredient;

import java.util.List;

/**
 * Created by ioutd on 2/5/2018.
 */

public class IngredientRepository {
    private final IngredientDao ingredientDao;

    public IngredientRepository(IngredientDao ingredientDao) {
        this.ingredientDao = ingredientDao;
    }

    public LiveData<List<Ingredient>> getIngredientsByRecipeID(long recipeID) {
        return ingredientDao.getIngredientsByRecipeID(recipeID);
    }
}
