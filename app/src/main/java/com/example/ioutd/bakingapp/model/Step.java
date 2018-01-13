package com.example.ioutd.bakingapp.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

import java.net.URL;

/**
 * Created by ioutd on 1/11/2018.
 */
@Entity(foreignKeys = @ForeignKey(entity = Recipe.class,
        parentColumns = "id",
        childColumns = "recipeID"))
public class Step {
    @ColumnInfo private int id;
    @ColumnInfo private int recipeID;
    @ColumnInfo private String shortDescription;
    @ColumnInfo private String description;
    @ColumnInfo private URL videoURL;

    public Step(int id, int recipeID, String shortDescription, String description, URL videoURL) {
        this.id = id;
        this.recipeID = recipeID;
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoURL = videoURL;
    }

    public int getId() {
        return id;
    }

    public int getRecipeID() {
        return recipeID;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public URL getVideoURL() {
        return videoURL;
    }
}
