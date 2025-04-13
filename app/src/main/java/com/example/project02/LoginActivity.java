package com.example.project02;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.project02.database.AppDatabase;
import com.example.project02.database.entities.User;
import com.example.project02.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = AppDatabase.getDatabase(this);

        binding.loginButton.setOnClickListener(v -> verifyUser());

        binding.signupButton.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, SignupActivity.class));
        });


    }

    private void verifyUser() {
        String username = binding.usernameEditText.getText().toString().trim();
        String password = binding.passwordEditText.getText().toString().trim();

        if (username.isEmpty()) {
            toastMaker("Username Cannot be blank");
            return;
        }

        LiveData<User> userObserver = database.userDAO().getUserByUserName(username);
        userObserver.observe(this,user -> {
            if (user != null) {
                if (password.equals(user.getPassword())) {
                    startActivity(MainActivity.mainActivityIntentFactory(getApplicationContext(),user.getId()));
                    finish();
                } else {
                    toastMaker("Invalid password");
                    binding.passwordEditText.setSelection(0);
                }
            } else {
                toastMaker(username + " is not a valid username");
                binding.usernameEditText.setSelection(0);
            }
        });
    }



    private void toastMaker(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    static Intent loginIntentFactory(Context context) {
        return new Intent(context, LoginActivity.class);
    }
}
