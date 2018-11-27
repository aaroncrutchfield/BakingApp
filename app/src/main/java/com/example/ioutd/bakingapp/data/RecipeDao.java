package com.example.ioutd.bakingapp.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.ioutd.bakingapp.model.Recipe;

import java.util.ArrayList;
import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by ioutd on 2/1/2018.
 */

@Dao
public interface RecipeDao {

    @Insert(onConflict = REPLACE)
    void insertAllRecipes(ArrayList<Recipe> recipes);

    @Query("SELECT * FROM recipe")
    LiveData<List<Recipe>> getAllRecipes();

    @Query("SELECT * FROM recipe WHERE id=:id")
    LiveData<Recipe> getRecipeWithId(long id);
}
