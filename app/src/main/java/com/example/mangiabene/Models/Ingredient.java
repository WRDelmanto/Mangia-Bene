package com.example.mangiabene.Models;

public class Ingredient {
    private final String strIngredient;
    private final String strMeasure;

    public Ingredient(String strIngredient, String strMeasure) {
        this.strIngredient = strIngredient;
        this.strMeasure = strMeasure;
    }

    public String getStrIngredient() {
        return strIngredient;
    }

    public String getStrMeasure() {
        return strMeasure;
    }
}
