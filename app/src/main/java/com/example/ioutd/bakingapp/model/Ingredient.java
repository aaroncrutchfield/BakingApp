package com.example.ioutd.bakingapp.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by ioutd on 1/11/2018.
 */
@Entity
public class Ingredient {

    @PrimaryKey
    @NonNull
    private String ingredient;
    @ColumnInfo (name = "recipeID")
    private int recipeID;
    @ColumnInfo private float quantity;
    @ColumnInfo private String measure;

    public Ingredient() {
    }

    public int getRecipeID() {
        return recipeID;
    }

    public void setRecipeID(int recipeID) {
        this.recipeID = recipeID;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String toString() {
        return "recipeID= " + recipeID
                + "\ningredient= " + ingredient;

    }
}
