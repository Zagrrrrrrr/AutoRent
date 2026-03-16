package com.example.autorentnew.activities;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.autorent.R;
import com.example.autorentnew.utils.SessionManager;

public class ProfileActivity extends AppCompatActivity {

    private TextView tvMyBookings, tvSettings, tvSupport;
    private Button btnBack, btnLogout;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        sessionManager = new SessionManager(this);

        tvMyBookings = findViewById(R.id.tvMyBookings);
        tvSettings = findViewById(R.id.tvSettings);
        tvSupport = findViewById(R.id.tvSupport);
        btnBack = findViewById(R.id.btnBack);
        btnLogout = findViewById(R.id.btnLogout);

        tvMyBookings.setOnClickListener(v ->
                startActivity(new Intent(ProfileActivity.this, MyBookingsActivity.class))
        );

        tvSettings.setOnClickListener(v ->
                Toast.makeText(this, "Настройки (в разработке)", Toast.LENGTH_SHORT).show()
        );

        tvSupport.setOnClickListener(v ->
                Toast.makeText(this, "Поддержка (в разработке)", Toast.LENGTH_SHORT).show()
        );

        btnBack.setOnClickListener(v -> finish());

        // КНОПКА ВЫХОДА - ВАЖНО!
        btnLogout.setOnClickListener(v -> {
            sessionManager.logout(); // Очищаем сессию
            Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }
}