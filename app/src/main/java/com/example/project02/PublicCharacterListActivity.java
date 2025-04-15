package com.example.project02;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project02.characterViewHolders.CharacterAdapter;
import com.example.project02.characterViewHolders.CharacterViewModel;
import com.example.project02.database.CharacterDAO;
import com.example.project02.database.GameRepository;
import com.example.project02.database.entities.Character;
import com.example.project02.database.entities.User;
import com.example.project02.databinding.ActivityPublicCharacterListBinding;

import java.util.List;

public class PublicCharacterListActivity extends AppCompatActivity {

    private ActivityPublicCharacterListBinding binding;
    private GameRepository repository;

    private User user;
    private CharacterViewModel characterViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPublicCharacterListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        characterViewModel = new ViewModelProvider(this).get(CharacterViewModel.class);

        RecyclerView recyclerView = binding.logDisplayRecyclerView;
        final CharacterAdapter adapter = new CharacterAdapter(new CharacterAdapter.CharacterDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        repository = GameRepository.getRepository(getApplication());


        characterViewModel.getAllLogsById(user.getId()).observe(this, characters -> {
            //characters.submitList(characters);
        });

        //LiveData<Character> characterObserver = repository.getPublicCharacters();


    }
}
