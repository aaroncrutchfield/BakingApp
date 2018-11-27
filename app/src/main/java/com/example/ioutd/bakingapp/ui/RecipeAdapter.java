package com.example.ioutd.bakingapp.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ioutd.bakingapp.R;
import com.example.ioutd.bakingapp.model.Recipe;
import com.example.ioutd.bakingapp.utilities.AssetImageLoader;

import java.util.List;

/**
 * Created by ioutd on 1/11/2018.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private static final String TAG = RecipeAdapter.class.getSimpleName();

    private Context context;
    private List<Recipe> recipes;

    RecipeAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.list_item_recipe, parent, false);

        return new RecipeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecipeViewHolder holder, int position) {
        Recipe recipe = recipes.get(position);

        String recipeName = recipe.getName();
        // Set the recipe name
        holder.tvRecipeName.setText(recipeName);

        loadRecipeImage(holder, recipeName);
    }

    private void loadRecipeImage(final RecipeViewHolder holder, String recipeName) {
        Bitmap bitmap = new AssetImageLoader().loadImage(context, recipeName);
        holder.ivRecipeImage.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        if (recipes == null) return 0;
        return recipes.size();
    }

    void addRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
        notifyDataSetChanged();
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder {

        static final String RECIPE_ID = "recipeID";
        static final String RECIPE_NAME = "recipeName";
        static final String STEP_ID = "stepID";

        ImageView ivRecipeImage;
        TextView tvRecipeName;

        RecipeViewHolder(final View itemView) {
            super(itemView);
            ivRecipeImage = itemView.findViewById(R.id.iv_recipe_image);
            tvRecipeName = itemView.findViewById(R.id.tv_recipe_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startRecipeDetailsActivity();
                }
            });
        }

        private void startRecipeDetailsActivity() {
            Intent intent = new Intent(context, RecipeDetailsActivity.class);
            Recipe recipe = recipes.get(getAdapterPosition());

            int recipeID = recipe.getId();
            int stepID = getIntroStepID(recipeID);
            String recipeName = recipe.getName();

            intent.putExtra(RECIPE_ID, recipeID);
            intent.putExtra(RECIPE_NAME, recipeName);
            intent.putExtra(STEP_ID, stepID);

            context.startActivity(intent);
        }

        private int getIntroStepID(int recipeID) {
            return recipeID * 100;
        }
    }
}
