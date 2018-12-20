package com.example.ioutd.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.ioutd.bakingapp.data.AppViewModel;
import com.example.ioutd.bakingapp.model.Ingredient;
import com.example.ioutd.bakingapp.utilities.AppExecutors;
import com.example.ioutd.bakingapp.utilities.SharedPrefs;

import java.util.ArrayList;
import java.util.List;

public class IngredientsWidgetService extends RemoteViewsService{

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new IngredientsRemoteViewsFactory(this.getApplicationContext(), intent);
    }

    class IngredientsRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

        private final Context mContext;
        private AppViewModel viewModel;
        private List<Ingredient> ingredientsList = new ArrayList<>();
        private int recipeID;

        IngredientsRemoteViewsFactory(Context applicationContext, Intent intent) {
            mContext = applicationContext.getApplicationContext();
            viewModel = new AppViewModel(getApplication());
            recipeID = SharedPrefs.loadRecipeID(mContext);
        }

        @Override
        public void onCreate() {
            getIngredients();
        }

        @Override
        public void onDataSetChanged() {
            recipeID = SharedPrefs.loadRecipeID(mContext);
            getIngredients();
        }

        @Override
        public void onDestroy() {
            ingredientsList.clear();
        }

        @Override
        public int getCount() {
            return ingredientsList.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {
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

        void getIngredients() {
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    ingredientsList = viewModel.getIngredientsByRecipeID(recipeID);
                }
            });
        }
    }
}
