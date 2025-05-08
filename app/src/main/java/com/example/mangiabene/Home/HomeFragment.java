package com.example.mangiabene.Home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mangiabene.Adapters.CategoriesRecyclerAdapter;
import com.example.mangiabene.Adapters.QuickSnacksRecyclerAdapter;
import com.example.mangiabene.Adapters.TopRatedRecipesRecyclerAdapter;
import com.example.mangiabene.Models.Category;
import com.example.mangiabene.Models.Recipe;
import com.example.mangiabene.Models.User;
import com.example.mangiabene.R;
import com.example.mangiabene.SharedViewModel;
import com.example.mangiabene.Utils.FastSharedPreferences;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    ImageView userPicture;
    TextView username;

    SharedViewModel sharedViewModel;

    ArrayList<Recipe> topRatedRecipesList = new ArrayList<>();
    ArrayList<Recipe> quickSnacksRecipesList = new ArrayList<>();
    ArrayList<Category> categoriesList = new ArrayList<>();

    RecyclerView topRatedRecipesRecyclerView;
    RecyclerView.LayoutManager topRatedRecipesLayoutManager;
    RecyclerView.Adapter topRatedRecipesAdapter;

    RecyclerView quickSnacksRecyclerView;
    RecyclerView.LayoutManager quickSnacksLayoutManager;
    RecyclerView.Adapter quickSnacksAdapter;

    RecyclerView categoriesRecyclerView;
    RecyclerView.LayoutManager categoriesLayoutManager;
    RecyclerView.Adapter categoriesAdapter;

    User user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onViewCreated(@androidx.annotation.NonNull View view, @androidx.annotation.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userPicture = view.findViewById(R.id.profile_picture);
        username = view.findViewById(R.id.username);
        topRatedRecipesRecyclerView = view.findViewById(R.id.topRatedRecipes);
        quickSnacksRecyclerView = view.findViewById(R.id.quickSnacksRecipes);
        categoriesRecyclerView = view.findViewById(R.id.categoriesView);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        sharedViewModel.getTopRatedRecipesList().observe(getViewLifecycleOwner(), updatedList -> {
            topRatedRecipesList.clear();
            topRatedRecipesList.addAll(updatedList);
            topRatedRecipesAdapter.notifyDataSetChanged();
        });

        sharedViewModel.getQuickSnacksRecipesList().observe(getViewLifecycleOwner(), updatedList -> {
            quickSnacksRecipesList.clear();
            quickSnacksRecipesList.addAll(updatedList);
            quickSnacksAdapter.notifyDataSetChanged();
        });

        sharedViewModel.getCategoriesList().observe(getViewLifecycleOwner(), updatedList -> {
            categoriesList.clear();
            categoriesList.addAll(updatedList);
            categoriesAdapter.notifyDataSetChanged();
        });

        // Handle top-rated recipes
        topRatedRecipesLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        topRatedRecipesRecyclerView.setLayoutManager(topRatedRecipesLayoutManager);
        topRatedRecipesAdapter = new TopRatedRecipesRecyclerAdapter(getContext(), topRatedRecipesList);
        topRatedRecipesRecyclerView.setAdapter(topRatedRecipesAdapter);

        // Handle quick snacks recipes
        quickSnacksLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        quickSnacksRecyclerView.setLayoutManager(quickSnacksLayoutManager);
        quickSnacksAdapter = new QuickSnacksRecyclerAdapter(quickSnacksRecipesList);
        quickSnacksRecyclerView.setAdapter(quickSnacksAdapter);

        // Handle categories
        categoriesLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        categoriesRecyclerView.setLayoutManager(categoriesLayoutManager);
        categoriesAdapter = new CategoriesRecyclerAdapter(categoriesList);
        categoriesRecyclerView.setAdapter(categoriesAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();

        user = FastSharedPreferences.get(getContext(), "user", new TypeToken<User>() {
        }.getType(), new User());

        username.setText(user.getName());
        if (user.getPicture().isEmpty()) {
            userPicture.setImageResource(R.mipmap.ic_launcher);
        } else {
            Glide.with(this)
                    .load(user.getPicture())
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(userPicture);
        }
    }
}
