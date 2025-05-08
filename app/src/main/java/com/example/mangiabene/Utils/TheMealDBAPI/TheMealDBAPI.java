package com.example.mangiabene.Utils.TheMealDBAPI;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.mangiabene.Models.Category;
import com.example.mangiabene.Models.Recipe;
import com.example.mangiabene.Utils.TheMealDBAPI.Callbacks.CategoryCallback;
import com.example.mangiabene.Utils.TheMealDBAPI.Callbacks.RecipeCallback;
import com.example.mangiabene.Utils.TheMealDBAPI.Callbacks.RecipesByCategoryCallback;
import com.example.mangiabene.Utils.TheMealDBAPI.Callbacks.RecipesByInputCallback;
import com.example.mangiabene.Utils.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TheMealDBAPI {
    // https://www.themealdb.com/api.php

    RequestQueue requestQueue;
    Context context;

    public TheMealDBAPI(Context context) {
        this.context = context;
        requestQueue = VolleySingleton.getInstance(context).getRequestQueue();
    }

    public void fetchRandomRecipe(RecipeCallback callback) {
        String url = "https://www.themealdb.com/api/json/v1/1/random.php";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                JSONArray jsonArray = response.getJSONArray("meals");
                JSONObject jsonObject = jsonArray.getJSONObject(0);

                Log.d("TheMealDBAPI", "Recipe: " + jsonObject.toString());

                List<String> ingredients = new ArrayList<>();
                List<String> measures = new ArrayList<>();

                for (int j = 1; j <= 20; j++) {
                    String ingredient = jsonObject.getString("strIngredient" + j);
                    String measure = jsonObject.getString("strMeasure" + j);

                    if (!ingredient.trim().isEmpty()) {
                        ingredients.add(ingredient);
                        measures.add(measure);
                    }
                }

                Recipe recipe = new Recipe(
                        jsonObject.getString("idMeal"),
                        jsonObject.getString("strMeal"),
                        jsonObject.optString("strDrinkAlternate"),
                        jsonObject.getString("strCategory"),
                        jsonObject.getString("strArea"),
                        jsonObject.getString("strInstructions"),
                        jsonObject.getString("strMealThumb"),
                        jsonObject.optString("strTags"),
                        jsonObject.optString("strYoutube"),
                        ingredients,
                        measures,
                        jsonObject.optString("strSource"),
                        jsonObject.optString("strImageSource"),
                        jsonObject.optString("strCreativeCommonsConfirmed"),
                        jsonObject.optString("dateModified")
                );

                callback.onSuccess(recipe);
            } catch (Exception exception) {
                Log.e("TheMealDBAPI", "Error parsing recipe data: ", exception);
                callback.onError("Error fetching recipe");
            }
        }, error -> {
            Log.e("TheMealDBAPI", "Request error: " + error.getMessage());
            callback.onError(error.getMessage());
        });

        requestQueue.add(jsonObjectRequest);
    }

    public void fetchAllCategories(CategoryCallback callback) {
        String url = "https://www.themealdb.com/api/json/v1/1/categories.php";

        ArrayList<Category> categories = new ArrayList<>();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                JSONArray jsonArray = response.getJSONArray("categories");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    Category category = new Category(
                            jsonObject.getString("idCategory"),
                            jsonObject.getString("strCategory"),
                            jsonObject.getString("strCategoryThumb"),
                            jsonObject.getString("strCategoryDescription")
                    );

                    categories.add(category);
                }

                Log.d("TheMealDBAPI", "Categories: " + categories);
                callback.onSuccess(categories);
            } catch (Exception exception) {
                Log.e("TheMealDBAPI", "Error parsing category data: ", exception);
                callback.onError("Error fetching category");
            }
        }, error -> {
            Log.e("TheMealDBAPI", "Request error: " + error.getMessage());
            callback.onError(error.getMessage());
        });

        requestQueue.add(jsonObjectRequest);
    }

    public void fetchAllRecipesByCategory(RecipesByCategoryCallback recipesByCategoryCallback, String category) {
        String url = "https://www.themealdb.com/api/json/v1/1/filter.php?c=" + category;

        ArrayList<Recipe> recipes = new ArrayList<>();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                JSONArray jsonArray = response.getJSONArray("meals");

                if (jsonArray.length() == 0) {
                    recipesByCategoryCallback.onSuccess(recipes);
                    return;
                }

                final int totalRecipes = jsonArray.length();
                final int[] completedRequests = {0};

                for (int i = 0; i < totalRecipes; i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String mealId = jsonObject.getString("idMeal");

                    fetchRecipeById(new RecipeCallback() {
                        @Override
                        public void onSuccess(Recipe recipe) {
                            recipes.add(recipe);

                            completedRequests[0]++;
                            if (completedRequests[0] == totalRecipes) {
                                recipesByCategoryCallback.onSuccess(recipes);
                            }
                        }

                        @Override
                        public void onError(String errorMessage) {
                            Log.e("TheMealDBAPI", "Error fetching recipe details: " + errorMessage);
                            completedRequests[0]++;
                            if (completedRequests[0] == totalRecipes) {
                                recipesByCategoryCallback.onSuccess(recipes);
                            }
                        }
                    }, mealId);
                }

            } catch (Exception exception) {
                Log.e("TheMealDBAPI", "Error parsing recipe data: ", exception);
                recipesByCategoryCallback.onError("Error fetching recipes");
            }
        }, error -> {
            Log.e("TheMealDBAPI", "Request error: " + error.getMessage());
            recipesByCategoryCallback.onError(error.getMessage());
        });

        requestQueue.add(jsonObjectRequest);
    }


    public void fetchRecipeById(RecipeCallback callback, String id) {
        String url = "https://www.themealdb.com/api/json/v1/1/lookup.php?i=" + id;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                JSONArray jsonArray = response.getJSONArray("meals");
                JSONObject jsonObject = jsonArray.getJSONObject(0);

                Log.d("TheMealDBAPI", "Recipe: " + jsonObject.toString());

                List<String> ingredients = new ArrayList<>();
                List<String> measures = new ArrayList<>();

                for (int j = 1; j <= 20; j++) {
                    String ingredient = jsonObject.getString("strIngredient" + j);
                    String measure = jsonObject.getString("strMeasure" + j);

                    if (!ingredient.trim().isEmpty()) {
                        ingredients.add(ingredient);
                        measures.add(measure);
                    }
                }

                Recipe recipe = new Recipe(
                        jsonObject.getString("idMeal"),
                        jsonObject.getString("strMeal"),
                        jsonObject.optString("strDrinkAlternate"),
                        jsonObject.getString("strCategory"),
                        jsonObject.getString("strArea"),
                        jsonObject.getString("strInstructions"),
                        jsonObject.getString("strMealThumb"),
                        jsonObject.optString("strTags"),
                        jsonObject.optString("strYoutube"),
                        ingredients,
                        measures,
                        jsonObject.optString("strSource"),
                        jsonObject.optString("strImageSource"),
                        jsonObject.optString("strCreativeCommonsConfirmed"),
                        jsonObject.optString("dateModified")
                );

                callback.onSuccess(recipe);
            } catch (Exception exception) {
                Log.e("TheMealDBAPI", "Error parsing recipe data: ", exception);
                callback.onError("Error fetching recipe");
            }
        }, error -> {
            Log.e("TheMealDBAPI", "Request error: " + error.getMessage());
            callback.onError(error.getMessage());
        });

        requestQueue.add(jsonObjectRequest);
    }

    public void fetchRecipesByInput(RecipesByInputCallback recipesByInputCallback, String input) {
        String url = "https://www.themealdb.com/api/json/v1/1/search.php?s=" + input;

        ArrayList<Recipe> recipes = new ArrayList<>();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                JSONArray jsonArray = response.getJSONArray("meals");

                if (jsonArray.length() == 0) {
                    recipesByInputCallback.onSuccess(recipes);
                    return;
                }

                final int totalRecipes = jsonArray.length();
                final int[] completedRequests = {0};

                for (int i = 0; i < totalRecipes; i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String mealId = jsonObject.getString("idMeal");

                    fetchRecipeById(new RecipeCallback() {
                        @Override
                        public void onSuccess(Recipe recipe) {
                            recipes.add(recipe);

                            completedRequests[0]++;
                            if (completedRequests[0] == totalRecipes) {
                                recipesByInputCallback.onSuccess(recipes);
                            }
                        }

                        @Override
                        public void onError(String errorMessage) {
                            Log.e("TheMealDBAPI", "Error fetching recipe details: " + errorMessage);
                            completedRequests[0]++;
                            if (completedRequests[0] == totalRecipes) {
                                recipesByInputCallback.onSuccess(recipes);
                            }
                        }
                    }, mealId);
                }

            } catch (Exception exception) {
                Log.e("TheMealDBAPI", "Error parsing recipe data: ", exception);
                recipesByInputCallback.onError("Error fetching recipes");
            }
        }, error -> {
            Log.e("TheMealDBAPI", "Request error: " + error.getMessage());
            recipesByInputCallback.onError(error.getMessage());
        });

        requestQueue.add(jsonObjectRequest);
    }
}
