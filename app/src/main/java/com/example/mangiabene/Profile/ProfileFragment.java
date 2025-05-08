package com.example.mangiabene.Profile;

import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.mangiabene.Models.User;
import com.example.mangiabene.R;
import com.example.mangiabene.SharedViewModel;
import com.example.mangiabene.Utils.FastSharedPreferences;
import com.google.gson.reflect.TypeToken;

public class ProfileFragment extends Fragment {
    FrameLayout profileLayout;
    ImageView userPicture;
    EditText username;

    SharedViewModel sharedViewModel;

    User user;

    private final ActivityResultLauncher<Intent> imagePickerLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Uri imageUri = result.getData().getData();

                    if (imageUri != null) {
                        Log.d("ProfileFragment", "Selected Image URI: " + imageUri);

                        user.setPicture(imageUri.toString());
                        FastSharedPreferences.put(requireContext(), "user", user);

                        updateProfilePicture();
                    }
                }
            });

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        profileLayout = view.findViewById(R.id.profile_picture_frame);
        userPicture = view.findViewById(R.id.profile_picture);
        username = view.findViewById(R.id.user_name_input);

        user = FastSharedPreferences.get(requireContext(), "user", new TypeToken<User>() {
        }.getType(), new User());

        username.setText(user.getName());

        updateProfilePicture();

        profileLayout.setOnClickListener(v -> showImageOptionsDialog());

        username.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Do nothing
            }

            @Override
            public void onTextChanged(CharSequence input, int i, int i1, int i2) {
                user.setName(input.toString().isEmpty() ? "Guest" : input.toString());
            }

            @Override
            public void afterTextChanged(android.text.Editable editable) {
                // Do nothing
            }
        });
    }

    private void showImageOptionsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Profile Picture");

        String[] options = {"Select from Gallery", "Remove Picture"};
        builder.setItems(options, (dialog, which) -> {
            if (which == 0) {
                // Select from gallery
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                imagePickerLauncher.launch(intent);
            } else if (which == 1) {
                // Remove profile picture
                user.setPicture("");
                FastSharedPreferences.put(requireContext(), "user", user);
                updateProfilePicture();
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    private void updateProfilePicture() {
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

    @Override
    public void onDestroy() {
        FastSharedPreferences.put(requireContext(), "user", user);

        super.onDestroy();
    }
}
