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
import com.example.autorentnew.models.Car;

public class AdminAddCarActivity extends AppCompatActivity {

    private EditText etBrand, etModel, etYear, etEngineVolume, etPriceHour, etPriceDay, etPriceWeek;
    private Spinner spinnerEngineType;
    private Button btnSave, btnBack;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_car);

        dbHelper = new DatabaseHelper(this);

        initViews();
        setupSpinner();
        setupListeners();
    }

    private void initViews() {
        etBrand = findViewById(R.id.etBrand);
        etModel = findViewById(R.id.etModel);
        etYear = findViewById(R.id.etYear);
        etEngineVolume = findViewById(R.id.etEngineVolume);
        etPriceHour = findViewById(R.id.etPriceHour);
        etPriceDay = findViewById(R.id.etPriceDay);
        etPriceWeek = findViewById(R.id.etPriceWeek);
        spinnerEngineType = findViewById(R.id.spinnerEngineType);
        btnSave = findViewById(R.id.btnSave);
        btnBack = findViewById(R.id.btnBack);
    }

    private void setupSpinner() {
        String[] types = {"Бензин", "Дизель", "Электро", "Гибрид"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, types);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEngineType.setAdapter(adapter);
    }

    private void setupListeners() {
        btnSave.setOnClickListener(v -> {
            if (validateFields()) {
                saveCar();
            }
        });

        btnBack.setOnClickListener(v -> finish());
    }

    private boolean validateFields() {
        if (etBrand.getText().toString().trim().isEmpty()) {
            etBrand.setError("Введите марку");
            return false;
        }
        if (etModel.getText().toString().trim().isEmpty()) {
            etModel.setError("Введите модель");
            return false;
        }
        if (etYear.getText().toString().trim().isEmpty()) {
            etYear.setError("Введите год");
            return false;
        }
        if (etEngineVolume.getText().toString().trim().isEmpty()) {
            etEngineVolume.setError("Введите объем");
            return false;
        }
        return true;
    }

    private void saveCar() {
        try {
            String brand = etBrand.getText().toString().trim();
            String model = etModel.getText().toString().trim();
            int year = Integer.parseInt(etYear.getText().toString().trim());
            double engineVolume = Double.parseDouble(etEngineVolume.getText().toString().trim());
            String engineType = spinnerEngineType.getSelectedItem().toString();

            double priceHour = 0;
            double priceDay = 0;
            double priceWeek = 0;

            if (!etPriceHour.getText().toString().trim().isEmpty()) {
                priceHour = Double.parseDouble(etPriceHour.getText().toString().trim());
            }
            if (!etPriceDay.getText().toString().trim().isEmpty()) {
                priceDay = Double.parseDouble(etPriceDay.getText().toString().trim());
            }
            if (!etPriceWeek.getText().toString().trim().isEmpty()) {
                priceWeek = Double.parseDouble(etPriceWeek.getText().toString().trim());
            }

            Car car = new Car(
                    0, brand, model, year, engineVolume, engineType,
                    priceHour, priceDay, priceWeek, true, ""
            );

            long result = dbHelper.addCar(car);

            if (result != -1) {
                Toast.makeText(this, "Автомобиль добавлен", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Ошибка при добавлении", Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Проверьте правильность ввода чисел", Toast.LENGTH_SHORT).show();
        }
    }
}