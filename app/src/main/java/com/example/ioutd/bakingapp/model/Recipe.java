package com.example.ioutd.bakingapp.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by ioutd on 1/11/2018.
 */

@Entity
public class Recipe implements Parcelable {
    @PrimaryKey private int id;

    @ColumnInfo private String name;
    @ColumnInfo private int servings;
    @ColumnInfo private URL imageURL;

    @Ignore private ArrayList<Ingredient> ingredients;
    @Ignore private ArrayList<Step> steps;


    public Recipe(int id, String name, ArrayList<Ingredient> ingredients, int servings, ArrayList<Step> steps, URL imageURL) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.servings = servings;
        this.steps = steps;
        this.imageURL = imageURL;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public int getServings() {
        return servings;
    }

    public ArrayList<Step> getSteps() {
        return steps;
    }

    public URL getImageURL() {
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
        dest.writeList(this.ingredients);
        dest.writeInt(this.servings);
        dest.writeList(this.steps);
        dest.writeSerializable(this.imageURL);
    }

    public Recipe() {
    }

    protected Recipe(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.ingredients = new ArrayList<Ingredient>();
        in.readList(this.ingredients, Ingredient.class.getClassLoader());
        this.servings = in.readInt();
        this.steps = new ArrayList<Step>();
        in.readList(this.steps, Step.class.getClassLoader());
        this.imageURL = (URL) in.readSerializable();
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
