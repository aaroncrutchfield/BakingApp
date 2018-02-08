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
public class Step {
    @PrimaryKey
    @NonNull
    private String stepID;

    @ColumnInfo
    private int id;

    @ColumnInfo (name = "recipeID")
    private int recipeID;

    @ColumnInfo private String shortDescription;
    @ColumnInfo private String description;
    @ColumnInfo private String videoURL;

    public Step() {
    }

    public int getRecipeID() {
        return recipeID;
    }

    public void setRecipeID(int recipeID) {
        this.recipeID = recipeID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getStepID() {
        return stepID;
    }

    public void setStepID(String stepID) {
        this.stepID = stepID;
    }

    public String toString() {
        return "id= " + id
                + "\nrecipeID= " + recipeID
                + "\nshortDescription= " + shortDescription
                + "\nvideoURL= " + videoURL;

    }
}
