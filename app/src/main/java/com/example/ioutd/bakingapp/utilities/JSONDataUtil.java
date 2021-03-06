package com.example.ioutd.bakingapp.utilities;

import android.content.Context;

import com.example.ioutd.bakingapp.data.AppDatabase;
import com.example.ioutd.bakingapp.model.Ingredient;
import com.example.ioutd.bakingapp.model.Recipe;
import com.example.ioutd.bakingapp.model.Step;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by ioutd on 1/13/2018.
 */

public class JSONDataUtil {
    private static final String TAG = JSONDataUtil.class.getSimpleName();

    private static final String INGREDIENTS = "ingredients";
    private static final String STEPS = "steps";

    // Recipe properties
    // id, name, servings, thumbnailURL

    // Ingredient properties
    // quantity, measure, ingredient

    // Step properties
    // shortDescription, description, videoURL, thumbnailURL

    public void insertJSONtoDatabase(Context context, JSONArray jsonArray) throws JSONException {

        AppDatabase appDatabase = AppDatabase.getAppDatabase(context);

        // ArrayList of Recipes to save to the database
        ArrayList<Recipe> recipeArrayList = new ArrayList<>();
        for(int i = 0; i < jsonArray.length(); i++) {
            JSONObject JSONRecipeObject = jsonArray.getJSONObject(i);

            Gson gson = new Gson();
            Recipe recipe = gson.fromJson(JSONRecipeObject.toString(), Recipe.class);

            recipeArrayList.add(recipe);

            // Get the ingredients array
            JSONArray JSONIngredientsArray = JSONRecipeObject.getJSONArray(INGREDIENTS);

            // ArrayList of Ingredients to save to the database
            List<Ingredient> ingredientArrayList = new ArrayList<>();
            for (int j = 0; j < JSONIngredientsArray.length(); j++) {
                JSONObject JSONIngredientsObject = JSONIngredientsArray.getJSONObject(j);

                // Create the Ingredient object from the JSON string
                Gson ingredientGson = new Gson();
                Ingredient ingredient = ingredientGson
                        .fromJson(JSONIngredientsObject.toString(), Ingredient.class);

                // Set the foreignKey recipeID
                ingredient.setRecipeID(recipe.getId());

                ingredientArrayList.add(ingredient);
            }
            // Insert all Ingredients into the DB
            appDatabase.ingredientDao().insertAllIngredients(ingredientArrayList);

            // get the steps array
            JSONArray JSONStepsArray = JSONRecipeObject.getJSONArray(STEPS);

            // ArrayList of Steps to save to the database
            List<Step> stepArrayList = new ArrayList<>();
            for (int k = 0; k < JSONStepsArray.length(); k++) {
                JSONObject JSONStepsObject = JSONStepsArray.getJSONObject(k);

                // Create the Step object from the JSON string
                Gson stepGson = new Gson();
                Step step = stepGson.fromJson(JSONStepsObject.toString(), Step.class);

                // Set the foreignKey recipeID
                step.setRecipeID(recipe.getId());

                int stepID = formatStepID(step.getRecipeID(), step.getId());
                step.setStepID(stepID);
                stepArrayList.add(step);
            }
            // TODO: 9/14/18 Use the repo to insertAllSteps insted of the DAO directly
            // Insert all Steps into the DB
            appDatabase.stepDao().insertAllSteps(stepArrayList);

        }
        appDatabase.recipeDao().insertAllRecipes(recipeArrayList);
    }

    private int formatStepID(int recipeID, int stepID) {
        int newID;
        String stepIdString = String.valueOf(stepID);

        if (stepIdString.length() == 1) {
            newID = Integer.valueOf(recipeID + "0" + stepID);
        } else {
            newID = Integer.valueOf(recipeID + stepIdString);
        }

        return newID;
    }
}
