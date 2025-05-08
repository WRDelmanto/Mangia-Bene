package com.example.mangiabene.Utils.TheMealDBAPI.Callbacks;

import com.example.mangiabene.Models.Recipe;

public interface RecipeCallback {
    void onSuccess(Recipe recipe);
    void onError(String errorMessage);
}
