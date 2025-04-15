package com.example.project02.characterViewHolders;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.project02.database.GameRepository;
import com.example.project02.database.entities.Character;

import java.util.List;

public class CharacterViewModel extends AndroidViewModel {
    private final GameRepository repository;

    public CharacterViewModel(Application application) {
        super(application);
        repository = GameRepository.getRepository(application);
    }

    public LiveData<List<Character>> getAllLogsById(int userId) {
        return repository.getAllLogsByUserIdLiveData(userId);
    }

    public void insert(Character character) {
        repository.insertCharacter(character);
    }
}
