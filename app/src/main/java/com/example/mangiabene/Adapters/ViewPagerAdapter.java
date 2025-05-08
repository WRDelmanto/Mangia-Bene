package com.example.mangiabene.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.mangiabene.Recipe.IngredientsFragment;
import com.example.mangiabene.Recipe.InstructionsFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 1) {
            return new IngredientsFragment();
        }

        return new InstructionsFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}

