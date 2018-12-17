package com.example.ioutd.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.ioutd.bakingapp.data.AppViewModel;
import com.example.ioutd.bakingapp.model.Ingredient;
import com.example.ioutd.bakingapp.utilities.AppExecutors;

import java.util.ArrayList;
import java.util.List;

public class IngredientsWidgetService extends RemoteViewsService{

    String TAG = "WidgetService";
    
    // TODO: 10/28/18 intent contains recipeID
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Log.d(TAG, "onGetViewFactory: ");
        return new IngredientsRemoteViewsFactory(this.getApplicationContext(), intent);
    }

    class IngredientsRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

        private final Context mContext;
        AppViewModel viewModel;
        private List<Ingredient> ingredientsList = new ArrayList<>();
        private int recipeID;

        public IngredientsRemoteViewsFactory(Context applicationContext, Intent intent) {
            mContext = applicationContext.getApplicationContext();
            viewModel = new AppViewModel(getApplication());
            // TODO: 12/14/18 Does getRecipe with ID 0 return the first recipe?
            recipeID = intent.getIntExtra("recipeID", 1);
            Log.d(TAG, "IngredientsRemoteViewsFactory.recipeID: " + recipeID);
        }

        @Override
        public void onCreate() {
            Log.d(TAG, "onCreate: ");
            getIngredients();
        }

        @Override
        public void onDataSetChanged() {
            Log.d(TAG, "onDataSetChanged: ");
            getIngredients();
        }

        @Override
        public void onDestroy() {
            ingredientsList.clear();
        }

        @Override
        public int getCount() {
            Log.d(TAG, "getCount: " + ingredientsList.size());
            return ingredientsList.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {

            Log.d(TAG, "getViewAt: " + position);

            Ingredient ingredient = ingredientsList.get(position);
            String quantity = String.valueOf(ingredient.getQuantity()) + " " +
                    String.valueOf(ingredient.getMeasure());
            String ingredientString = ingredient.getIngredient();

            RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.widget_item_ingredient);
            views.setTextViewText(R.id.widget_tv_quantity, quantity);
            views.setTextViewText(R.id.widget_tv_ingredient, ingredientString);

            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        public void getIngredients() {
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    ingredientsList = viewModel.getIngredientsByRecipeID(recipeID);
                    Log.d("WidgetService", "run: " + ingredientsList.toString());
                }
            });
        }
    }
}
