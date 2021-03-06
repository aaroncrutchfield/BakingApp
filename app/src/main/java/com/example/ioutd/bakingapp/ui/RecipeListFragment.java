package com.example.ioutd.bakingapp.ui;

import android.appwidget.AppWidgetManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.example.ioutd.bakingapp.IngredientsWidgetProvider;
import com.example.ioutd.bakingapp.R;
import com.example.ioutd.bakingapp.data.AppViewModel;
import com.example.ioutd.bakingapp.model.Recipe;
import com.example.ioutd.bakingapp.utilities.AppExecutors;
import com.example.ioutd.bakingapp.utilities.JSONDataUtil;
import com.example.ioutd.bakingapp.utilities.SharedPrefs;
import com.example.ioutd.bakingapp.utilities.Utils;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ioutd on 1/31/2018.
 */

public class RecipeListFragment extends Fragment implements RecipeAdapter.OnItemClickListener{

    @BindView(R.id.rv_recipes)
    RecyclerView rvRecipes;

    private Unbinder unbinder;
    GridLayoutManager layoutManager;

    static final String RECIPE_ID = "recipeID";
    static final String RECIPE_NAME = "recipeName";
    static final String STEP_ID = "stepID";

    public RecipeListFragment(){
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_recipe_list, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        loadRecipesFromJSON();

        initializeRecipesList();


        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Unbind the views
        unbinder.unbind();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            layoutManager.setSpanCount(4);

        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            layoutManager.setSpanCount(2);
        }
    }

    private void loadRecipesFromJSON() {
        final JSONDataUtil jsonDataUtil = new JSONDataUtil();
        final Context context = getContext();

        if (context != null) {
            AndroidNetworking.initialize(context);

            String url = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

            AndroidNetworking.get(url)
                    .build()
                    .getAsJSONArray(new JSONArrayRequestListener() {
                        @Override
                        public void onResponse(final JSONArray response) {
                            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        jsonDataUtil.insertJSONtoDatabase(context, response);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }

                        @Override
                        public void onError(ANError anError) {

                        }
                    });
        }
    }

    private void initializeRecipesList() {
        final RecipeAdapter recipeAdapter = new RecipeAdapter(getContext(), this);

        int spanCount = Utils.getScreenOrientation(getContext());
        layoutManager = new GridLayoutManager(getContext(), spanCount);

        rvRecipes.setLayoutManager(layoutManager);
        rvRecipes.setAdapter(recipeAdapter);


        // Construct the ViewModel
        AppViewModel viewModel = ViewModelProviders.of(this).get(AppViewModel.class);

        // Use the ViewModel to observe any changes
        // onChanged, add the new data to the RecyclerView.Adapter
        viewModel.getRecipes().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable List<Recipe> recipes) {
                recipeAdapter.addRecipes(recipes);
            }
        });
    }

    @Override
    public void onItemClick(Recipe recipe) {
        startRecipeDetailsActivity(recipe);
        updateWidgetRecipe();
    }

    private void startRecipeDetailsActivity(Recipe recipe) {
        Intent intent = new Intent(getContext(), RecipeDetailsActivity.class);

        SharedPrefs.saveRecipe(getContext(), recipe);

        int recipeID = recipe.getId();
        int stepID = getIntroStepID(recipeID);
        String recipeName = recipe.getName();

        intent.putExtra(RECIPE_ID, recipeID);
        intent.putExtra(RECIPE_NAME, recipeName);
        intent.putExtra(STEP_ID, stepID);

        startActivity(intent);
    }

    private void updateWidgetRecipe() {
        AppWidgetManager manager = getContext().getSystemService(AppWidgetManager.class);
        int [] ids = manager.getAppWidgetIds(new ComponentName(getContext(), IngredientsWidgetProvider.class));

        IngredientsWidgetProvider.updateAppWidgets(getContext(), manager, ids);
    }

    private int getIntroStepID(int recipeID) {
        return recipeID * 100;
    }
}
