package com.example.ioutd.bakingapp.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ioutd on 1/11/2018.
 */
@Entity(foreignKeys = @ForeignKey(entity = Recipe.class,
        parentColumns = "id",
        childColumns = "recipeID"))
public class Ingredient implements Parcelable {

    public enum Measurement {
        TBLSP, TSP, CUP, UNIT,
        K, G, OZ
    }


    @ColumnInfo private int recipeID;
    @ColumnInfo private String ingredient;
    @ColumnInfo private int quantity;
    @ColumnInfo private Measurement measurement;

    // Default constructor
    public Ingredient(String ingredient, int quantity, Measurement measurement) {
        this.ingredient = ingredient;
        this.quantity = quantity;
        this.measurement = measurement;
    }

    // Builder constructor
    private Ingredient(Builder builder) {
        setRecipeID(builder.recipeID);
        setIngredient(builder.ingredient);
        setQuantity(builder.quantity);
        setMeasurement(builder.measurement);
    }

    // Getters
    public int getRecipeID() {
        return recipeID;
    }

    public String getIngredient() {
        return ingredient;
    }

    public int getQuantity() {
        return quantity;
    }

    public Measurement getMeasurement() {
        return measurement;
    }

    // Setters
    public void setRecipeID(int recipeID) {
        this.recipeID = recipeID;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setMeasurement(Measurement measurement) {
        this.measurement = measurement;
    }

    // Parcelable logic
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.recipeID);
        dest.writeString(this.ingredient);
        dest.writeInt(this.quantity);
        dest.writeInt(this.measurement == null ? -1 : this.measurement.ordinal());
    }

    protected Ingredient(Parcel in) {
        this.recipeID = in.readInt();
        this.ingredient = in.readString();
        this.quantity = in.readInt();
        int tmpMeasurement = in.readInt();
        this.measurement = tmpMeasurement == -1 ? null : Measurement.values()[tmpMeasurement];
    }

    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel source) {
            return new Ingredient(source);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

    // Builder logic
    public static final class Builder {
        private int recipeID;
        private String ingredient;
        private int quantity;
        private Measurement measurement;

        public Builder() {
        }

        public Builder recipeID(int val) {
            recipeID = val;
            return this;
        }

        public Builder ingredient(String val) {
            ingredient = val;
            return this;
        }

        public Builder quantity(int val) {
            quantity = val;
            return this;
        }

        public Builder measurement(Measurement val) {
            measurement = val;
            return this;
        }

        public Ingredient build() {
            return new Ingredient(this);
        }
    }
}
