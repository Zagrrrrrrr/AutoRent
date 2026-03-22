package com.example.autorentnew.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.autorentnew.adapters.CarStatAdapter;
import com.example.autorentnew.database.DatabaseHelper;
import com.example.autorentnew.models.CarStat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ManagerPeriodStatsActivity extends AppCompatActivity {

    private TextView tvStartDate, tvEndDate, tvTotalProfit, tvPeriodTitle;
    private Button btnSelectStart, btnSelectEnd, btnShowStats, btnBack;
    private Spinner spinnerStatsType;
    private RecyclerView recyclerView;

    private DatabaseHelper dbHelper;
    private Calendar startCalendar = Calendar.getInstance();
    private Calendar endCalendar = Calendar.getInstance();
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
    private SimpleDateFormat dbDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    private String currentStatsType = "popular";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Устанавливаем layout через getIdentifier
        setContentView(getResources().getIdentifier("activity_manager_period_stats", "layout", getPackageName()));

        dbHelper = new DatabaseHelper(this);

        initViews();
        setupSpinner();
        setupListeners();

        endCalendar.setTime(Calendar.getInstance().getTime());
        startCalendar.setTime(endCalendar.getTime());
        startCalendar.add(Calendar.MONTH, -1);
        updateDateDisplay();
    }

    private void initViews() {
        tvStartDate = findViewById(getResources().getIdentifier("tvStartDate", "id", getPackageName()));
        tvEndDate = findViewById(getResources().getIdentifier("tvEndDate", "id", getPackageName()));
        tvTotalProfit = findViewById(getResources().getIdentifier("tvTotalProfit", "id", getPackageName()));
        tvPeriodTitle = findViewById(getResources().getIdentifier("tvPeriodTitle", "id", getPackageName()));
        btnSelectStart = findViewById(getResources().getIdentifier("btnSelectStart", "id", getPackageName()));
        btnSelectEnd = findViewById(getResources().getIdentifier("btnSelectEnd", "id", getPackageName()));
        btnShowStats = findViewById(getResources().getIdentifier("btnShowStats", "id", getPackageName()));
        btnBack = findViewById(getResources().getIdentifier("btnBack", "id", getPackageName()));
        spinnerStatsType = findViewById(getResources().getIdentifier("spinnerStatsType", "id", getPackageName()));
        recyclerView = findViewById(getResources().getIdentifier("recyclerView", "id", getPackageName()));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setupSpinner() {
        String[] statsTypes = {"Популярные автомобили", "Прибыль за период"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, statsTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatsType.setAdapter(adapter);

        spinnerStatsType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    currentStatsType = "popular";
                    tvPeriodTitle.setText("Популярные автомобили");
                    tvTotalProfit.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                } else {
                    currentStatsType = "profit";
                    tvPeriodTitle.setText("Прибыль за период");
                    tvTotalProfit.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                currentStatsType = "popular";
            }
        });
    }

    private void setupListeners() {
        btnSelectStart.setOnClickListener(v -> showDatePickerDialog(true));
        btnSelectEnd.setOnClickListener(v -> showDatePickerDialog(false));
        btnShowStats.setOnClickListener(v -> loadStats());
        btnBack.setOnClickListener(v -> finish());
    }

    private void showDatePickerDialog(boolean isStartDate) {
        Calendar calendar = isStartDate ? startCalendar : endCalendar;

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    calendar.set(year, month, dayOfMonth);

                    if (endCalendar.before(startCalendar)) {
                        endCalendar.setTime(startCalendar.getTime());
                        Toast.makeText(this, "Дата окончания не может быть раньше даты начала", Toast.LENGTH_SHORT).show();
                    }

                    updateDateDisplay();
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        datePickerDialog.show();
    }

    private void updateDateDisplay() {
        tvStartDate.setText(dateFormat.format(startCalendar.getTime()));
        tvEndDate.setText(dateFormat.format(endCalendar.getTime()));
    }

    private void loadStats() {
        String startDate = dbDateFormat.format(startCalendar.getTime());
        String endDate = dbDateFormat.format(endCalendar.getTime());

        if (currentStatsType.equals("popular")) {
            loadPopularCarsStats(startDate, endDate);
        } else {
            loadProfitStats(startDate, endDate);
        }
    }

    private void loadPopularCarsStats(String startDate, String endDate) {
        List<CarStat> stats = dbHelper.getPopularCarsStats(startDate, endDate);

        if (stats.isEmpty()) {
            Toast.makeText(this, "Нет данных за выбранный период", Toast.LENGTH_SHORT).show();
        }

        CarStatAdapter adapter = new CarStatAdapter(this, stats);
        recyclerView.setAdapter(adapter);
        tvTotalProfit.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    private void loadProfitStats(String startDate, String endDate) {
        double profit = dbHelper.getProfitByPeriod(startDate, endDate);
        tvTotalProfit.setText(String.format("Прибыль за период: %.2f$", profit));
        tvTotalProfit.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }
}