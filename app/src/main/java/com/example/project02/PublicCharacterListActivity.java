package com.example.project02;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project02.characterViewHolders.CharacterAdapter;
import com.example.project02.database.AppDatabase;
import com.example.project02.database.entities.Character;
import com.example.project02.databinding.ActivityPublicCharacterListBinding;

public class PublicCharacterListActivity extends AppCompatActivity {
    private com.example.project02.characterViewHolders.CharacterAdapter characterAdapter;
    private RecyclerView recyclerView;
    private AppDatabase db;

    private Character selectedCharacter;
    private TextView selectedCharacterTextView;
    private LinearLayout characterDetailsSection;
    private Button claimButton;

    ActivityPublicCharacterListBinding binding;

    private boolean isAdmin = false; // NEW: store if user is admin

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_character_list);

        recyclerView = findViewById(R.id.recyclerViewCharacters);
        selectedCharacterTextView = findViewById(R.id.selectedCharacterTextView);
        characterDetailsSection = findViewById(R.id.characterDetailsSection);
        claimButton = findViewById(R.id.claimButton);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = AppDatabase.getDatabase(this);

        // NEW: Read if user is admin
        isAdmin = getIntent().getBooleanExtra("isAdmin", false);

        // Change button text if admin
        if (isAdmin) {
            claimButton.setText("Delete");
        } else {
            claimButton.setText("Claim");
        }

        db.characterDAO().getPublicCharacters().observe(this, characters -> {
            characterAdapter = new CharacterAdapter(characters, character -> {
                showCharacterDetails(character);
            });
            recyclerView.setAdapter(characterAdapter);
        });

        claimButton.setOnClickListener(v -> {
            if (selectedCharacter != null) {
                if (isAdmin) {
                    deleteCharacter(selectedCharacter);
                } else {
                    claimCharacter(selectedCharacter);
                }
            }
        });
    }

    private void showCharacterDetails(Character character) {
        selectedCharacter = character;

        selectedCharacterTextView.setText(character.toString());

        characterDetailsSection.setVisibility(View.VISIBLE);
        claimButton.setVisibility(View.VISIBLE);
    }

    private int getCurrentUserId() {
        SharedPreferences sharedPreferences = getSharedPreferences("SHARED_PREFERENCE_USERID_KEY", MODE_PRIVATE);
        return sharedPreferences.getInt("SHARED_PREFERENCE_USERID_KEY", -1);
    }

    private void claimCharacter(Character character) {
        character.setPublic(false);
        character.setUserId(getCurrentUserId());

        AppDatabase.databaseWriteExecutor.execute(() -> {
            db.characterDAO().update(character);
        });

        Toast.makeText(PublicCharacterListActivity.this, "Character claimed!", Toast.LENGTH_SHORT).show();
        finish(); // After claiming, exit the screen or refresh
    }

    private void deleteCharacter(Character character) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            db.characterDAO().delete(character);
        });

        Toast.makeText(PublicCharacterListActivity.this, "Character deleted!", Toast.LENGTH_SHORT).show();
        finish(); // After deleting, exit the screen or refresh
    }
}
