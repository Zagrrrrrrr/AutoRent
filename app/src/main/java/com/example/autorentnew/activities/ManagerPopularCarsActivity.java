package com.example.autorentnew.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.autorent.R;
import com.example.autorentnew.adapters.CarStatAdapter;
import com.example.autorentnew.database.DatabaseHelper;
import com.example.autorentnew.models.CarStat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ManagerPopularCarsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView tvPeriod;
    private Button btnBack;
    private DatabaseHelper dbHelper;
    private CarStatAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_popular_cars);

        dbHelper = new DatabaseHelper(this);

        recyclerView = findViewById(R.id.recyclerView);
        tvPeriod = findViewById(R.id.tvPeriod);
        btnBack = findViewById(R.id.btnBack);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadStats();

        btnBack.setOnClickListener(v -> finish());
    }

    private void loadStats() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Calendar cal = Calendar.getInstance();
        String endDate = sdf.format(cal.getTime());
        cal.add(Calendar.MONTH, -1);
        String startDate = sdf.format(cal.getTime());

        tvPeriod.setText("Период: " + startDate + " - " + endDate);

        List<CarStat> stats = dbHelper.getPopularCarsStats(startDate, endDate);

        adapter = new CarStatAdapter(this, stats);
        recyclerView.setAdapter(adapter);
    }
}