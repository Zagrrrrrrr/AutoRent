package com.example.autorentnew.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.autorent.R;
import com.example.autorentnew.utils.SessionManager;

public class MainActivity extends AppCompatActivity {

    private Button btnLogin, btnRegister;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.deleteDatabase("autorent.db"); // ВРЕМЕННО!
        sessionManager = new SessionManager(this);

        // Если уже есть сессия — сразу кидаем в нужный экран
        if (sessionManager.isLoggedIn()) {
            redirectToRoleScreen();
            return;
        }

        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

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
            default: // user
                intent = new Intent(MainActivity.this, UserMainActivity.class);
                break;
        }
        startActivity(intent);
        finish();
    }
}