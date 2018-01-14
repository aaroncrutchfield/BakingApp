package com.example.ioutd.bakingapp.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.os.Parcel;
import android.os.Parcelable;

import java.net.URL;

/**
 * Created by ioutd on 1/11/2018.
 */
@Entity(foreignKeys = @ForeignKey(entity = Recipe.class,
        parentColumns = "id",
        childColumns = "recipeID"))
public class Step implements Parcelable {
    @ColumnInfo private int id;
    @ColumnInfo private int recipeID;
    @ColumnInfo private String shortDescription;
    @ColumnInfo private String description;
    @ColumnInfo private String videoURL;

    // Default constructor
    public Step(int id, int recipeID, String shortDescription, String description, String videoURL) {
        this.id = id;
        this.recipeID = recipeID;
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoURL = videoURL;
    }

    // Builder constructor
    private Step(Builder builder) {
        setId(builder.id);
        setRecipeID(builder.recipeID);
        setShortDescription(builder.shortDescription);
        setDescription(builder.description);
        setVideoURL(builder.videoURL);
    }

    // Getters
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

    public String getVideoURL() {
        return videoURL;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Setters
    public void setRecipeID(int recipeID) {
        this.recipeID = recipeID;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }


    // Parcelable logic
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.recipeID);
        dest.writeString(this.shortDescription);
        dest.writeString(this.description);
        dest.writeString(this.videoURL);
    }

    protected Step(Parcel in) {
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

    // Builder logic
    public static final class Builder {
        private int id;
        private int recipeID;
        private String shortDescription;
        private String description;
        private String videoURL;

        public Builder() {
        }

        public Builder id(int val) {
            id = val;
            return this;
        }

        public Builder recipeID(int val) {
            recipeID = val;
            return this;
        }

        public Builder shortDescription(String val) {
            shortDescription = val;
            return this;
        }

        public Builder description(String val) {
            description = val;
            return this;
        }

        public Builder videoURL(String val) {
            videoURL = val;
            return this;
        }

        public Step build() {
            return new Step(this);
        }
    }
}
