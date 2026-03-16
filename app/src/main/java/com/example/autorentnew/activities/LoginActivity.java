package com.example.autorentnew.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.autorent.R;
import com.example.autorentnew.database.DatabaseHelper;
import com.example.autorentnew.models.User;
import com.example.autorentnew.utils.SessionManager;

public class LoginActivity extends AppCompatActivity {

    private EditText etLogin, etPassword;
    private Button btnLogin;
    private TextView tvBack;
    private DatabaseHelper dbHelper;
    private SessionManager sessionManager;
    private String role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        role = getIntent().getStringExtra("role");
        if (role == null) role = "user";

        dbHelper = new DatabaseHelper(this);
        sessionManager = new SessionManager(this);

        etLogin = findViewById(R.id.etLogin);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvBack = findViewById(R.id.tvBack);

        btnLogin.setOnClickListener(v -> {
            String login = etLogin.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (login.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Заполните все поля", Toast.LENGTH_SHORT).show();
                return;
            }

            Log.d("LOGIN", "Пытаемся войти: " + login + " / " + password + " как " + role);

            User user = dbHelper.authenticate(login, password);

            if (user != null) {
                Log.d("LOGIN", "Найден пользователь: " + user.getLogin() + ", роль: " + user.getRole());

                if (user.getRole().equals(role)) {
                    sessionManager.saveUser(user);
                    Toast.makeText(LoginActivity.this, "Добро пожаловать, " + user.getFirstName(), Toast.LENGTH_SHORT).show();
                    redirectToRoleScreen(user.getRole());
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Эта роль не подходит для данного пользователя", Toast.LENGTH_LONG).show();
                }
            } else {
                Log.d("LOGIN", "Пользователь не найден");
                Toast.makeText(LoginActivity.this, "Неверный логин или пароль", Toast.LENGTH_LONG).show();
            }
        });

        tvBack.setOnClickListener(v -> finish());
    }

    private void redirectToRoleScreen(String role) {
        Intent intent;
        if (role.equals("admin")) {
            intent = new Intent(LoginActivity.this, AdminMainActivity.class);
        } else if (role.equals("manager")) {
            intent = new Intent(LoginActivity.this, ManagerMainActivity.class);
        } else {
            intent = new Intent(LoginActivity.this, UserMainActivity.class);
        }
        startActivity(intent);
    }
}