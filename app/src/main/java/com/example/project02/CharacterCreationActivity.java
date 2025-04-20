package com.example.project02;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.project02.characterViewHolders.CharacterViewModel;
import com.example.project02.database.CharacterDAO;
import com.example.project02.database.UserDAO;
import com.example.project02.database.entities.Character;
import com.example.project02.database.entities.User;

public class CharacterCreationActivity extends AppCompatActivity {

    private EditText setCharacterName, setCharacterSpecies, setCharacterClass, setCharacterAge;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch publicToggle;
    private Button saveCharacter;

    public static int userID;

    private CharacterViewModel characterViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_character);

        setCharacterName = findViewById(R.id.setCharacterName);
        setCharacterSpecies = findViewById(R.id.setCharacterSpecies);
        setCharacterClass = findViewById(R.id.setCharacterClass);
        setCharacterAge = findViewById(R.id.setCharacterAge);
        publicToggle = findViewById(R.id.publicToggle);
        saveCharacter = findViewById(R.id.saveCharacter);

        characterViewModel = new ViewModelProvider(this).get(CharacterViewModel.class);

        saveCharacter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCharacter();
            }
        });
    }

    private void saveCharacter() {
        String name = setCharacterName.getText().toString();
        String species = setCharacterSpecies.getText().toString();
        String characterClass = setCharacterClass.getText().toString();
        int age = Integer.parseInt(setCharacterAge.getText().toString());
        boolean isPublic = publicToggle.isChecked();

        if (name.isEmpty() || species.isEmpty() || characterClass.isEmpty()) {
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Character newCharacter = new Character(name, species, characterClass, age, isPublic, userID);

        characterViewModel.insert(newCharacter);

        Toast.makeText(this, "Character Created!", Toast.LENGTH_SHORT).show();

        finish();
    }
}
