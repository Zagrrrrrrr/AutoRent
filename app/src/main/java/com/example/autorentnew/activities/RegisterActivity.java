package com.example.autorentnew.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.autorent.R;
import com.example.autorentnew.database.DatabaseHelper;
import com.example.autorentnew.models.User;

public class RegisterActivity extends AppCompatActivity {

    private EditText etLogin, etPassword, etConfirmPassword, etFirstName, etLastName, etEmail, etPhone;
    private Button btnRegister, btnBack;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        dbHelper = new DatabaseHelper(this);

        initViews();
        setupListeners();
    }

    private void initViews() {
        etLogin = findViewById(R.id.etLogin);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        btnRegister = findViewById(R.id.btnRegister);
        btnBack = findViewById(R.id.btnBack);
    }

    private void setupListeners() {
        btnRegister.setOnClickListener(v -> {
            if (validateFields()) {
                registerUser();
            }
        });

        btnBack.setOnClickListener(v -> finish());
    }

    private boolean validateFields() {
        if (etLogin.getText().toString().trim().isEmpty()) {
            etLogin.setError("Введите логин");
            return false;
        }
        if (etPassword.getText().toString().trim().isEmpty()) {
            etPassword.setError("Введите пароль");
            return false;
        }
        if (etConfirmPassword.getText().toString().trim().isEmpty()) {
            etConfirmPassword.setError("Подтвердите пароль");
            return false;
        }
        if (!etPassword.getText().toString().equals(etConfirmPassword.getText().toString())) {
            Toast.makeText(this, "Пароли не совпадают", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (etFirstName.getText().toString().trim().isEmpty()) {
            etFirstName.setError("Введите имя");
            return false;
        }
        if (etLastName.getText().toString().trim().isEmpty()) {
            etLastName.setError("Введите фамилию");
            return false;
        }
        return true;
    }

    private void registerUser() {
        String login = etLogin.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String firstName = etFirstName.getText().toString().trim();
        String lastName = etLastName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();

        User user = new User(
                0, login, password, "user",
                firstName, lastName, "", "", "", email, phone
        );

        long result = dbHelper.addUser(user);

        if (result != -1) {
            Toast.makeText(this, "Регистрация успешна! Теперь войдите в систему.", Toast.LENGTH_LONG).show();
            finish();
        } else {
            Toast.makeText(this, "Ошибка регистрации. Возможно, логин уже занят.", Toast.LENGTH_SHORT).show();
        }
    }
}