package com.example.mangiabene.Category;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mangiabene.Adapters.RecipesAdapter;
import com.example.mangiabene.R;
import com.example.mangiabene.Models.Category;
import com.example.mangiabene.Models.Recipe;
import com.example.mangiabene.Utils.TheMealDBAPI.Callbacks.RecipesByCategoryCallback;
import com.example.mangiabene.Utils.TheMealDBAPI.TheMealDBAPI;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {
    ArrayList<Recipe> recipesList = new ArrayList<>();

    ImageView backButton;

    RecyclerView recipesRecyclerView;
    RecyclerView.LayoutManager recipesLayoutManager;
    RecyclerView.Adapter recipesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_category);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TheMealDBAPI theMealDBAPI = new TheMealDBAPI(getApplicationContext());

        backButton = findViewById(R.id.backButton);
        recipesRecyclerView = findViewById(R.id.recipesView);

        Category category = getIntent().getParcelableExtra("category");
        assert category != null;

        backButton.setOnClickListener(v -> finish());

        // Populate recipes list
        theMealDBAPI.fetchAllRecipesByCategory(new RecipesByCategoryCallback() {
            @Override
            public void onSuccess(ArrayList<Recipe> tempRecipesList) {
                runOnUiThread(() -> {
                    recipesList.clear();
                    recipesList.addAll(tempRecipesList);
                    recipesAdapter.notifyDataSetChanged();
                });
            }

            @Override
            public void onError(String errorMessage) {
                Log.e("CategoryActivity", "Failed to fetch recipes: " + errorMessage);
            }
        }, category.getStrCategory());

        recipesLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recipesRecyclerView.setLayoutManager(recipesLayoutManager);
        recipesAdapter = new RecipesAdapter(recipesList);
        recipesRecyclerView.setAdapter(recipesAdapter);
    }
}