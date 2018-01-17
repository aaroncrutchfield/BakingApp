package com.example.ioutd.bakingapp.utilities;

import android.util.Log;

import com.example.ioutd.bakingapp.model.Ingredient;
import com.example.ioutd.bakingapp.model.Ingredient.Measurement;

import com.example.ioutd.bakingapp.model.Recipe;
import com.example.ioutd.bakingapp.model.Step;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;


/**
 * Created by ioutd on 1/13/2018.
 */

public class JSONDataHandler {
    private static final String TAG = JSONDataHandler.class.getSimpleName();

    // Recipe properties
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String SERVINGS = "servings";
    private static final String IMAGE_URL = "thumbnailURL";
    private static final String INGREDIENTS = "ingredients";
    private static final String STEPS = "steps";

    // Ingredient properties
    private static final String QUANTITY = "quantity";
    private static final String MEASURE = "measure";
    private static final String INGREDIENT = "ingredient";

    // Step properties
    private static final String SHORT_DESCRIPION = "shortDescripion";
    private static final String DESCRIPTION = "description";
    private static final String VIDEO_URL = "videoUrl";
    public static final String THUMBNAIL_URL = "thumbnailUrl";

    public static ArrayList<Recipe> getRecipeArrayList (String jsonString) throws JSONException {

        // Convert the JSON String into a JSON Object.
        JSONArray jsonArray = new JSONArray(jsonString);

        //Create an ArrayList to hold all of the Recipe Objects
        ArrayList<Recipe> recipeArrayList = new ArrayList<>();

        for(int i = 0; i < jsonArray.length(); i++) {
//            String jsonName = jsonObject.names().getString(i);
            JSONObject JSONRecipeObject = jsonArray.getJSONObject(i);

            // Get the ID
            int id = JSONRecipeObject.getInt(ID);

            // Get the name
            String name = JSONRecipeObject.getString(NAME);

            // Get the servings
            int servings = JSONRecipeObject.getInt(SERVINGS);

            // Get image URL
            String imageUrl = getStringIfNotNull(JSONRecipeObject, IMAGE_URL);

            // Get the ingredients array
            JSONArray JSONIngredientsArray = JSONRecipeObject.getJSONArray(INGREDIENTS);
            ArrayList<Ingredient> ingredientArrayList = new ArrayList<>();
            for (int j = 0; j < JSONIngredientsArray.length(); j++) {
                JSONObject JSONIngredientsObject = JSONIngredientsArray.getJSONObject(j);

                int quantity = JSONIngredientsObject.getInt(QUANTITY);
                Measurement measurement = Measurement.valueOf(
                        JSONIngredientsObject.getString(MEASURE));
                String ingredient = JSONIngredientsObject.getString(INGREDIENT);

                // Store ingredient values in object
                Ingredient ingredientObject = new Ingredient.Builder()
                        .recipeID(id)
                        .quantity(quantity)
                        .measurement(measurement)
                        .ingredient(ingredient)
                        .build();

                // Add ingredient object to an arraylist
                ingredientArrayList.add(ingredientObject);
            }

            // get the steps array
            JSONArray JSONStepsArray = JSONRecipeObject.getJSONArray(STEPS);
            ArrayList<Step> stepArrayList = new ArrayList<>();
            for (int k = 0; k < JSONStepsArray.length(); k++) {
                JSONObject JSONStepsObject = JSONStepsArray.getJSONObject(k);

                int stepID = JSONStepsObject.getInt(ID);
                String shortDescripion = getStringIfNotNull(JSONStepsObject, SHORT_DESCRIPION);
                String description = JSONStepsObject.getString(DESCRIPTION);
                String videoUrl = getStringIfNotNull(JSONStepsObject, VIDEO_URL);

                // Store step values in object
                Step step = new Step.Builder()
                        .id(stepID)
                        .shortDescription(shortDescripion)
                        .description(description)
                        .videoURL(videoUrl)
                        .build();

                // Add step object to an arraylist
                stepArrayList.add(step);
            }

            Recipe recipe = new Recipe.Builder()
                    .id(id)
                    .name(name)
                    .servings(servings)
                    .imageURL(imageUrl)
                    .ingredients(ingredientArrayList)
                    .steps(stepArrayList)
                    .build();

            recipeArrayList.add(recipe);
        }

        Log.d(TAG, "getRecipeArrayList() returned size= " + recipeArrayList.size());
        return recipeArrayList;
    }

    private static String getStringIfNotNull(JSONObject jsonObject, String name) throws JSONException {
        if (jsonObject.isNull(name))
            return "";
        else
            return jsonObject.getString(name);
    }
}
