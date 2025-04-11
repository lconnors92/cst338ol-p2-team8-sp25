package com.example.project02.database;

import android.app.Application;
import android.util.Log;

import com.example.project02.database.entities.Character;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class GameRepository {
    private final CharacterDAO characterDAO;
    private final UserDAO userDAO;
    private static GameRepository repository;

    private GameRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        this.characterDAO = db.characterDAO();
        this.userDAO = db.userDAO();
    }

    public static GameRepository getRepository(Application application) {
        if (repository != null) {
            return repository;
        }
        Future<GameRepository> future = AppDatabase.databaseWriteExecutor.submit(
                new Callable<GameRepository>() {
                    @Override
                    public GameRepository call() {
                        return new GameRepository(application);
                    }
                }
        );
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.e("CharacterRepository", "Error getting repository", e);
        }
        return null;
    }

    public ArrayList<Character> getPublicCharacters() {
        Future<ArrayList<Character>> future = AppDatabase.databaseWriteExecutor.submit(
                new Callable<ArrayList<Character>>() {
                    @Override
                    public ArrayList<Character> call() {
                        return (ArrayList<Character>) characterDAO.getPublicCharacters();
                    }
                }
        );
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.e("CharacterRepository", "Error fetching public characters", e);
        }
        return null;
    }
}
