package com.example.autorentnew.activities;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.autorentnew.database.DatabaseHelper;
import com.example.autorentnew.models.User;

public class AdminEditUserActivity extends AppCompatActivity {

    private EditText etLogin, etPassword, etFirstName, etLastName, etEmail, etPhone;
    private Spinner spinnerRole;
    private Button btnSave, btnDelete, btnBack;
    private DatabaseHelper dbHelper;
    private User user;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getResources().getIdentifier("activity_admin_edit_user", "layout", getPackageName()));

        dbHelper = new DatabaseHelper(this);

        userId = getIntent().getIntExtra("user_id", -1);
        if (userId == -1) {
            Toast.makeText(this, "Ошибка загрузки", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        user = dbHelper.getUserById(userId);
        if (user == null) {
            Toast.makeText(this, "Пользователь не найден", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        initViews();
        setupSpinner();
        fillData();
        setupListeners();
    }

    private void initViews() {
        etLogin = findViewById(getResources().getIdentifier("etLogin", "id", getPackageName()));
        etPassword = findViewById(getResources().getIdentifier("etPassword", "id", getPackageName()));
        etFirstName = findViewById(getResources().getIdentifier("etFirstName", "id", getPackageName()));
        etLastName = findViewById(getResources().getIdentifier("etLastName", "id", getPackageName()));
        etEmail = findViewById(getResources().getIdentifier("etEmail", "id", getPackageName()));
        etPhone = findViewById(getResources().getIdentifier("etPhone", "id", getPackageName()));
        spinnerRole = findViewById(getResources().getIdentifier("spinnerRole", "id", getPackageName()));
        btnSave = findViewById(getResources().getIdentifier("btnSave", "id", getPackageName()));
        btnDelete = findViewById(getResources().getIdentifier("btnDelete", "id", getPackageName()));
        btnBack = findViewById(getResources().getIdentifier("btnBack", "id", getPackageName()));
    }

    private void setupSpinner() {
        String[] roles = {"user", "manager", "admin"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, roles);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRole.setAdapter(adapter);
    }

    private void fillData() {
        etLogin.setText(user.getLogin());
        etPassword.setText(user.getPassword());
        etFirstName.setText(user.getFirstName());
        etLastName.setText(user.getLastName());
        etEmail.setText(user.getEmail());
        etPhone.setText(user.getPhone());

        String[] roles = {"user", "manager", "admin"};
        for (int i = 0; i < roles.length; i++) {
            if (roles[i].equals(user.getRole())) {
                spinnerRole.setSelection(i);
                break;
            }
        }
    }

    private void setupListeners() {
        btnSave.setOnClickListener(v -> saveUser());
        btnDelete.setOnClickListener(v -> deleteUser());
        btnBack.setOnClickListener(v -> finish());
    }

    private void saveUser() {
        if (!validateFields()) return;

        // Обновляем пользователя через сеттеры
        user.setLogin(etLogin.getText().toString().trim());
        user.setPassword(etPassword.getText().toString().trim());
        user.setFirstName(etFirstName.getText().toString().trim());
        user.setLastName(etLastName.getText().toString().trim());
        user.setEmail(etEmail.getText().toString().trim());
        user.setPhone(etPhone.getText().toString().trim());
        user.setRole(spinnerRole.getSelectedItem().toString());

        boolean updated = dbHelper.updateUser(user);

        if (updated) {
            Toast.makeText(this, "Пользователь обновлен", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Ошибка при обновлении", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteUser() {
        new AlertDialog.Builder(this)
                .setTitle("Удаление пользователя")
                .setMessage("Вы уверены, что хотите удалить " + user.getLogin() + "?")
                .setPositiveButton("Да", (dialog, which) -> {
                    boolean deleted = dbHelper.deleteUser(userId);
                    if (deleted) {
                        Toast.makeText(this, "Пользователь удален", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(this, "Ошибка при удалении", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Нет", null)
                .show();
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
}