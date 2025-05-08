package com.example.mangiabene.Utils.TheMealDBAPI.Callbacks;

import com.example.mangiabene.Models.Category;

import java.util.ArrayList;

public interface CategoryCallback {
    void onSuccess(ArrayList<Category> categoriesList);
    void onError(String errorMessage);
}
