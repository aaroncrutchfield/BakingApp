package com.example.ioutd.bakingapp.ui;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.os.AsyncTask;
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
import com.example.ioutd.bakingapp.R;
import com.example.ioutd.bakingapp.data.AppDatabase;
import com.example.ioutd.bakingapp.data.AppViewModel;
import com.example.ioutd.bakingapp.model.Recipe;
import com.example.ioutd.bakingapp.repositories.RecipeRepository;
import com.example.ioutd.bakingapp.utilities.JSONDataUtil;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ioutd on 1/31/2018.
 */

public class RecipeListFragment extends Fragment {

    @BindView(R.id.rv_recipes)
    RecyclerView rvRecipes;

    private Unbinder unbinder;

    public RecipeListFragment(){
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_recipe_list, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        final RecipeAdapter recipeAdapter = new RecipeAdapter(getContext());
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);

        rvRecipes.setLayoutManager(layoutManager);
        rvRecipes.setAdapter(recipeAdapter);
        
        setupRecipesRecyclerView();
        
        // Construct the ViewModel
        AppViewModel viewModel = new AppViewModel();

        // Get an instance of the Database
        AppDatabase appDatabase = AppDatabase.getAppDatabase(getContext());

        // Pass the Dao to the Repository
        RecipeRepository recipeRepository = new RecipeRepository(appDatabase.recipeDao());

        // Use the ViewModel to observe any changes 
        // onChanged, add the new data to the RecyclerView.Adapter
        viewModel.getRecipes(recipeRepository).observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable List<Recipe> recipes) {
                recipeAdapter.addRecipes(recipes);
            }
        });

        return rootView;
    }

    private void setupRecipesRecyclerView() {
        // TODO: 2/5/2018 setupRecipesRecyclerView() - move network call to a seperate class
        final JSONDataUtil jsonDataUtil = new JSONDataUtil();
        final Context context = getContext();

        AndroidNetworking.initialize(context);
        String url = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

        AndroidNetworking.get(url)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(final JSONArray response) {
                            new AsyncTask<Void, Void, Void>() {

                                @Override
                                protected Void doInBackground(Void... voids) {
                        try {
                                    jsonDataUtil.insertJSONtoDatabase(context, response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                                    return null;
                                }
                            }.execute();

                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Unbind the views
        unbinder.unbind();
    }
}
