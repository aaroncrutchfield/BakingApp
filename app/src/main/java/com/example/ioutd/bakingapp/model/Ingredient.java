package com.example.ioutd.bakingapp.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

/**
 * Created by ioutd on 1/11/2018.
 */
@Entity
public class Ingredient implements Parcelable {

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.ingredient);
        dest.writeInt(this.recipeID);
        dest.writeFloat(this.quantity);
        dest.writeString(this.measure);
    }

    protected Ingredient(Parcel in) {
        this.ingredient = in.readString();
        this.recipeID = in.readInt();
        this.quantity = in.readFloat();
        this.measure = in.readString();
    }

    public static final Parcelable.Creator<Ingredient> CREATOR = new Parcelable.Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel source) {
            return new Ingredient(source);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };
}
