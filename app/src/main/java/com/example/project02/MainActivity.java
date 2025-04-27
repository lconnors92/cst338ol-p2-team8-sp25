package com.example.project02;

import static java.lang.String.*;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.project02.database.AppDatabase;
import com.example.project02.database.GameRepository;
import com.example.project02.database.entities.User;
import com.example.project02.databinding.ActivityMainBinding;
import com.example.project02.viewmodel.EditCharacterActivity;
import com.example.project02.viewmodel.ViewCharacterActivity;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private static final String MAIN_ACTIVITY_USER_ID = "MAIN_ACTIVITY_USER_ID";
    public static final String TAG = "APP_DATABASE";
    private ActivityMainBinding binding;
    private static final String SHARED_PREFERENCE_USERID_KEY = "SHARED_PREFERENCE_USERID_KEY";
    private static final String SAVED_INSTANCE_STATE_USERID_KEY = "SAVED_INSTANCE_STATE_USERID_KEY";
    private static final int LOGGED_OUT = -1;
    private int loggedInUserId = -1;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.logoutToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        loginUser(savedInstanceState);

        if (loggedInUserId == LOGGED_OUT) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        //Admin Checker
        AppDatabase database = AppDatabase.getDatabase(this);
        database.userDAO().getUserByUserId(loggedInUserId).observe(this, user -> {

            if (user != null) {
                this.user = user;
                invalidateOptionsMenu();

                // Show/hide admin button
                binding.adminButton.setVisibility(user.isAdmin() ? View.VISIBLE : View.GONE);
                binding.welcomeTextView.setText(format("Welcome, %s, to Forge & Fate! ", user.getUsername()));
                // Setup other buttons
                setupCharacterButtons();

                //Character owned display
                database.characterDAO().getCharacterCountByUser(loggedInUserId).observe(this, this::onChanged);
            } else {
                logout();
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
        binding.characterSelectionButton.setOnClickListener(v -> {
            // Launch the PublicCharacterListActivity to view public characters
            Intent intent = new Intent(MainActivity.this, PublicCharacterListActivity.class);
            startActivity(intent);
        });

        // Button to access user inventory
        binding.accessInventoryButton.setOnClickListener(v -> {
            // Launch the Inventory Activity
            Intent intent = new Intent(MainActivity.this, AccessInventoryActivity.class);
            startActivity(intent);
        });

        binding.editPasswordButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, com.example.project02.viewmodel.EditPasswordActivity.class);
            intent.putExtra("USER_ID", loggedInUserId);
            startActivity(intent);
        });

        binding.editCharacterButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, EditCharacterActivity.class);
            intent.putExtra("USER_ID", loggedInUserId);
            startActivity(intent);
        });
    }

    private void loginUser(Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCE_USERID_KEY, Context.MODE_PRIVATE);
        loggedInUserId = sharedPreferences.getInt(SHARED_PREFERENCE_USERID_KEY, LOGGED_OUT);

        if (loggedInUserId == LOGGED_OUT && savedInstanceState != null) {
            loggedInUserId = savedInstanceState.getInt(SAVED_INSTANCE_STATE_USERID_KEY, LOGGED_OUT);
        }
        if (loggedInUserId == LOGGED_OUT) {
            loggedInUserId = getIntent().getIntExtra(MAIN_ACTIVITY_USER_ID, LOGGED_OUT);
        } else {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(SHARED_PREFERENCE_USERID_KEY, loggedInUserId);
            editor.apply();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.logoutMenuItem);
        if (user != null) {
            item.setTitle(user.getUsername());
            item.setVisible(true);
        } else {
            item.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logoutMenuItem) {
            showLogoutDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showLogoutDialog() {
        new AlertDialog.Builder(this).setMessage("Would you like to logout?")
                .setPositiveButton("Logout", (dialog, which) -> logout())
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .create().show();
    }

    private void logout() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCE_USERID_KEY, Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();

        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SAVED_INSTANCE_STATE_USERID_KEY, loggedInUserId);
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCE_USERID_KEY,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPrefEditor = sharedPreferences.edit();
        sharedPrefEditor.putInt(MainActivity.SHARED_PREFERENCE_USERID_KEY, loggedInUserId);
        sharedPrefEditor.apply();

    }

    static Intent mainActivityIntentFactory(Context context, int userId) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(MAIN_ACTIVITY_USER_ID, userId);
        return intent;
    }

    private void onChanged(Integer count) {
        binding.characterCountTextView.setText(format("Characters Owned: %d", count));
    }
}
