package com.example.ioutd.bakingapp.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.ioutd.bakingapp.R;
import com.example.ioutd.bakingapp.RecipeDetailsActivity;
import com.example.ioutd.bakingapp.model.Recipe;
import com.example.ioutd.bakingapp.utilities.GoogleImageSearch;
import com.example.ioutd.bakingapp.utilities.ImageJSONHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ioutd on 1/11/2018.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private static final String TAG = RecipeAdapter.class.getSimpleName();

    private Context context;
    private ArrayList<Recipe> recipesArray;

    public RecipeAdapter(Context context, ArrayList<Recipe> recipesArray) {
        this.context = context;
        this.recipesArray = recipesArray;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.list_item_recipe, parent, false);

        return new RecipeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecipeViewHolder holder, int position) {
        Recipe recipe = recipesArray.get(position);

        String recipeName = recipe.getName();
        // Set the recipe name
        holder.tvRecipeName.setText(recipeName);

        String url = GoogleImageSearch.buildSearchString(recipeName, 1, 1);

        // Query the api on the background
        AndroidNetworking.get(url)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String imageUrl = ImageJSONHandler.getImageUrl(response);

                        // In case there were no image results from Google
                        if (imageUrl.equals("")) return;

                        Picasso.with(context)
                                .load(imageUrl)
                                .fit()
                                .into(holder.ivRecipeImage);
                        Log.d(TAG, "onResponse: ran");
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }

    @Override
    public int getItemCount() {
        if (recipesArray == null)
            return 0;
        else
            return recipesArray.size();
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder {

        public static final String RECIPE = "recipe";

        ImageView ivRecipeImage;
        TextView tvRecipeName;

        RecipeViewHolder(View itemView) {
            super(itemView);
            ivRecipeImage = itemView.findViewById(R.id.iv_recipe_image);
            tvRecipeName = itemView.findViewById(R.id.tv_recipe_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, RecipeDetailsActivity.class);

                    intent.putExtra(RECIPE, recipesArray.get(getAdapterPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
