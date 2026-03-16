package com.example.autorentnew.activities;



import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.autorent.R;
import com.example.autorentnew.adapters.AdminCarAdapter;
import com.example.autorentnew.database.DatabaseHelper;
import com.example.autorentnew.models.Car;

import java.util.List;

public class AdminCarsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Button btnBack;
    private DatabaseHelper dbHelper;
    private AdminCarAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_cars);

        dbHelper = new DatabaseHelper(this);

        recyclerView = findViewById(R.id.recyclerView);
        btnBack = findViewById(R.id.btnBack);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadCars();

        btnBack.setOnClickListener(v -> finish());
    }

    private void loadCars() {
        List<Car> carList = dbHelper.getAllCars();

        adapter = new AdminCarAdapter(this, carList, new AdminCarAdapter.OnCarActionListener() {
            @Override
            public void onEditClick(Car car) {
                Toast.makeText(AdminCarsActivity.this, "Редактирование: " + car.getBrand(), Toast.LENGTH_SHORT).show();
                // TODO: реализовать редактирование
            }

            @Override
            public void onDeleteClick(Car car) {
                boolean deleted = dbHelper.deleteCar(car.getId());
                if (deleted) {
                    Toast.makeText(AdminCarsActivity.this, "Автомобиль удален", Toast.LENGTH_SHORT).show();
                    loadCars();
                } else {
                    Toast.makeText(AdminCarsActivity.this, "Ошибка удаления", Toast.LENGTH_SHORT).show();
                }
            }
        });

        recyclerView.setAdapter(adapter);
    }
}