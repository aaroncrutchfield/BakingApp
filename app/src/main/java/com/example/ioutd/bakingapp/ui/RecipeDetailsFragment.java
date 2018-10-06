package com.example.ioutd.bakingapp.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.ioutd.bakingapp.R;
import com.example.ioutd.bakingapp.data.AppViewModel;
import com.example.ioutd.bakingapp.model.Ingredient;
import com.example.ioutd.bakingapp.model.Step;
import com.example.ioutd.bakingapp.utilities.GoogleImageSearch;
import com.example.ioutd.bakingapp.utilities.ImageJSONHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RecipeDetailsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RecipeDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipeDetailsFragment extends Fragment {

    public static final String RECIPE_NAME = "recipe_name";
    @BindView(R.id.rv_ingredients)
    RecyclerView rvIngredients;

    @BindView(R.id.rv_steps)
    RecyclerView rvSteps;

    @Nullable
    @BindView(R.id.iv_recipe_image)
    ImageView ivRecipeImage;

    @Nullable
    @BindView(R.id.recipe_toolbar)
    Toolbar tbRecipe;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String RECIPE_ID = "param1";

    // TODO: Rename and change types of parameters
    private int recipeID;
    private String recipeName;

    private OnFragmentInteractionListener mListener;
    private Unbinder unbinder;

    public RecipeDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param recipeID Parameter 1.
     * @return A new instance of fragment RecipeDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecipeDetailsFragment newInstance(int recipeID, String recipeName) {
        RecipeDetailsFragment fragment = new RecipeDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(RECIPE_ID, recipeID);
        args.putString(RECIPE_NAME, recipeName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            recipeID = getArguments().getInt(RECIPE_ID);
            recipeName = getArguments().getString(RECIPE_NAME);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe_details, container, false);
        unbinder = ButterKnife.bind(this, view);

        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        if (appCompatActivity != null) {
            appCompatActivity.setSupportActionBar(tbRecipe);
            ActionBar actionBar = appCompatActivity.getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setTitle(recipeName);
            }
        }

        loadRecipeImage();
        initializeIngredientsAndStepsList();

        return view;
    }

    private void loadRecipeImage() {
        if (recipeName != null) {
            String url = GoogleImageSearch.buildSearchString(recipeName, 1, 1);
            Log.d("RecipeDetailsFragment", "loadRecipeImage.url: " + url);
            if (!url.equals("")) {
                // Query the api on the background
                AndroidNetworking.get(url)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                String imageUrl = ImageJSONHandler.getImageUrl(response);

                                // In case there were no image results from Google
                                if (imageUrl.equals("") || ivRecipeImage == null) return;

                                Picasso.with(getContext())
                                        .load(imageUrl)
                                        .fit()
                                        .into(ivRecipeImage);
                            }

                            @Override
                            public void onError(ANError anError) {

                            }
                        });
            }
        }
    }

    private void initializeIngredientsAndStepsList() {

        AppViewModel viewModel = ViewModelProviders.of(this).get(AppViewModel.class);

        final IngredientsAdapter ingredientsAdapter = new IngredientsAdapter(getContext());
        // TODO: 9/30/18 check within RecyclerView that context implements listener
        final StepAdapter stepAdapter = new StepAdapter(getContext(), mListener);

        viewModel.getIngredientsByRecipeID(recipeID).observe(this, new Observer<List<Ingredient>>() {
            @Override
            public void onChanged(@Nullable List<Ingredient> ingredients) {
                ingredientsAdapter.addIngredients(ingredients);
            }
        });

        viewModel.getStepsByRecipeID(recipeID).observe(this, new Observer<List<Step>>() {
            @Override
            public void onChanged(@Nullable List<Step> steps) {
                stepAdapter.addSteps(steps);
            }
        });

        rvIngredients.setLayoutManager(new LinearLayoutManager(getContext()));
        rvIngredients.setAdapter(ingredientsAdapter);

        rvSteps.setLayoutManager(new LinearLayoutManager(getContext()));
        rvSteps.setAdapter(stepAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(int stepID) {
        if (mListener != null) {
            mListener.onFragmentInteraction(stepID);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(int stepID);
    }
}
