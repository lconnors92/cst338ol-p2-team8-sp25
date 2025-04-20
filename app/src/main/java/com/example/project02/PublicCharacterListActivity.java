package com.example.project02;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project02.characterViewHolders.CharacterAdapter;
import com.example.project02.database.AppDatabase;
import com.example.project02.database.entities.Character;
import com.example.project02.database.entities.User;
import com.example.project02.databinding.ActivityPublicCharacterListBinding;

import java.util.List;

/**
 * all functionality T0D0's d0ne
 * need cleanup
 */

public class PublicCharacterListActivity extends AppCompatActivity {
    private com.example.project02.characterViewHolders.CharacterAdapter characterAdapter;
    private RecyclerView recyclerView;
    private AppDatabase db;

    private Character selectedCharacter;

    private TextView selectedCharacterTextView;
    private LinearLayout characterDetailsSection;
    private Button claimButton;

    ActivityPublicCharacterListBinding binding;

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

        db.characterDAO().getPublicCharacters().observe(this, characters -> {

                characterAdapter = new CharacterAdapter(characters, character -> {
                    showCharacterDetails(character);
                });
                recyclerView.setAdapter(characterAdapter);
        });

        claimButton.setOnClickListener(v -> {
            if (selectedCharacter != null && selectedCharacter.isPublic()) {
                selectedCharacter.setPublic(false);
                selectedCharacter.setUserId(getCurrentUserId());
                addToInventory(selectedCharacter);


                AppDatabase.databaseWriteExecutor.execute(() -> {
                    db.characterDAO().update(selectedCharacter);
                });

                Toast.makeText(PublicCharacterListActivity.this, "Character claimed!", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void showCharacterDetails(Character character) {
        selectedCharacter = character;

        selectedCharacterTextView.setText(character.toString());

        characterDetailsSection.setVisibility(View.VISIBLE);
        claimButton.setVisibility(View.VISIBLE);
    }

    User user;
    private int getCurrentUserId() {
        SharedPreferences sharedPreferences = getSharedPreferences("SHARED_PREFERENCE_USERID_KEY", MODE_PRIVATE);
        return sharedPreferences.getInt("SHARED_PREFERENCE_USERID_KEY", -1);
    }

    private void addToInventory(Character character) {
        character.setUserId(getCurrentUserId());

        AppDatabase.databaseWriteExecutor.execute(() -> {
            db.characterDAO().update(character);
        });
    }

}
