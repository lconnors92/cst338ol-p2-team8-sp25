package com.example.project02;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project02.characterViewHolders.CharacterAdapter;
import com.example.project02.database.CharacterDAO;
import com.example.project02.database.GameRepository;
import com.example.project02.database.entities.Character;
import com.example.project02.databinding.ActivityPublicCharacterListBinding;

import java.util.List;

public class PublicCharacterListActivity extends AppCompatActivity {

    private ActivityPublicCharacterListBinding binding;
    private CharacterAdapter characterAdapter;
    private GameRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPublicCharacterListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = GameRepository.getRepository(getApplication());

        RecyclerView recyclerView = binding.logDisplayRecyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        characterAdapter = new CharacterAdapter(new CharacterAdapter.CharacterDiff());
        recyclerView.setAdapter(characterAdapter);

        System.out.println(repository.toString());
    }
}
