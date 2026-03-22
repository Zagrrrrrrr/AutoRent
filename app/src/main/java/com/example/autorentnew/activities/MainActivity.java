package com.example.autorentnew.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.autorentnew.utils.SessionManager;

public class MainActivity extends AppCompatActivity {

    private Button btnLogin, btnRegister;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getResources().getIdentifier("activity_main", "layout", getPackageName()));

        sessionManager = new SessionManager(this);

        if (sessionManager.isLoggedIn()) {
            redirectToRoleScreen();
            return;
        }

        btnLogin = findViewById(getResources().getIdentifier("btnLogin", "id", getPackageName()));
        btnRegister = findViewById(getResources().getIdentifier("btnRegister", "id", getPackageName()));

        btnLogin.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        btnRegister.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    private void redirectToRoleScreen() {
        String role = sessionManager.getUserRole();
        Intent intent;

        switch (role) {
            case "admin":
                intent = new Intent(MainActivity.this, AdminMainActivity.class);
                break;
            case "manager":
                intent = new Intent(MainActivity.this, ManagerMainActivity.class);
                break;
            default:
                intent = new Intent(MainActivity.this, UserMainActivity.class);
                break;
        }
        startActivity(intent);
        finish();
    }
}