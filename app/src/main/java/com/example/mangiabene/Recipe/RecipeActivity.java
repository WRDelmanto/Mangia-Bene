package com.example.mangiabene.Recipe;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.example.mangiabene.Adapters.ViewPagerAdapter;
import com.example.mangiabene.Models.Ingredient;
import com.example.mangiabene.Models.Recipe;
import com.example.mangiabene.Models.User;
import com.example.mangiabene.R;
import com.example.mangiabene.SharedViewModel;
import com.example.mangiabene.Utils.FastSharedPreferences;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecipeActivity extends AppCompatActivity {
    ImageView backButton;
    ImageView favoriteButton;
    ImageView youtubeButton;
    ImageView recipePicture;
    TextView recipeName;
    TextView recipeAreaCategory;

    ArrayList<Ingredient> ingredientsList = new ArrayList<>();
    ArrayList<String> instructionsList = new ArrayList<>();

    TabLayout tabLayout;
    ViewPager2 viewPager;

    SharedViewModel sharedViewModel;

    User user;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recipe);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        RecipeViewModel recipeViewModel = new ViewModelProvider(this).get(RecipeViewModel.class);

        sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);

        backButton = findViewById(R.id.backButton);
        favoriteButton = findViewById(R.id.favoriteButton);
        youtubeButton = findViewById(R.id.youtubeButton);
        recipePicture = findViewById(R.id.recipePicture);
        recipeName = findViewById(R.id.recipeName);
        recipeAreaCategory = findViewById(R.id.recipeAreaCategory);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        Recipe recipe = getIntent().getParcelableExtra("recipe");
        assert recipe != null;

        // Populate ingredientsList
        for (int i = 0; i < recipe.getStrIngredients().size(); i++) {
            String strIngredient = recipe.getStrIngredients().get(i);
            String strMeasure = recipe.getStrMeasures().get(i);

            if (strIngredient != null && !strIngredient.isEmpty()) {
                ingredientsList.add(new Ingredient(strIngredient, strMeasure));
            }
        }

        // Populate instructionsList
        for (String instruction : recipe.getStrInstructions().split("\n")) {
            if (!instruction.trim().isEmpty()) {
                instructionsList.add(instruction);
            }
        }

        recipeViewModel.updateIngredientsList(ingredientsList);
        recipeViewModel.updateInstructionsList(instructionsList);

        user = FastSharedPreferences.get(getApplicationContext(), "user", new TypeToken<User>() {
        }.getType(), new User());

        backButton.setOnClickListener(v -> finish());

        favoriteButton.setImageTintList(ContextCompat.getColorStateList(this, user.getFavoriteRecipes().contains(recipe) ? R.color.red : R.color.white));

        favoriteButton.setOnClickListener(v -> {
            ArrayList<Recipe> favoriteRecipes = user.getFavoriteRecipes();

            if (favoriteRecipes.contains(recipe)) {
                favoriteRecipes.remove(recipe);
                favoriteButton.setImageTintList(ContextCompat.getColorStateList(this, R.color.light_grey));
                Log.d("TAG", "Removed from favorites: " + recipe.getStrMeal());
            } else {
                favoriteRecipes.add(recipe);
                favoriteButton.setImageTintList(ContextCompat.getColorStateList(this, R.color.red));
                Log.d("TAG", "Added to favorites: " + recipe.getStrMeal());
            }

            user.setFavoriteRecipes(favoriteRecipes);
            FastSharedPreferences.put(getApplicationContext(), "user", user);
        });

        youtubeButton.setOnClickListener(view -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(recipe.getStrYoutube()))));
        Picasso.get().load(recipe.getStrMealThumb()).into(recipePicture);
        recipeName.setText(recipe.getStrMeal());
        recipeAreaCategory.setText(recipe.getStrArea() + " - " + recipe.getStrCategory());

        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            @SuppressLint("InflateParams") View tabView = LayoutInflater.from(this).inflate(R.layout.tab_recipe, null);
            TextView tabText = tabView.findViewById(R.id.tab_text);

            switch (position) {
                case 0:
                    tabText.setText("Instructions");
                    break;
                case 1:
                    tabText.setText("Ingredients");
                    break;
            }

            tab.setCustomView(tabView);
        }).attach();
    }
}