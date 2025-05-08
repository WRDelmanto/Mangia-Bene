package com.example.mangiabene.Utils.TheMealDBAPI.Callbacks;

import com.example.mangiabene.Models.Recipe;

import java.util.ArrayList;

public interface RecipesByCategoryCallback {
    void onSuccess(ArrayList<Recipe> recipesList);
    void onError(String errorMessage);
}
