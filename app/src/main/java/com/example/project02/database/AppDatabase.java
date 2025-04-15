package com.example.project02.database;
import com.example.project02.LoginActivity;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.project02.MainActivity;
import com.example.project02.database.entities.Character;
import com.example.project02.database.entities.Inventory;
import com.example.project02.database.entities.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {User.class, Character.class, Inventory.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "CharacterDatabase";
    public static final String USER_TABLE = "userTable";
    public static final String CHARACTER_TABLE = "characterTable";


    private static volatile AppDatabase INSTANCE;

    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME).fallbackToDestructiveMigration().addCallback(addDefaultValues).build();
                }
            }
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback addDefaultValues = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Log.i(MainActivity.TAG, "DATABASE CREATED!");
            databaseWriteExecutor.execute(() -> {
                UserDAO dao = INSTANCE.userDAO();
                dao.deleteAll();
                User admin = new User("admin1", "admin1");
                admin.setAdmin(true);
                dao.insert(admin);
                User testUser1 = new User("testuser1", "testuser1");
                dao.insert(testUser1);

                CharacterDAO cDao = INSTANCE.characterDAO();
                cDao.deleteAll();
                Character privateCharacter = new Character("privatename1", "privatespecies1", "privateclass1", 1, false, 1);
                cDao.insert(privateCharacter);
                Character publicCharacter = new Character("publicname1", "publicspecies1", "publicclass1", 2, true, 2 );
                cDao.insert(publicCharacter);

            });

        }
    };

    public abstract UserDAO userDAO();

    public abstract CharacterDAO characterDAO();

    public abstract InventoryDAO inventoryDAO();
}
