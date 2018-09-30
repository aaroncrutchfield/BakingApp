package com.example.ioutd.bakingapp.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.ioutd.bakingapp.model.Step;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by ioutd on 2/1/2018.
 */

@Dao
public interface StepDao {

    @Insert(onConflict = REPLACE)
    void insertAllSteps(List<Step> steps);

    @Query("SELECT * FROM step WHERE recipeID=:recipeID")
    LiveData<List<Step>> getStepsByRecipeID(long recipeID);

    @Query("SELECT * FROM step WHERE stepID=:stepID")
    LiveData<Step> getStepByStepID(int stepID);
}
