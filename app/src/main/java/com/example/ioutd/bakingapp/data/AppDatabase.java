package com.example.ioutd.bakingapp.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.ioutd.bakingapp.model.Ingredient;
import com.example.ioutd.bakingapp.model.Recipe;
import com.example.ioutd.bakingapp.model.Step;

/**
 * Created by ioutd on 2/1/2018.
 */

@Database(entities = {Recipe.class, Ingredient.class, Step.class}, version = 12)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract RecipeDao recipeDao();
    public abstract IngredientDao ingredientDao();
    public abstract StepDao stepDao();

    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null){
            INSTANCE = Room
                    .databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "app_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }


}
