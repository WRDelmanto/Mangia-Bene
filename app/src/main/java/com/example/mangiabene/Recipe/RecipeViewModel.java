package com.example.mangiabene.Recipe;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mangiabene.Models.Ingredient;

import java.util.ArrayList;

public class RecipeViewModel extends ViewModel {
    private final MutableLiveData<ArrayList<Ingredient>> ingredientsList = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<ArrayList<String>> instructionsList = new MutableLiveData<>(new ArrayList<>());

    public LiveData<ArrayList<Ingredient>> getIngredientsList() {
        return ingredientsList;
    }

    public void updateIngredientsList(ArrayList<Ingredient> newList) {
        ingredientsList.setValue(newList);
    }

    public LiveData<ArrayList<String>> getInstructionsList() {
        return instructionsList;
    }

    public void updateInstructionsList(ArrayList<String> newList) {
        instructionsList.setValue(newList);
    }
}
