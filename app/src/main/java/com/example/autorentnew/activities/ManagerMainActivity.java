package com.example.autorentnew.activities;



import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.autorent.R;
import com.example.autorentnew.utils.SessionManager;

public class ManagerMainActivity extends AppCompatActivity {

    private Button btnPopularCars, btnProfit, btnCalendar, btnLogout;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_main);

        sessionManager = new SessionManager(this);

        btnPopularCars = findViewById(R.id.btnPopularCars);
        btnProfit = findViewById(R.id.btnProfit);
        btnCalendar = findViewById(R.id.btnCalendar);
        btnLogout = findViewById(R.id.btnLogout);

        btnPopularCars.setOnClickListener(v ->
                startActivity(new Intent(ManagerMainActivity.this, ManagerPopularCarsActivity.class))
        );

        btnProfit.setOnClickListener(v ->
                startActivity(new Intent(ManagerMainActivity.this, ManagerProfitActivity.class))
        );

        // В методе onCreate найди кнопку btnCalendar и замени:

        btnCalendar.setOnClickListener(v -> {
            Intent intent = new Intent(ManagerMainActivity.this, ManagerPeriodStatsActivity.class);
            startActivity(intent);
        });

        btnLogout.setOnClickListener(v -> {
            sessionManager.logout();
            Intent intent = new Intent(ManagerMainActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }
}