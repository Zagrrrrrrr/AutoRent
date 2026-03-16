package com.example.autorentnew.activities;



import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.autorent.R;
import com.example.autorentnew.database.DatabaseHelper;
import com.example.autorentnew.models.User;

public class AdminAddUserActivity extends AppCompatActivity {

    private EditText etLogin, etPassword, etFirstName, etLastName, etEmail, etPhone;
    private Spinner spinnerRole;
    private Button btnSave, btnBack;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_user);

        dbHelper = new DatabaseHelper(this);

        initViews();
        setupSpinner();
        setupListeners();
    }

    private void initViews() {
        etLogin = findViewById(R.id.etLogin);
        etPassword = findViewById(R.id.etPassword);
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        spinnerRole = findViewById(R.id.spinnerRole);
        btnSave = findViewById(R.id.btnSave);
        btnBack = findViewById(R.id.btnBack);
    }

    private void setupSpinner() {
        String[] roles = {"user", "manager", "admin"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, roles);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRole.setAdapter(adapter);
    }

    private void setupListeners() {
        btnSave.setOnClickListener(v -> {
            if (validateFields()) {
                saveUser();
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

    private void saveUser() {
        String login = etLogin.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String firstName = etFirstName.getText().toString().trim();
        String lastName = etLastName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String role = spinnerRole.getSelectedItem().toString();

        User user = new User(
                0, login, password, role,
                firstName, lastName, "", // middleName - пустая строка
                "", "", email, phone
        );

        long result = dbHelper.addUser(user);

        if (result != -1) {
            Toast.makeText(this, "Пользователь добавлен", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Ошибка при добавлении", Toast.LENGTH_SHORT).show();
        }
    }
}