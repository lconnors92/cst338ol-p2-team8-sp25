package com.example.project02;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project02.characterViewHolders.CharacterAdapter;
import com.example.project02.database.AppDatabase;
import com.example.project02.database.entities.Character;
import com.example.project02.databinding.ActivityPublicCharacterListBinding;

import java.util.List;

public class PublicCharacterListActivity extends AppCompatActivity {
    private com.example.project02.characterViewHolders.CharacterAdapter characterAdapter;
    private RecyclerView recyclerView;
    private AppDatabase db;

    ActivityPublicCharacterListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_character_list);

        recyclerView = findViewById(R.id.recyclerViewCharacters);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = AppDatabase.getDatabase(this);

        db.characterDAO().getPublicCharacters().observe(this, new Observer<List<Character>>() {
            @Override
            public void onChanged(List<Character> characters) {
                characterAdapter = new CharacterAdapter(characters);
                recyclerView.setAdapter(characterAdapter);
            }
        });
    }
}
