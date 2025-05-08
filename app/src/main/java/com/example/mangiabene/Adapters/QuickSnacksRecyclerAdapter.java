package com.example.mangiabene.Adapters;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mangiabene.R;
import com.example.mangiabene.Recipe.RecipeActivity;
import com.example.mangiabene.Models.Recipe;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class QuickSnacksRecyclerAdapter extends RecyclerView.Adapter<QuickSnacksRecyclerAdapter.ViewHolder> {
    private final ArrayList<Recipe> recipesList;

    public QuickSnacksRecyclerAdapter(ArrayList<Recipe> recipesList) {
        this.recipesList = recipesList;
    }

    @NonNull
    @Override
    public QuickSnacksRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recipe_home, viewGroup, false);

        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull QuickSnacksRecyclerAdapter.ViewHolder viewHolder, int position) {
        Recipe recipe = recipesList.get(position);

        viewHolder.recipeName.setText(recipe.getStrMeal());
        viewHolder.recipeAreaCategory.setText(recipe.getStrArea() + " - " + recipe.getStrCategory());

        Picasso.get().load(recipe.getStrMealThumb() + "/preview").into(viewHolder.recipeImage);

        viewHolder.recipeItem.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), RecipeActivity.class);
            intent.putExtra("recipe", recipe);

            Log.d("QuickSnacksRecyclerAdapter", recipe.getStrMeal() + " selected");

            view.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return recipesList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout recipeItem;
        ImageView recipeImage;
        TextView recipeName;
        TextView recipeAreaCategory;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            recipeItem = itemView.findViewById(R.id.recipe_item);
            recipeImage = itemView.findViewById(R.id.recipe_image);
            recipeName = itemView.findViewById(R.id.recipe_name);
            recipeAreaCategory = itemView.findViewById(R.id.recipe_area_category);
        }
    }
}
