package com.example.ioutd.bakingapp.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.ioutd.bakingapp.model.Recipe;

public class SharedPrefs {
    public static final String PREFS_NAME = "prefs";


    public static void saveRecipe(Context context, Recipe recipe) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();
        prefs.putInt("recipeID", recipe.getId());
        prefs.putString("recipeName", recipe.getName());

        Log.d(PREFS_NAME, "saveRecipe.recipeID: " + recipe.getId());
        prefs.apply();
    }


    public static String loadRecipeName(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getString("recipeName", "");
        }

    public static int loadRecipeID(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        int recipeID = prefs.getInt("recipeID", 0);
        Log.d(PREFS_NAME, "loadRecipeID: " + recipeID);
        return recipeID;
    }
}
