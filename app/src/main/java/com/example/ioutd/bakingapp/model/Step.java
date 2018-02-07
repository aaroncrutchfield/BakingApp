package com.example.ioutd.bakingapp.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ioutd on 1/11/2018.
 */
@Entity
public class Step implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int stepID;

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

    public int getStepID() {
        return stepID;
    }

    public void setStepID(int stepID) {
        this.stepID = stepID;
    }

    public String toString() {
        return "id= " + id
                + "\nrecipeID= " + recipeID
                + "\nshortDescription= " + shortDescription
                + "\nvideoURL= " + videoURL;

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.stepID);
        dest.writeInt(this.id);
        dest.writeInt(this.recipeID);
        dest.writeString(this.shortDescription);
        dest.writeString(this.description);
        dest.writeString(this.videoURL);
    }

    protected Step(Parcel in) {
        this.stepID = in.readInt();
        this.id = in.readInt();
        this.recipeID = in.readInt();
        this.shortDescription = in.readString();
        this.description = in.readString();
        this.videoURL = in.readString();
    }

    public static final Creator<Step> CREATOR = new Creator<Step>() {
        @Override
        public Step createFromParcel(Parcel source) {
            return new Step(source);
        }

        @Override
        public Step[] newArray(int size) {
            return new Step[size];
        }
    };
}
