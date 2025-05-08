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

import com.example.mangiabene.Category.CategoryActivity;
import com.example.mangiabene.R;
import com.example.mangiabene.Models.Category;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoriesRecyclerAdapter extends RecyclerView.Adapter<CategoriesRecyclerAdapter.ViewHolder> {
    private final ArrayList<Category> categoriesList;

    public CategoriesRecyclerAdapter(ArrayList<Category> categoriesList) {
        this.categoriesList = categoriesList;
    }

    @NonNull
    @Override
    public CategoriesRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_category_home, viewGroup, false);

        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CategoriesRecyclerAdapter.ViewHolder viewHolder, int position) {
        Category category = categoriesList.get(position);

        viewHolder.categoryName.setText(category.getStrCategory());

        Picasso.get().load(category.getStrCategoryThumb()).into(viewHolder.categoryImage);

        viewHolder.categoryItem.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), CategoryActivity.class);
            intent.putExtra("category", category);

            Log.d("CategoriesRecyclerAdapter", category.getStrCategory() + " selected");

            view.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout categoryItem;
        ImageView categoryImage;
        TextView categoryName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            categoryItem = itemView.findViewById(R.id.category_item);
            categoryImage = itemView.findViewById(R.id.category_image);
            categoryName = itemView.findViewById(R.id.category_name);
        }
    }
}
