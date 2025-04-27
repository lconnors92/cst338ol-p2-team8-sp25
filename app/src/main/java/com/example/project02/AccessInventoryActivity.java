package com.example.project02;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project02.characterViewHolders.CharacterAdapter;
import com.example.project02.database.AppDatabase;
import com.example.project02.databinding.ActivityAccessInventoryBinding;
import com.example.project02.database.entities.Character;

public class AccessInventoryActivity extends AppCompatActivity {
    private com.example.project02.characterViewHolders.CharacterAdapter characterAdapter;
    private RecyclerView recyclerView;
    private AppDatabase db;
    private Character selectedCharacter;
    private TextView selectedCharacterTextView;
    private LinearLayout characterDetailsSection;
    private Button removeButton;

    ActivityAccessInventoryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_access_inventory);

        selectedCharacter = null;
        recyclerView = findViewById(R.id.recyclerViewCharacters);
        selectedCharacterTextView = findViewById(R.id.selectedCharacterTextView);
        characterDetailsSection = findViewById(R.id.characterDetailsSection);
        removeButton = findViewById(R.id.removeButton);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = AppDatabase.getDatabase(this);

        db.characterDAO().getRecordSetUserId(getCurrentUserId()).observe(this, characters -> {
            characterAdapter = new CharacterAdapter(characters, character -> {
                showCharacterDetails(character);
            });
            recyclerView.setAdapter(characterAdapter);
        });

        removeButton.setOnClickListener(v -> {
            if (selectedCharacter == null) {
                if (db.characterDAO().getCharacterCountByUser(getCurrentUserId()).toString().equals(String.valueOf(0))) {
                    Toast.makeText(AccessInventoryActivity.this, "No characters available for removal.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AccessInventoryActivity.this, "No character selected.", Toast.LENGTH_SHORT).show();
                }
            } else {
                db.characterDAO().delete(selectedCharacter);

                AppDatabase.databaseWriteExecutor.execute(() -> {
                    db.characterDAO().update(selectedCharacter);
                });
                Toast.makeText(AccessInventoryActivity.this, "Character removed!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showCharacterDetails(Character character) {
        selectedCharacter = character;

        selectedCharacterTextView.setText(character.toString());

        characterDetailsSection.setVisibility(View.VISIBLE);
        removeButton.setVisibility(View.VISIBLE);
    }

    private int getCurrentUserId() {
        SharedPreferences sharedPreferences = getSharedPreferences("SHARED_PREFERENCE_USERID_KEY", MODE_PRIVATE);
        return sharedPreferences.getInt("SHARED_PREFERENCE_USERID_KEY", -1);
    }
}