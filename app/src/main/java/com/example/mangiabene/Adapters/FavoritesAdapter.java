package com.example.mangiabene.Adapters;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mangiabene.Models.Recipe;
import com.example.mangiabene.R;
import com.example.mangiabene.Recipe.RecipeActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {
    private final ArrayList<Recipe> recipesList;

    public FavoritesAdapter(ArrayList<Recipe> recipesList) {
        this.recipesList = recipesList;
    }

    @NonNull
    @Override
    public FavoritesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_favorites, viewGroup, false);

        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull FavoritesAdapter.ViewHolder viewHolder, int position) {
        Recipe recipe = recipesList.get(position);

        viewHolder.recipeName.setText(recipe.getStrMeal());
        viewHolder.recipeAreaCategory.setText(recipe.getStrArea() + " - " + recipe.getStrCategory());

        Picasso.get().load(recipe.getStrMealThumb() + "/preview").into(viewHolder.recipePicture);

        viewHolder.favoriteItemLayout.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), RecipeActivity.class);
            intent.putExtra("recipe", recipe);

            Log.d("RecipesAdapter", recipe.getStrMeal() + " selected");

            view.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return recipesList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout favoriteItemLayout;
        ImageView recipePicture;
        TextView recipeName;
        TextView recipeAreaCategory;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            favoriteItemLayout = itemView.findViewById(R.id.favorite_item_layout);
            recipePicture = itemView.findViewById(R.id.recipePicture);
            recipeName = itemView.findViewById(R.id.recipeName);
            recipeAreaCategory = itemView.findViewById(R.id.recipeAreaCategory);
        }
    }
}
