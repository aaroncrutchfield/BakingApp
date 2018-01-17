package com.example.ioutd.bakingapp.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ioutd.bakingapp.R;
import com.example.ioutd.bakingapp.model.Recipe;
import com.example.ioutd.bakingapp.utilities.GoogleImageSearch;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by ioutd on 1/11/2018.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    public static final String TAG = RecipeAdapter.class.getSimpleName();

    private Context context;
    private ArrayList<Recipe> recipesArray;

    public RecipeAdapter(Context context, ArrayList<Recipe> recipesArray) {
        this.context = context;
        this.recipesArray = recipesArray;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.recipe_list_item, parent, false);

        return new RecipeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        Recipe recipe = recipesArray.get(position);

        String recipeName = recipe.getName();
        // Set the recipe name
        holder.tvRecipeName.setText(recipeName);

        String imageUrl = recipe.getImageURL();
        Log.d(TAG, "onBindViewHolder: imageUrl= " + imageUrl);

        if (imageUrl == null || imageUrl.equals("") || imageUrl.equals(" ")) {
            // Build the google search string with the name
            GoogleImageSearch.buildSearchString(recipeName, 1, 1);

            // Query the api on the background

            // Get the image URL

            // Use this image URL to load an image with Picasso
        } else {

            // Load the recipe image
            Picasso.with(context)
                    .load(imageUrl)
                    .fit()
                    .into(holder.ivRecipeImage);
        }
    }

    @Override
    public int getItemCount() {
        if (recipesArray == null)
            return 0;
        else
            return recipesArray.size();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder {

        ImageView ivRecipeImage;
        TextView tvRecipeName;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            ivRecipeImage = itemView.findViewById(R.id.iv_recipe_image);
            tvRecipeName = itemView.findViewById(R.id.tv_recipe_name);
        }
    }
}
