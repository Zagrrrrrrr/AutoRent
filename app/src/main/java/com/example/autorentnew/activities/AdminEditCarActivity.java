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
import com.example.autorentnew.models.Car;

public class AdminEditCarActivity extends AppCompatActivity {

    private EditText etBrand, etModel, etYear, etEngineVolume, etPriceHour, etPriceDay, etPriceWeek, etImageUrl;
    private Spinner spinnerEngineType;
    private Button btnSave, btnDelete, btnBack;
    private DatabaseHelper dbHelper;
    private Car car;
    private int carId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Устанавливаем layout через идентификатор
        setContentView(getResources().getIdentifier("activity_admin_edit_car", "layout", getPackageName()));

        dbHelper = new DatabaseHelper(this);

        carId = getIntent().getIntExtra("car_id", -1);
        if (carId == -1) {
            Toast.makeText(this, "Ошибка загрузки", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        car = dbHelper.getCarById(carId);
        if (car == null) {
            Toast.makeText(this, "Автомобиль не найден", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        initViews();
        setupSpinner();
        fillData();
        setupListeners();
    }

    private void initViews() {
        etBrand = findViewById(getResources().getIdentifier("etBrand", "id", getPackageName()));
        etModel = findViewById(getResources().getIdentifier("etModel", "id", getPackageName()));
        etYear = findViewById(getResources().getIdentifier("etYear", "id", getPackageName()));
        etEngineVolume = findViewById(getResources().getIdentifier("etEngineVolume", "id", getPackageName()));
        etPriceHour = findViewById(getResources().getIdentifier("etPriceHour", "id", getPackageName()));
        etPriceDay = findViewById(getResources().getIdentifier("etPriceDay", "id", getPackageName()));
        etPriceWeek = findViewById(getResources().getIdentifier("etPriceWeek", "id", getPackageName()));
        etImageUrl = findViewById(getResources().getIdentifier("etImageUrl", "id", getPackageName()));
        spinnerEngineType = findViewById(getResources().getIdentifier("spinnerEngineType", "id", getPackageName()));
        btnSave = findViewById(getResources().getIdentifier("btnSave", "id", getPackageName()));
        btnDelete = findViewById(getResources().getIdentifier("btnDelete", "id", getPackageName()));
        btnBack = findViewById(getResources().getIdentifier("btnBack", "id", getPackageName()));
    }

    private void setupSpinner() {
        String[] types = {"Бензин", "Дизель", "Электро", "Гибрид"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, types);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEngineType.setAdapter(adapter);
    }

    private void fillData() {
        etBrand.setText(car.getBrand());
        etModel.setText(car.getModel());
        etYear.setText(String.valueOf(car.getYear()));
        etEngineVolume.setText(String.valueOf(car.getEngineVolume()));

        String[] types = {"Бензин", "Дизель", "Электро", "Гибрид"};
        for (int i = 0; i < types.length; i++) {
            if (types[i].equals(car.getEngineType())) {
                spinnerEngineType.setSelection(i);
                break;
            }
        }

        etPriceHour.setText(String.valueOf((int)car.getPricePerHour()));
        etPriceDay.setText(String.valueOf((int)car.getPricePerDay()));
        etPriceWeek.setText(String.valueOf((int)car.getPricePerWeek()));
        etImageUrl.setText(car.getImageUrl());
    }

    private void setupListeners() {
        btnSave.setOnClickListener(v -> saveCar());
        btnDelete.setOnClickListener(v -> deleteCar());
        btnBack.setOnClickListener(v -> finish());
    }

    private void saveCar() {
        if (!validateFields()) return;

        try {
            car.setBrand(etBrand.getText().toString().trim());
            car.setModel(etModel.getText().toString().trim());
            car.setYear(Integer.parseInt(etYear.getText().toString().trim()));
            car.setEngineVolume(Double.parseDouble(etEngineVolume.getText().toString().trim()));
            car.setEngineType(spinnerEngineType.getSelectedItem().toString());

            double priceHour = etPriceHour.getText().toString().trim().isEmpty() ? 0 :
                    Double.parseDouble(etPriceHour.getText().toString().trim());
            double priceDay = etPriceDay.getText().toString().trim().isEmpty() ? 0 :
                    Double.parseDouble(etPriceDay.getText().toString().trim());
            double priceWeek = etPriceWeek.getText().toString().trim().isEmpty() ? 0 :
                    Double.parseDouble(etPriceWeek.getText().toString().trim());

            car.setPricePerHour(priceHour);
            car.setPricePerDay(priceDay);
            car.setPricePerWeek(priceWeek);
            car.setImageUrl(etImageUrl.getText().toString().trim());

            boolean updated = dbHelper.updateCar(car);

            if (updated) {
                Toast.makeText(this, "Автомобиль обновлен", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Ошибка при обновлении", Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Проверьте правильность ввода чисел", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteCar() {
        new AlertDialog.Builder(this)
                .setTitle("Удаление автомобиля")
                .setMessage("Вы уверены, что хотите удалить " + car.getBrand() + " " + car.getModel() + "?")
                .setPositiveButton("Да", (dialog, which) -> {
                    boolean deleted = dbHelper.deleteCar(carId);
                    if (deleted) {
                        Toast.makeText(this, "Автомобиль удален", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(this, "Ошибка при удалении", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Нет", null)
                .show();
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
}