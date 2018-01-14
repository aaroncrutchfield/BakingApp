package com.example.ioutd.bakingapp.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.List;

/**
 * Created by ioutd on 1/11/2018.
 */

@Entity
public class Recipe implements Parcelable {
    @PrimaryKey private int id;

    @ColumnInfo private String name;
    @ColumnInfo private int servings;
    @ColumnInfo private String imageURL;

    @Ignore private ArrayList<Ingredient> ingredients;
    @Ignore private ArrayList<Step> steps;

    // Default constructor
    public Recipe(int id, String name, ArrayList<Ingredient> ingredients, int servings, ArrayList<Step> steps, String imageURL) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.servings = servings;
        this.steps = steps;
        this.imageURL = imageURL;
    }

    // Builder constructor
    private Recipe(Builder builder) {
        setId(builder.id);
        setName(builder.name);
        setServings(builder.servings);
        setImageURL(builder.imageURL);
        setIngredients(builder.ingredients);
        setSteps(builder.steps);
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public void setSteps(ArrayList<Step> steps) {
        this.steps = steps;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public int getServings() {
        return servings;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public String getImageURL() {
        return imageURL;
    }

    // Parcelable logic
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeInt(this.servings);
        dest.writeString(this.imageURL);
        dest.writeList(this.ingredients);
        dest.writeList(this.steps);
    }

    protected Recipe(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.servings = in.readInt();
        this.imageURL = in.readString();
        this.ingredients = new ArrayList<Ingredient>();
        in.readList(this.ingredients, Ingredient.class.getClassLoader());
        this.steps = new ArrayList<Step>();
        in.readList(this.steps, Step.class.getClassLoader());
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel source) {
            return new Recipe(source);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    // Builder logic
    public static final class Builder {
        private int id;
        private String name;
        private int servings;
        private String imageURL;
        private ArrayList<Ingredient> ingredients;
        private ArrayList<Step> steps;

        public Builder() {
        }

        public Builder id(int val) {
            id = val;
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder servings(int val) {
            servings = val;
            return this;
        }

        public Builder imageURL(String val) {
            imageURL = val;
            return this;
        }

        public Builder ingredients(ArrayList<Ingredient> val) {
            ingredients = val;
            return this;
        }

        public Builder steps(ArrayList<Step> val) {
            steps = val;
            return this;
        }

        public Recipe build() {
            return new Recipe(this);
        }
    }
}
