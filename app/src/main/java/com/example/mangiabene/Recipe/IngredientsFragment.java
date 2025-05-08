package com.example.mangiabene.Recipe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mangiabene.Adapters.IngredientsRecyclerAdapter;
import com.example.mangiabene.R;
import com.example.mangiabene.Models.Ingredient;

import java.util.ArrayList;

public class IngredientsFragment extends Fragment {
    ArrayList<Ingredient> ingredientsList = new ArrayList<>();

    RecyclerView ingredientsRecyclerView;
    RecyclerView.LayoutManager ingredientsLayoutManager;
    RecyclerView.Adapter ingredientsAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ingredients, container, false);
    }

    @Override
    public void onViewCreated(@androidx.annotation.NonNull View view, @androidx.annotation.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ingredientsRecyclerView = view.findViewById(R.id.ingredientsView);

        RecipeViewModel recipeViewModel = new ViewModelProvider(requireActivity()).get(RecipeViewModel.class);

        recipeViewModel.getIngredientsList().observe(getViewLifecycleOwner(), updatedList -> {
            ingredientsList.clear();
            ingredientsList.addAll(updatedList);
            ingredientsAdapter.notifyDataSetChanged();
        });

        ingredientsLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        ingredientsRecyclerView.setLayoutManager(ingredientsLayoutManager);
        ingredientsAdapter = new IngredientsRecyclerAdapter(ingredientsList);
        ingredientsRecyclerView.setAdapter(ingredientsAdapter);
    }
}