package com.example.ioutd.bakingapp.data;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.ioutd.bakingapp.model.Ingredient;
import com.example.ioutd.bakingapp.model.Recipe;
import com.example.ioutd.bakingapp.model.Step;
import com.example.ioutd.bakingapp.repositories.IngredientRepository;
import com.example.ioutd.bakingapp.repositories.RecipeRepository;
import com.example.ioutd.bakingapp.repositories.StepRepository;

import java.util.List;

/**
 * Created by ioutd on 2/2/2018.
 */

public class AppViewModel extends AndroidViewModel {


    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingrdientRepository;
    private final StepRepository stepRepository;

    public AppViewModel(@NonNull Application application) {
        super(application);
        recipeRepository = new RecipeRepository(application);
        ingrdientRepository = new IngredientRepository(application);
        stepRepository = new StepRepository(application);
    }

    public LiveData<List<Recipe>> getRecipes() {
        return recipeRepository.getAllRecipes();
    }

    public LiveData<Recipe> getRecipeWithId(long id) {
        return recipeRepository.getRecipeWithId(id);
    }

    public LiveData<List<Ingredient>> getLiveIngredientsByRecipeID(long recipeID) {
        return ingrdientRepository.getLiveIngredientsByRecipeID(recipeID);
    }

    public List<Ingredient> getIngredientsByRecipeID(long recipeID) {
        return ingrdientRepository.getIngredientsByRecipeID(recipeID);
    }

    public LiveData<List<Step>> getStepsByRecipeID(long recipeID) {
        return stepRepository.getStepsByRecipeID(recipeID);
    }

    public LiveData<Step> getStepByStepID(int stepID) {
        return stepRepository.getStepByStepID(stepID);
    }
}
