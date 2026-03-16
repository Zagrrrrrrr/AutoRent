package com.example.autorentnew.activities;



import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.autorent.R;
import com.example.autorentnew.adapters.CarAdapter;
import com.example.autorentnew.database.DatabaseHelper;
import com.example.autorentnew.models.Booking;
import com.example.autorentnew.models.Car;
import com.example.autorentnew.utils.SessionManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class UserMainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DatabaseHelper dbHelper;
    private SessionManager sessionManager;
    private TextView tvProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);

        dbHelper = new DatabaseHelper(this);
        sessionManager = new SessionManager(this);

        recyclerView = findViewById(R.id.recyclerView);
        tvProfile = findViewById(R.id.tvProfile);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadCars();

        tvProfile.setOnClickListener(v -> {
            Intent intent = new Intent(UserMainActivity.this, ProfileActivity.class);
            startActivity(intent);
        });
    }

    private void loadCars() {
        List<Car> carList = dbHelper.getAllCars();

        CarAdapter adapter = new CarAdapter(this, carList, car -> {
            createBooking(car);
        });

        recyclerView.setAdapter(adapter);
    }

    private void createBooking(Car car) {
        try {
            int userId = sessionManager.getUserId();

            if (userId == -1) {
                Toast.makeText(this, "Ошибка: пользователь не найден", Toast.LENGTH_SHORT).show();
                return;
            }

            // Бронируем на 3 дня
            int days = 3;
            double totalPrice = car.getPricePerDay() * days;

            // Форматируем даты
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String startDate = sdf.format(new Date());
            String endDate = sdf.format(new Date(new Date().getTime() + days * 86400000L));

            // Создаем бронирование
            Booking booking = new Booking(
                    0,
                    car.getId(),
                    userId,
                    startDate,
                    endDate,
                    totalPrice,
                    "active",
                    ""
            );

            long result = dbHelper.addBooking(booking);

            if (result != -1) {
                Toast.makeText(this, "Автомобиль забронирован!", Toast.LENGTH_SHORT).show();
                // Переходим на экран успеха
                Intent intent = new Intent(UserMainActivity.this, BookingSuccessActivity.class);
                intent.putExtra("car_name", car.getBrand() + " " + car.getModel());
                intent.putExtra("total_price", totalPrice);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Ошибка при бронировании", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Ошибка: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}