package com.example.autorentnew.activities;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.autorent.R;

public class BookingSuccessActivity extends AppCompatActivity {

    private Button btnBack;
    private TextView tvCarName, tvPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_success);

        btnBack = findViewById(R.id.btnBack);
        tvCarName = findViewById(R.id.tvCarName);
        tvPrice = findViewById(R.id.tvPrice);

        // Получаем данные из Intent
        String carName = getIntent().getStringExtra("car_name");
        double price = getIntent().getDoubleExtra("total_price", 0);

        if (carName != null) {
            tvCarName.setText("Автомобиль: " + carName);
        }
        tvPrice.setText("Сумма: " + (int)price + "$");

        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(BookingSuccessActivity.this, UserMainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });
    }
}