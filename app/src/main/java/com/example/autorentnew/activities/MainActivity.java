package com.example.autorentnew.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.autorent.R;
import com.example.autorentnew.utils.SessionManager;

public class MainActivity extends AppCompatActivity {

    private Button btnUserLogin, btnAdminLogin, btnManagerLogin, btnUserRegister;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sessionManager = new SessionManager(this);

        if (sessionManager.isLoggedIn()) {
            redirectToRoleScreen();
            return;
        }

        btnUserLogin = findViewById(R.id.btnUserLogin);
        btnAdminLogin = findViewById(R.id.btnAdminLogin);
        btnManagerLogin = findViewById(R.id.btnManagerLogin);
        btnUserRegister = findViewById(R.id.btnUserRegister);

        btnUserLogin.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            intent.putExtra("role", "user");
            startActivity(intent);
        });

        btnAdminLogin.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            intent.putExtra("role", "admin");
            startActivity(intent);
        });

        btnManagerLogin.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            intent.putExtra("role", "manager");
            startActivity(intent);
        });

        btnUserRegister.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    private void redirectToRoleScreen() {
        String role = sessionManager.getUserRole();
        Intent intent;

        if (role.equals("admin")) {
            intent = new Intent(MainActivity.this, AdminMainActivity.class);
        } else if (role.equals("manager")) {
            intent = new Intent(MainActivity.this, ManagerMainActivity.class);
        } else {
            intent = new Intent(MainActivity.this, UserMainActivity.class);
        }

        startActivity(intent);
        finish();
    }
}