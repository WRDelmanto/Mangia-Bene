package com.example.mangiabene.Search;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mangiabene.Adapters.RecipesAdapter;
import com.example.mangiabene.R;
import com.example.mangiabene.SharedViewModel;
import com.example.mangiabene.Models.Recipe;
import com.example.mangiabene.Utils.TheMealDBAPI.Callbacks.RecipesByInputCallback;
import com.example.mangiabene.Utils.TheMealDBAPI.TheMealDBAPI;

import java.util.ArrayList;

public class SearchFragment extends Fragment {
    ArrayList<Recipe> recipesList = new ArrayList<>();

    EditText searchInput;

    RecyclerView recipesRecyclerView;
    RecyclerView.LayoutManager recipesLayoutManager;
    RecyclerView.Adapter recipesAdapter;

    private Handler searchHandler = new Handler();
    private Runnable searchRunnable;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@androidx.annotation.NonNull View view, @androidx.annotation.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TheMealDBAPI theMealDBAPI = new TheMealDBAPI(getContext());

        searchInput = view.findViewById(R.id.searchInput);
        recipesRecyclerView = view.findViewById(R.id.recipesView);

        SharedViewModel sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        searchInput.setText(sharedViewModel.getSearchInput().getValue());
        if (sharedViewModel.getSearchInputList().getValue() != null) {
            recipesList.addAll(sharedViewModel.getSearchInputList().getValue());
        }

        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Do nothing
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Do nothing
            }

            @Override
            public void afterTextChanged(Editable editable) {
                searchHandler.removeCallbacks(searchRunnable);

                searchRunnable = () -> {
                    sharedViewModel.updateSearchInput(editable.toString());

                    theMealDBAPI.fetchRecipesByInput(new RecipesByInputCallback() {
                        @Override
                        public void onSuccess(ArrayList<Recipe> tempRecipesList) {
                            getActivity().runOnUiThread(() -> {
                                recipesList.clear();
                                recipesList.addAll(tempRecipesList);
                                recipesAdapter.notifyDataSetChanged();

                                sharedViewModel.updateSearchInputList(recipesList);
                            });
                        }

                        @Override
                        public void onError(String errorMessage) {
                            Log.e("SearchFragment", "Failed to fetch recipes: " + errorMessage);
                        }
                    }, editable.toString());
                };

                searchHandler.postDelayed(searchRunnable, 1000);
            }
        });

        recipesLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recipesRecyclerView.setLayoutManager(recipesLayoutManager);
        recipesAdapter = new RecipesAdapter(recipesList);
        recipesRecyclerView.setAdapter(recipesAdapter);
    }
}