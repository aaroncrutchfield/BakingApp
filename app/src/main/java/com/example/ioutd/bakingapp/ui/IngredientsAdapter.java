package com.example.ioutd.bakingapp.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ioutd.bakingapp.R;
import com.example.ioutd.bakingapp.model.Ingredient;

import java.util.List;

/**
 * Created by ioutd on 1/18/2018.
 */

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder> {

    private Context context;
    private List<Ingredient> ingredients;

    public IngredientsAdapter(Context context) {
        this.context = context;
    }

    @Override
    public IngredientsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_item_ingredient, parent, false);

        return new IngredientsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IngredientsViewHolder holder, int position) {
        Ingredient ingredient = ingredients.get(position);

        String measurementString = ingredient.getQuantity()+ " " +
                ingredient.getMeasure();

        holder.tvMeasurement.setText(measurementString);
        holder.tvIngredient.setText(ingredient.getIngredient());
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public void addIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
        notifyDataSetChanged();
    }

    class IngredientsViewHolder extends RecyclerView.ViewHolder{
        TextView tvMeasurement;
        TextView tvIngredient;

        public IngredientsViewHolder(View itemView) {
            super(itemView);

            tvMeasurement = itemView.findViewById(R.id.tv_measurement);
            tvIngredient = itemView.findViewById(R.id.tv_ingredient);
        }
    }
}
