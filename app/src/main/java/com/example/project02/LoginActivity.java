package com.example.project02;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project02.database.entities.User;

public class LoginActivity extends AppCompatActivity {

    private static final String MAIN_ACTIVITY_USER_ID = "MAIN_ACTIVITY_USER_ID";
    private static final String SHARED_PREFERENCE_USERID_KEY = "SHARED_PREFERENCE_USERID_KEY";
    private static final int LOGGED_OUT = -1;


    private int loggedInUserId = -1;
    private User user;

    public static final String TAG = "TEAM8_P2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

            return ;
        }
    }
