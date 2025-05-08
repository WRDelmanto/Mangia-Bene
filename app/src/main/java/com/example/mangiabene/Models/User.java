package com.example.mangiabene.Models;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class User {
    private String name;
    private String picture;
    private ArrayList<Recipe> favoriteRecipes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public ArrayList<Recipe> getFavoriteRecipes() {
        return favoriteRecipes;
    }

    public void setFavoriteRecipes(ArrayList<Recipe> favoriteRecipes) {
        this.favoriteRecipes = favoriteRecipes;
    }

    @NonNull
    public String toString() {
        return "User{name=" + name + ", picture=" + picture + ", favoriteRecipes=" + favoriteRecipes + "}";
    }
}
