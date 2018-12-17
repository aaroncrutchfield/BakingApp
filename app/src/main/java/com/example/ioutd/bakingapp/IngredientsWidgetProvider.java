package com.example.ioutd.bakingapp;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.ioutd.bakingapp.utilities.SharedPrefs;
import com.example.ioutd.bakingapp.utilities.Utils;

/**
 * Implementation of App Widget functionality.
 */
public class IngredientsWidgetProvider extends AppWidgetProvider {

    public static final String GET_INGREDIENTS_LIST = "getIngredientsList";
    public static final String INGREDIENTS_LIST = "ingredientsList";
    public static final String INGREDIENTS_WIDGET_UPDATE = "IngredientsWidgetUpdate";
    static String TAG = "WidgetProvider";

    // TODO: 10/28/18 delete updateAppWidget method
    static RemoteViews updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                       int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredients_widget);
        // TODO: 12/14/18 get recipe name and id from shared preferences

        String recipeName = SharedPrefs.loadRecipeName(context);
        int recipeID = SharedPrefs.loadRecipeID(context);
        Bitmap bitmap = loadImageView(recipeName, context);
        views.setBitmap(R.id.widget_image, "setImageBitmap", bitmap);
        Log.d(TAG, "updateAppWidget.recipeID: " + recipeID);

        // set the adapter on the listView
        Intent serviceIntent = new Intent(context, IngredientsWidgetService.class);
        serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        serviceIntent.putExtra("recipeID", recipeID);

        views.setRemoteAdapter(R.id.widget_list_ingredients, serviceIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.widget_list_ingredients);

        return views;
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        Log.d(TAG, "onUpdate: ");
        for (int appWidgetId : appWidgetIds) {
            // Use the first recipe by default onUpdate
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    public static void updateAppWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Log.d(TAG, "staticUpdateAppWidgetsL ");
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    private static Bitmap loadImageView(String recipeName, Context context) {
        if (recipeName != null) {
            return Utils.loadAssetImage(context, recipeName);
        }
        return null;
    }

}

