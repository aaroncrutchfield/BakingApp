package com.example.ioutd.bakingapp.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import com.example.ioutd.bakingapp.data.AppDatabase;
import com.example.ioutd.bakingapp.data.StepDao;
import com.example.ioutd.bakingapp.model.Step;

import java.util.List;

/**
 * Created by ioutd on 2/5/2018.
 */

public class StepRepository {
    private final StepDao stepDao;

    public StepRepository(Application application) {
        AppDatabase appDatabase = AppDatabase.getAppDatabase(application);
        stepDao = appDatabase.stepDao();
    }

    public LiveData<List<Step>> getStepsByRecipeID(long recipeID) {
        return stepDao.getStepsByRecipeID(recipeID);
    }

    public LiveData<Step> getStepByStepID(String stepID) {
        return stepDao.getStepByStepID(stepID);
    }
}
