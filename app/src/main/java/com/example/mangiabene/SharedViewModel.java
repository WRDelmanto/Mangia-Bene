package com.example.mangiabene;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mangiabene.Models.Category;
import com.example.mangiabene.Models.Recipe;

import java.util.ArrayList;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<ArrayList<Recipe>> topRatedRecipesList = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<ArrayList<Recipe>> quickSnacksRecipesList = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<ArrayList<Category>> categoriesList = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<String> searchInput = new MutableLiveData<>("");
    private final MutableLiveData<ArrayList<Recipe>> searchInputList = new MutableLiveData<>(new ArrayList<>());

    public LiveData<ArrayList<Recipe>> getTopRatedRecipesList() {
        return topRatedRecipesList;
    }

    public void updateTopRatedRecipes(ArrayList<Recipe> newList) {
        topRatedRecipesList.setValue(newList);
    }

    public LiveData<ArrayList<Recipe>> getQuickSnacksRecipesList() {
        return quickSnacksRecipesList;
    }

    public void updateQuickSnacksRecipes(ArrayList<Recipe> newList) {
        quickSnacksRecipesList.setValue(newList);
    }

    public LiveData<ArrayList<Category>> getCategoriesList() {
        return categoriesList;
    }

    public void updateCategoriesList(ArrayList<Category> newList) {
        categoriesList.setValue(newList);
    }

    public LiveData<String> getSearchInput() {
        return searchInput;
    }

    public void updateSearchInput(String newInput) {
        searchInput.setValue(newInput);
    }

    public LiveData<ArrayList<Recipe>> getSearchInputList() {
        return searchInputList;
    }

    public void updateSearchInputList(ArrayList<Recipe> newList) {
        searchInputList.setValue(newList);
    }
}
