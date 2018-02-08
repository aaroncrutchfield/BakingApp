package com.example.ioutd.bakingapp.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

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

public class AppViewModel extends ViewModel {


    public AppViewModel() {
    }

    public LiveData<List<Recipe>> getRecipes(RecipeRepository recipeRepository) {
        return recipeRepository.getAllRecipes();
    }

    public LiveData<Recipe> getRecipeWithId(RecipeRepository recipeRepository, long id) {
        return recipeRepository.getRecipeWithId(id);
    }

    public LiveData<List<Ingredient>> getIngredientsByRecipeID(IngredientRepository ingredientRepository, long recipeID) {
        return ingredientRepository.getIngredientsByRecipeID(recipeID);
    }

    public LiveData<List<Step>> getStepsByRecipeID(StepRepository stepRepository, long recipeID) {
        return stepRepository.getStepsByRecipeID(recipeID);
    }

    public LiveData<Step> getStepByStepID(StepRepository stepRepository, String stepID) {
        return stepRepository.getStepByStepID(stepID);
    }
}
