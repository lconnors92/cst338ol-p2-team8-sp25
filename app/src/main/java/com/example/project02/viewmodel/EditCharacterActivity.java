package com.example.project02.viewmodel;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;
import com.example.project02.characterViewHolders.CharacterViewModel;
import com.example.project02.database.entities.Character;
import com.example.project02.characterViewHolders.CharacterAdapter;
import com.example.project02.database.AppDatabase;
import com.example.project02.databinding.ActivityEditCharacterBinding;
import com.example.project02.R;

public class EditCharacterActivity extends AppCompatActivity {
    private EditText setCharacterName, setCharacterSpecies, setCharacterClass, setCharacterAge;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch publicToggle;
    private CharacterAdapter characterAdapter;
    private RecyclerView recyclerView;
    private AppDatabase db;
    private Character selectedCharacter;
    private Button saveButton;
    private CharacterViewModel characterViewModel;
    private int index;

    ActivityEditCharacterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_character);

        selectedCharacter = null;
        recyclerView = findViewById(R.id.recyclerViewCharacters);
        setCharacterName = findViewById(R.id.setCharacterName);
        setCharacterSpecies = findViewById(R.id.setCharacterSpecies);
        setCharacterClass = findViewById(R.id.setCharacterClass);
        setCharacterAge = findViewById(R.id.setCharacterAge);
        publicToggle = findViewById(R.id.publicToggle);
        saveButton = findViewById(R.id.saveChanges);

        characterViewModel = new ViewModelProvider(this).get(CharacterViewModel.class);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db = AppDatabase.getDatabase(this);

        db.characterDAO().getRecordSetUserId(getCurrentUserId()).observe(this, characters -> {
            characterAdapter = new CharacterAdapter(characters, character -> {
                populateCharacterDetails(character);
            });
            recyclerView.setAdapter(characterAdapter);
        });

        if (db.characterDAO().getCharacterCountByUser(getCurrentUserId()).toString().equals(String.valueOf(0))) {
            Toast.makeText(EditCharacterActivity.this, "No characters available for editing.", Toast.LENGTH_SHORT).show();
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCharacter(index);
            }
        });
    }

    private void saveCharacter(int idx) {
        String name = setCharacterName.getText().toString();
        String species = setCharacterSpecies.getText().toString();
        String characterClass = setCharacterClass.getText().toString();
        int age = Integer.parseInt(setCharacterAge.getText().toString());
        boolean isPublic = publicToggle.isChecked();

        if (name.isEmpty() || species.isEmpty() || characterClass.isEmpty() || age < 0) {
            Toast.makeText(this, "Please enter valid data for all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Character newCharacter = new Character(name, species, characterClass, age, isPublic, getCurrentUserId());
        newCharacter.setId(idx);

        if (isPublic) {
            newCharacter.setUserId(0);
        }

        characterViewModel.update(newCharacter);
        Toast.makeText(this, "Character Edited!", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void populateCharacterDetails(Character character) {
        selectedCharacter = character;
        index = character.getId();
        setCharacterName.setText(character.getName());
        setCharacterSpecies.setText(character.getSpecies());
        setCharacterClass.setText(character.getCharacterClass());
        setCharacterAge.setText(String.valueOf(character.getAge()));
        saveButton.setVisibility(View.VISIBLE);
    }

    private int getCurrentUserId() {
        SharedPreferences sharedPreferences = getSharedPreferences("SHARED_PREFERENCE_USERID_KEY", MODE_PRIVATE);
        return sharedPreferences.getInt("SHARED_PREFERENCE_USERID_KEY", -1);
    }
}