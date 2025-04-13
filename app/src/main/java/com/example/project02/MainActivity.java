package com.example.project02;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.project02.database.entities.User;
import com.example.project02.database.repositories.UserRepository;
import com.example.project02.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private static final String MAIN_ACTIVITY_USER_ID = "MAIN_ACTIVITY_USER_ID";
    private static final String SHARED_PREFERENCE_USERID_KEY = "SHARED_PREFERENCE_USERID_KEY";
    private static final int LOGGED_OUT = -1;

    private ActivityMainBinding binding;
    private int loggedInUserId = -1;
    private User user;

    public static final String TAG = "TEAM8_P2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            return ;
        }
    }
