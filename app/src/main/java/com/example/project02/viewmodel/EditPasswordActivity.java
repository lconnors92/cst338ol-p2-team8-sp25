package com.example.project02.viewmodel;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project02.R;
import com.example.project02.database.AppDatabase;
import com.example.project02.database.UserDAO;

import java.util.concurrent.Executors;

public class EditPasswordActivity extends AppCompatActivity {

    private EditText currentPasswordInput, newPasswordInput, confirmPasswordInput;
    private Button updatePasswordButton;
    private UserDAO userDAO;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_password);

        currentPasswordInput = findViewById(R.id.currentPasswordInput);
        newPasswordInput = findViewById(R.id.newPasswordInput);
        confirmPasswordInput = findViewById(R.id.confirmPasswordInput);
        updatePasswordButton = findViewById(R.id.updatePasswordButton);

        userDAO = AppDatabase.getDatabase(this).userDAO();
        userId = getIntent().getIntExtra("USER_ID", -1);

        updatePasswordButton.setOnClickListener(v -> {
            String currentPass = currentPasswordInput.getText().toString().trim();
            String newPass = newPasswordInput.getText().toString().trim();
            String confirmPass = confirmPasswordInput.getText().toString().trim();

            if (currentPass.isEmpty() || newPass.isEmpty() || confirmPass.isEmpty()) {
                Toast.makeText(this, "All fields are required.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!newPass.equals(confirmPass)) {
                Toast.makeText(this, "New password and confirmation do not match.", Toast.LENGTH_SHORT).show();
                return;
            }

            Executors.newSingleThreadExecutor().execute(() -> {
                String storedPass = userDAO.getPasswordByUserId(userId);

                runOnUiThread(() -> {
                    if (!currentPass.equals(storedPass)) {
                        Toast.makeText(this, "Current password is incorrect", Toast.LENGTH_SHORT).show();
                    } else {
                        Executors.newSingleThreadExecutor().execute(() -> {
                            userDAO.updatePassword(userId, newPass);
                            runOnUiThread(() -> {
                                Toast.makeText(this, "Password updated successfully!", Toast.LENGTH_SHORT).show();
                                finish();
                            });
                        });
                    }
                });
            });
        });
    }
}
