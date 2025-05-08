package com.example.mangiabene.Recipe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mangiabene.Adapters.InstructionsRecyclerAdapter;
import com.example.mangiabene.R;

import java.util.ArrayList;

public class InstructionsFragment extends Fragment {
    ArrayList<String> instructionsList = new ArrayList<>();

    RecyclerView instructionsRecyclerView;
    RecyclerView.LayoutManager instructionsLayoutManager;
    RecyclerView.Adapter instructionsAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_intructions, container, false);
    }

    @Override
    public void onViewCreated(@androidx.annotation.NonNull View view, @androidx.annotation.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        instructionsRecyclerView = view.findViewById(R.id.instructionsView);

        RecipeViewModel recipeViewModel = new ViewModelProvider(requireActivity()).get(RecipeViewModel.class);

        recipeViewModel.getInstructionsList().observe(getViewLifecycleOwner(), updatedList -> {
            instructionsList.clear();
            instructionsList.addAll(updatedList);
            instructionsAdapter.notifyDataSetChanged();
        });

        instructionsLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        instructionsRecyclerView.setLayoutManager(instructionsLayoutManager);
        instructionsAdapter = new InstructionsRecyclerAdapter(instructionsList);
        instructionsRecyclerView.setAdapter(instructionsAdapter);
    }
}