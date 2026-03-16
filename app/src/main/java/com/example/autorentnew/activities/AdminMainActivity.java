package com.example.autorentnew.activities;




import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.autorent.R;
import com.example.autorentnew.utils.SessionManager;

public class AdminMainActivity extends AppCompatActivity {

    private Button btnUserList, btnCarList, btnAddUser, btnAddCar, btnLogout;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        sessionManager = new SessionManager(this);

        btnUserList = findViewById(R.id.btnUserList);
        btnCarList = findViewById(R.id.btnCarList);
        btnAddUser = findViewById(R.id.btnAddUser);
        btnAddCar = findViewById(R.id.btnAddCar);
        btnLogout = findViewById(R.id.btnLogout);

        btnUserList.setOnClickListener(v ->
                startActivity(new Intent(AdminMainActivity.this, AdminUsersActivity.class))
        );

        btnCarList.setOnClickListener(v ->
                startActivity(new Intent(AdminMainActivity.this, AdminCarsActivity.class))
        );

        btnAddUser.setOnClickListener(v ->
                startActivity(new Intent(AdminMainActivity.this, AdminAddUserActivity.class))
        );

        btnAddCar.setOnClickListener(v ->
                startActivity(new Intent(AdminMainActivity.this, AdminAddCarActivity.class))
        );

        btnLogout.setOnClickListener(v -> {
            sessionManager.logout();
            Intent intent = new Intent(AdminMainActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }
}