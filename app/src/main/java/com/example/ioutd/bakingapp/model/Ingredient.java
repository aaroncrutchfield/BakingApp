package com.example.ioutd.bakingapp.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

/**
 * Created by ioutd on 1/11/2018.
 */
@Entity(foreignKeys = @ForeignKey(entity = Recipe.class,
        parentColumns = "id",
        childColumns = "recipeID"))
public class Ingredient {

    public enum Measurement {
        TBLSP, TSP, CUP, UNIT,
        K, G, OZ
    }

    @ColumnInfo private int recipeID;
    @ColumnInfo private String name;
    @ColumnInfo private int quantity;
    @ColumnInfo private Measurement measurement;

    public Ingredient(String name, int quantity, Measurement measurement) {
        this.name = name;
        this.quantity = quantity;
        this.measurement = measurement;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public Measurement getMeasurement() {
        return measurement;
    }
}
