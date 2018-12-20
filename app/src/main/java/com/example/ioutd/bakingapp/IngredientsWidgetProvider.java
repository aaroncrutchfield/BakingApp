package com.example.ioutd.bakingapp;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.widget.RemoteViews;

import com.example.ioutd.bakingapp.utilities.SharedPrefs;
import com.example.ioutd.bakingapp.utilities.Utils;

/**
 * Implementation of App Widget functionality.
 */
public class IngredientsWidgetProvider extends AppWidgetProvider {

    public static final String SET_IMAGE_BITMAP = "setImageBitmap";

    static RemoteViews updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                       int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredients_widget);

        String recipeName = SharedPrefs.loadRecipeName(context);
        Bitmap bitmap = loadImageView(recipeName, context);
        views.setTextViewText(R.id.widget_tv_title, recipeName);
        views.setBitmap(R.id.widget_image, SET_IMAGE_BITMAP, bitmap);

        // set the adapter on the listView
        Intent serviceIntent = new Intent(context, IngredientsWidgetService.class);
        serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);

        views.setRemoteAdapter(R.id.widget_list_ingredients, serviceIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.widget_list_ingredients);

        return views;
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            // Use the first recipe by default onUpdate
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    public static void updateAppWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
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

