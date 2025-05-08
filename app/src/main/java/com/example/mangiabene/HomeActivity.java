package com.example.mangiabene;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mangiabene.Favorite.FavoriteFragment;
import com.example.mangiabene.Home.HomeFragment;
import com.example.mangiabene.Models.Category;
import com.example.mangiabene.Models.Recipe;
import com.example.mangiabene.Models.User;
import com.example.mangiabene.Profile.ProfileFragment;
import com.example.mangiabene.Search.SearchFragment;
import com.example.mangiabene.Utils.FastSharedPreferences;
import com.example.mangiabene.Utils.TheMealDBAPI.Callbacks.CategoryCallback;
import com.example.mangiabene.Utils.TheMealDBAPI.Callbacks.RecipeCallback;
import com.example.mangiabene.Utils.TheMealDBAPI.TheMealDBAPI;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    ArrayList<Recipe> topRatedRecipesList = new ArrayList<>();
    ArrayList<Recipe> quickSnacksRecipesList = new ArrayList<>();
    ArrayList<Category> categoriesList = new ArrayList<>();

    BottomNavigationView bottom_navigation;

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TheMealDBAPI theMealDBAPI = new TheMealDBAPI(getApplicationContext());
        SharedViewModel sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);

        user = FastSharedPreferences.get(getApplicationContext(), "user", new TypeToken<User>() {
        }.getType(), new User());

        // First time user
        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName("Guest");
            user.setPicture("");
            user.setFavoriteRecipes(new ArrayList<>());
        }

        FastSharedPreferences.put(getApplicationContext(), "user", user);

        Log.d("HomeFragment", "User: " + user.toString());

        if (topRatedRecipesList.isEmpty()) {
            // Populate top rated recipes list
            for (int i = 0; i < 5; i++) {
                theMealDBAPI.fetchRandomRecipe(new RecipeCallback() {
                    @Override
                    public void onSuccess(Recipe recipe) {
                        runOnUiThread(() -> {
                            topRatedRecipesList.add(recipe);
                            sharedViewModel.updateTopRatedRecipes(topRatedRecipesList);
                        });
                    }

                    @Override
                    public void onError(String errorMessage) {
                        Log.e("HomeActivity", "Failed to fetch recipe: " + errorMessage);
                    }
                });
            }
        }

        if (quickSnacksRecipesList.isEmpty()) {
            // Populate quick snacks recipes list
            for (int i = 0; i < 5; i++) {
                theMealDBAPI.fetchRandomRecipe(new RecipeCallback() {
                    @Override
                    public void onSuccess(Recipe recipe) {
                        runOnUiThread(() -> {
                            quickSnacksRecipesList.add(recipe);
                            sharedViewModel.updateQuickSnacksRecipes(quickSnacksRecipesList);
                        });
                    }

                    @Override
                    public void onError(String errorMessage) {
                        Log.e("HomeActivity", "Failed to fetch recipe: " + errorMessage);
                    }
                });
            }
        }

        if (categoriesList.isEmpty()) {
            // Populate categories list
            theMealDBAPI.fetchAllCategories(new CategoryCallback() {
                @Override
                public void onSuccess(ArrayList<Category> tempCategoriesList) {
                    runOnUiThread(() -> {
                        categoriesList = tempCategoriesList;
                        sharedViewModel.updateCategoriesList(categoriesList);
                    });
                }

                @Override
                public void onError(String errorMessage) {
                    Log.e("HomeActivity", "Failed to fetch categories: " + errorMessage);
                }
            });
        }

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new HomeFragment())
                    .commit();
        }

        bottom_navigation = findViewById(R.id.bottom_navigation);
        bottom_navigation.setOnNavigationItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            if (item.getItemId() == R.id.home_icon) {
                selectedFragment = new HomeFragment();
                Log.d("HomeActivity", "HomeFragment selected");
            }

            if (item.getItemId() == R.id.search_icon) {
                selectedFragment = new SearchFragment();
                Log.d("HomeActivity", "SearchFragment selected");
            }

            if (item.getItemId() == R.id.favorite_icon) {
                selectedFragment = new FavoriteFragment();
                Log.d("HomeActivity", "FavoriteFragment selected");
            }

            if (item.getItemId() == R.id.profile_icon) {
                selectedFragment = new ProfileFragment();
                Log.d("HomeActivity", "ProfileFragment selected");
            }

            if (selectedFragment != null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment)
                        .commit();
            }

            return true;
        });

        bottom_navigation.setOnNavigationItemReselectedListener(item -> {
            // Do nothing
        });
    }
}