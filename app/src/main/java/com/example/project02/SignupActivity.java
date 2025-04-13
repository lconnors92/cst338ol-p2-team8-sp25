package com.example.project02;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.project02.database.AppDatabase;
import com.example.project02.database.entities.User;
import com.example.project02.databinding.ActivitySignupBinding;
import com.example.project02.viewmodel.UserViewModel;

public class SignupActivity extends AppCompatActivity {

    private ActivitySignupBinding binding;
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        binding.signupButton.setOnClickListener(v -> attemptSignup());
        }

        private void attemptSignup() {
        String username = binding.usernameEditText.getText().toString().trim();
        String password = binding.passwordEditText.getText().toString().trim();
        String confirmPassword = binding.confirmPasswordEditText.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
            return;
            }
        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords don't match", Toast.LENGTH_SHORT).show();
        }
        userViewModel.getUserByUsername(username).observe(this, existingUser -> {
            if (existingUser != null) {
                Toast.makeText(this, "Username already taken", Toast.LENGTH_SHORT).show();
            } else {
                User newUser = new User(username, password);
                userViewModel.insertUser(newUser);
                Toast.makeText(this, "Account Created!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        }
        static Intent signupIntentFactory(Context context) {
        return new Intent(context, SignupActivity.class);
        }
    }
