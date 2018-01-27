package com.example.ioutd.bakingapp.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ioutd.bakingapp.R;
import com.example.ioutd.bakingapp.model.Ingredient;

import java.util.ArrayList;

/**
 * Created by ioutd on 1/18/2018.
 */

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder> {

    private Context context;
    private ArrayList<Ingredient> ingredientArrayList;

    public IngredientsAdapter(Context context, ArrayList<Ingredient> ingredientArrayList) {
        this.context = context;
        this.ingredientArrayList = ingredientArrayList;
    }

    @Override
    public IngredientsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_item_ingredient, parent, false);

        return new IngredientsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IngredientsViewHolder holder, int position) {
        Ingredient ingredient = ingredientArrayList.get(position);

        String measurementString = ingredient.getQuantity()+ " " +
                ingredient.getMeasurement().toString();

        holder.tvMeasurement.setText(measurementString);
        holder.tvIngredient.setText(ingredient.getIngredient());
    }

    @Override
    public int getItemCount() {
        return ingredientArrayList.size();
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
