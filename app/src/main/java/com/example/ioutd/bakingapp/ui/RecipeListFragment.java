package com.example.ioutd.bakingapp.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ioutd.bakingapp.R;
import com.example.ioutd.bakingapp.model.Recipe;
import com.example.ioutd.bakingapp.utilities.NetworkUtil;
import com.squareup.moshi.Json;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ioutd on 1/11/2018.
 */

public class RecipeListFragment extends Fragment {

    private String TAG = RecipeListFragment.class.getSimpleName();

    private Unbinder unbinder;

    @BindView(R.id.rv_recipes) RecyclerView rvRecipes;

    public RecipeListFragment(){
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_master_list, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        // TODO: 1/12/2018 onCreateView() - change this reference to the URL
        String url = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
        String JSONString = "";
        try {
            JSONString = NetworkUtil.getJSONResponse(url);
        } catch (IOException e) {
            e.printStackTrace();
            // TODO: 1/12/2018 onCreateView() - handle IOExcpetion when requesting JSONResponse
        }

        //Convert the JSON to a POJO
        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<Recipe> jsonAdapter = moshi.adapter(Recipe.class);
        Recipe recipe = null;
        try {
            recipe = jsonAdapter.fromJson(JSONString);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "onCreateView: recipe=" + recipe.toString());

        // TODO: 1/11/2018 onCreateView() - set the adapter on rvRecipes



        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
