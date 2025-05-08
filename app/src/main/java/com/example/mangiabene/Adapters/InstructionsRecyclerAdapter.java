package com.example.mangiabene.Adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mangiabene.R;

import java.util.ArrayList;

public class InstructionsRecyclerAdapter extends RecyclerView.Adapter<InstructionsRecyclerAdapter.ViewHolder> {
    private final ArrayList<String> instructionsList;

    public InstructionsRecyclerAdapter(ArrayList<String> instructionsList) {
        this.instructionsList = instructionsList;
    }

    @NonNull
    @Override
    public InstructionsRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_instruction, viewGroup, false);

        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull InstructionsRecyclerAdapter.ViewHolder viewHolder, int position) {
        String instruction = instructionsList.get(position);

        viewHolder.instructionStep.setText(String.valueOf(position + 1));
        viewHolder.instruction.setText(instruction.trim());
    }

    @Override
    public int getItemCount() {
        return instructionsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView instructionStep;
        TextView instruction;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            instructionStep = itemView.findViewById(R.id.instructionStep);
            instruction = itemView.findViewById(R.id.instruction);
        }
    }
}
