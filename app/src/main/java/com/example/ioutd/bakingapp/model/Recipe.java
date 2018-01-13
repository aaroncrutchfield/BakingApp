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

    @Ignore private List<Ingredient> ingredients;
    @Ignore private List<Step> steps;

    public Recipe(int id, String name, List<Ingredient> ingredients, int servings, List<Step> steps, String imageURL) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.servings = servings;
        this.steps = steps;
        this.imageURL = imageURL;
    }

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

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

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

    public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel source) {
            return new Recipe(source);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };
}
