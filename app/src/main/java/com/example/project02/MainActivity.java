package com.example.project02;

import static java.lang.String.*;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.project02.database.AppDatabase;
import com.example.project02.database.GameRepository;
import com.example.project02.database.entities.User;
import com.example.project02.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private static final String MAIN_ACTIVITY_USER_ID = "MAIN_ACTIVITY_USER_ID";
    public static final String TAG = "APP_DATABASE";
    private ActivityMainBinding binding;


    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //Admin Checker
        AppDatabase database = AppDatabase.getDatabase(this);
        int userId = getIntent().getIntExtra(MAIN_ACTIVITY_USER_ID, -1);
        database.userDAO().getUserByUserId(userId).observe(this, user -> {
            if (user != null) {
                // Show/hide admin button
                binding.adminButton.setVisibility(user.isAdmin() ? View.VISIBLE : View.GONE);
                binding.welcomeTextView.setText(format("Welcome, %s, to Forge & Fate! ", user.getUsername()));
                // Setup other buttons
                setupCharacterButtons();

                //Character owned display
                database.characterDAO().getCharacterCountByUser(userId).observe(this, count -> {
                    binding.characterCountTextView.setText(String.format("Characters Owned: %d", count));
                });
            }
        });

    }
    private void setupCharacterButtons() {
        // Button to create a character
        binding.createCharacterButton.setOnClickListener(v -> {
            // Launch create character activity
            Intent intent = new Intent(MainActivity.this, CharacterCreationActivity.class);
            startActivity(intent);
        });

        // Button to view the character selection screen (list of public characters)
        // Missing MainActivity methods to fully implement, aiming for 4/16
        binding.characterSelectionButton.setOnClickListener(v -> {
            // Launch the PublicCharacterListActivity to view public characters
            Intent intent = new Intent(MainActivity.this, PublicCharacterListActivity.class);
            startActivity(intent);
        });

        // Button to access user inventory
        binding.accessInventoryButton.setOnClickListener(v -> {
            // Launch the Inventory Activity
            // Intent intent = new Intent(MainActivity.this, InventoryActivity.class);
            // startActivity(intent);
        });
    }



    static Intent mainActivityIntentFactory(Context context, int userId) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(MAIN_ACTIVITY_USER_ID, userId);
        return intent;
    }
}
