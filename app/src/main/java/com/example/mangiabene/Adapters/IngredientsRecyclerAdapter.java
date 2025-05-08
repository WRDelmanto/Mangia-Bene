package com.example.mangiabene.Adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mangiabene.R;
import com.example.mangiabene.Models.Ingredient;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class IngredientsRecyclerAdapter extends RecyclerView.Adapter<IngredientsRecyclerAdapter.ViewHolder> {
    private final ArrayList<Ingredient> ingredientsList;

    public IngredientsRecyclerAdapter(ArrayList<Ingredient> ingredientsList) {
        this.ingredientsList = ingredientsList;
    }

    @NonNull
    @Override
    public IngredientsRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_ingredient, viewGroup, false);

        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull IngredientsRecyclerAdapter.ViewHolder viewHolder, int position) {
        Ingredient ingredient = ingredientsList.get(position);
        viewHolder.recipeMeasureIngredient.setText((ingredient.getStrMeasure() + " " + ingredient.getStrIngredient()).trim());

        Picasso.get().load("https://www.themealdb.com/images/ingredients/" + ingredient.getStrIngredient() + "-Small.png").into(viewHolder.ingredientPicture);
    }

    @Override
    public int getItemCount() {
        return ingredientsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ingredientPicture;
        TextView recipeMeasureIngredient;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ingredientPicture = itemView.findViewById(R.id.ingredientPicture);
            recipeMeasureIngredient = itemView.findViewById(R.id.recipeMeasureIngredient);
        }
    }
}
