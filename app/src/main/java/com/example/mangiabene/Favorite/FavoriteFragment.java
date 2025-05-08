package com.example.mangiabene.Favorite;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mangiabene.Adapters.FavoritesAdapter;
import com.example.mangiabene.Models.User;
import com.example.mangiabene.R;
import com.example.mangiabene.SharedViewModel;
import com.example.mangiabene.Utils.FastSharedPreferences;
import com.google.gson.reflect.TypeToken;

public class FavoriteFragment extends Fragment {
    TextView favoriteTitle;
    TextView noFavorite;

    RecyclerView favoritesRecyclerView;
    RecyclerView.LayoutManager favoritesLayoutManager;
    RecyclerView.Adapter favoritesAdapter;

    SharedViewModel sharedViewModel;

    User user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(@androidx.annotation.NonNull View view, @androidx.annotation.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        favoriteTitle = view.findViewById(R.id.header_title);
        noFavorite = view.findViewById(R.id.no_favorite_items);
        favoritesRecyclerView = view.findViewById(R.id.recycler_view_favorite);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        favoritesLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        favoritesRecyclerView.setLayoutManager(favoritesLayoutManager);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onResume() {
        super.onResume();

        user = FastSharedPreferences.get(getContext(), "user", new TypeToken<User>() {
        }.getType(), new User());

        if (user.getFavoriteRecipes().isEmpty()) {
            noFavorite.setVisibility(View.VISIBLE);
            favoritesRecyclerView.setVisibility(View.GONE);
            favoriteTitle.setText("Favorites");
        } else {
            noFavorite.setVisibility(View.GONE);
            favoritesRecyclerView.setVisibility(View.VISIBLE);
            favoriteTitle.setText("Favorites (" + user.getFavoriteRecipes().size() + ")");
        }

        favoritesAdapter = new FavoritesAdapter(user.getFavoriteRecipes());
        favoritesRecyclerView.setAdapter(favoritesAdapter);
    }
}