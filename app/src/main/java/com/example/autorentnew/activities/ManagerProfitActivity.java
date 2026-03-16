package com.example.autorentnew.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.autorent.R;
import com.example.autorentnew.database.DatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ManagerProfitActivity extends AppCompatActivity {

    private TextView tvTotalProfit, tvMonthProfit, tvPeriod;
    private Button btnBack;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_profit);

        dbHelper = new DatabaseHelper(this);

        tvTotalProfit = findViewById(R.id.tvTotalProfit);
        tvMonthProfit = findViewById(R.id.tvMonthProfit);
        tvPeriod = findViewById(R.id.tvPeriod);
        btnBack = findViewById(R.id.btnBack);

        loadProfit();

        btnBack.setOnClickListener(v -> finish());
    }

    private void loadProfit() {
        double totalProfit = dbHelper.getTotalProfit();
        tvTotalProfit.setText("Общая прибыль: " + (int)totalProfit + "$");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Calendar cal = Calendar.getInstance();
        String endDate = sdf.format(cal.getTime());
        cal.add(Calendar.MONTH, -1);
        String startDate = sdf.format(cal.getTime());

        double monthProfit = dbHelper.getProfitByPeriod(startDate, endDate);
        tvMonthProfit.setText("За последний месяц: " + (int)monthProfit + "$");
        tvPeriod.setText("Период: " + startDate + " - " + endDate);
    }
}