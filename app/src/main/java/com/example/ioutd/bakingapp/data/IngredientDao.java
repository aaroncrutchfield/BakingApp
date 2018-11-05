package com.example.ioutd.bakingapp.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.ioutd.bakingapp.model.Ingredient;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by ioutd on 2/1/2018.
 */

@Dao
public interface IngredientDao {

    @Insert(onConflict = REPLACE)
    void insertAllIngredients(List<Ingredient> ingredients);

    @Query("SELECT * FROM ingredient WHERE recipeID=:recipeID")
    LiveData<List<Ingredient>> getLiveIngredientsByRecipeID(long recipeID);

    @Query("SELECT * FROM ingredient WHERE recipeID=:recipeID")
    List<Ingredient> getIngredientsByRecipeID(long recipeID);
}
