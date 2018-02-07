package com.example.ioutd.bakingapp.repositories;

import android.arch.lifecycle.LiveData;

import com.example.ioutd.bakingapp.data.StepDao;
import com.example.ioutd.bakingapp.model.Step;

import java.util.List;

/**
 * Created by ioutd on 2/5/2018.
 */

public class StepRepository {
    private final StepDao stepDao;

    public StepRepository(StepDao stepDao) {
        this.stepDao = stepDao;
    }

    public LiveData<List<Step>> getStepsByRecipeID(long recipeID) {
        return stepDao.getStepsByRecipeID(recipeID);
    }
}
