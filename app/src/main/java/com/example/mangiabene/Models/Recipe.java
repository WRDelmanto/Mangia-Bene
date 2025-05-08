package com.example.mangiabene.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Recipe implements Parcelable {
    private final String idMeal;
    private final String strMeal;
    private final String strDrinkAlternate;
    private final String strCategory;
    private final String strArea;
    private final String strInstructions;
    private final String strMealThumb;
    private final String strTags;
    private final String strYoutube;
    private final List<String> strIngredients;
    private final List<String> strMeasures;
    private final String strSource;
    private final String strImageSource;
    private final String strCreativeCommonsConfirmed;
    private final String dateModified;

    public Recipe(String idMeal,
                  String strMeal,
                  String strDrinkAlternate,
                  String strCategory,
                  String strArea,
                  String strInstructions,
                  String strMealThumb,
                  String strTags,
                  String strYoutube,
                  List<String> strIngredients,
                  List<String> strMeasures,
                  String strSource,
                  String strImageSource,
                  String strCreativeCommonsConfirmed,
                  String dateModified
    ) {
        this.idMeal = idMeal;
        this.strMeal = strMeal;
        this.strDrinkAlternate = strDrinkAlternate;
        this.strCategory = strCategory;
        this.strArea = strArea;
        this.strInstructions = strInstructions;
        this.strMealThumb = strMealThumb;
        this.strTags = strTags;
        this.strYoutube = strYoutube;
        this.strIngredients = strIngredients != null ? strIngredients : new ArrayList<>();
        this.strMeasures = strMeasures != null ? strMeasures : new ArrayList<>();
        this.strSource = strSource;
        this.strImageSource = strImageSource;
        this.strCreativeCommonsConfirmed = strCreativeCommonsConfirmed;
        this.dateModified = dateModified;
    }

    protected Recipe(Parcel in) {
        idMeal = in.readString();
        strMeal = in.readString();
        strDrinkAlternate = in.readString();
        strCategory = in.readString();
        strArea = in.readString();
        strInstructions = in.readString();
        strMealThumb = in.readString();
        strTags = in.readString();
        strYoutube = in.readString();
        strIngredients = in.createStringArrayList();
        strMeasures = in.createStringArrayList();
        strSource = in.readString();
        strImageSource = in.readString();
        strCreativeCommonsConfirmed = in.readString();
        dateModified = in.readString();
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(idMeal);
        dest.writeString(strMeal);
        dest.writeString(strDrinkAlternate);
        dest.writeString(strCategory);
        dest.writeString(strArea);
        dest.writeString(strInstructions);
        dest.writeString(strMealThumb);
        dest.writeString(strTags);
        dest.writeString(strYoutube);
        dest.writeStringList(strIngredients);
        dest.writeStringList(strMeasures);
        dest.writeString(strSource);
        dest.writeString(strImageSource);
        dest.writeString(strCreativeCommonsConfirmed);
        dest.writeString(dateModified);
    }

    public String getIdMeal() {
        return idMeal;
    }

    public String getStrMeal() {
        return strMeal;
    }

    public String getStrDrinkAlternate() {
        return strDrinkAlternate;
    }

    public String getStrCategory() {
        return strCategory;
    }

    public String getStrArea() {
        return strArea;
    }

    public String getStrInstructions() {
        return strInstructions;
    }

    public String getStrMealThumb() {
        return strMealThumb;
    }

    public String getStrTags() {
        return strTags;
    }

    public String getStrYoutube() {
        return strYoutube;
    }

    public List<String> getStrIngredients() {
        return strIngredients;
    }

    public List<String> getStrMeasures() {
        return strMeasures;
    }

    public String getStrSource() {
        return strSource;
    }

    public String getStrImageSource() {
        return strImageSource;
    }

    public String getStrCreativeCommonsConfirmed() {
        return strCreativeCommonsConfirmed;
    }

    public String getDateModified() {
        return dateModified;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Recipe recipe = (Recipe) obj;
        return idMeal.equals(recipe.idMeal);
    }

    @Override
    public int hashCode() {
        return idMeal.hashCode();
    }

}
