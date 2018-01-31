package com.example.ioutd.bakingapp.ui;

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
import com.example.ioutd.bakingapp.MainActivity;
import com.example.ioutd.bakingapp.R;
import com.example.ioutd.bakingapp.model.Recipe;
import com.example.ioutd.bakingapp.utilities.JSONDataHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

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
        setupRecipesRecyclerView();

        return rootView;
    }

    private void setupRecipesRecyclerView() {
        AndroidNetworking.initialize(getContext());

        String url = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

        AndroidNetworking.get(url)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        ArrayList<Recipe> recipeArrayList = new ArrayList<>();
                        try {
                            recipeArrayList = JSONDataHandler.getRecipeArrayList(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        RecipeAdapter recipeAdapter = new RecipeAdapter(getContext(), recipeArrayList);
                        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);

                        rvRecipes.setLayoutManager(layoutManager);
                        rvRecipes.setAdapter(recipeAdapter);
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
