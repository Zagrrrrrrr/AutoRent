package com.example.autorentnew.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.autorent.R;
import com.example.autorentnew.database.DatabaseHelper;
import com.example.autorentnew.models.Booking;
import com.example.autorentnew.models.Car;
import com.example.autorentnew.utils.SessionManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CarDetailActivity extends AppCompatActivity {

    private ImageView ivCarImage;
    private TextView tvCarName, tvYear, tvEngine, tvPricePerHour, tvPricePerDay, tvPricePerWeek, tvDescription;
    private Button btnBook, btnBack;

    private DatabaseHelper dbHelper;
    private SessionManager sessionManager;
    private Car car;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_detail);

        dbHelper = new DatabaseHelper(this);
        sessionManager = new SessionManager(this);

        int carId = getIntent().getIntExtra("car_id", -1);
        if (carId == -1) {
            Toast.makeText(this, "Ошибка загрузки данных", Toast.LENGTH_SHORT).show();
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
        displayCarInfo();
        setupListeners();
    }

    private void initViews() {
        ivCarImage = findViewById(R.id.ivCarDetailImage);
        tvCarName = findViewById(R.id.tvCarName);
        tvYear = findViewById(R.id.tvYear);
        tvEngine = findViewById(R.id.tvEngine);
        tvPricePerHour = findViewById(R.id.tvPricePerHour);
        tvPricePerDay = findViewById(R.id.tvPricePerDay);
        tvPricePerWeek = findViewById(R.id.tvPricePerWeek);
        tvDescription = findViewById(R.id.tvDescription);
        btnBook = findViewById(R.id.btnBook);
        btnBack = findViewById(R.id.btnBack);
    }

    private void displayCarInfo() {
        tvCarName.setText(car.getBrand() + " " + car.getModel());
        tvYear.setText("Год выпуска: " + car.getYear());
        tvEngine.setText(String.format("Двигатель: %.1f л. %s", car.getEngineVolume(), car.getEngineType()));
        tvPricePerHour.setText((int)car.getPricePerHour() + "$/час");
        tvPricePerDay.setText((int)car.getPricePerDay() + "$/сутки");
        tvPricePerWeek.setText((int)car.getPricePerWeek() + "$/неделя");

        // Загружаем картинку
        String imageName = car.getImageUrl();
        if (imageName != null && !imageName.isEmpty()) {
            int resId = getResources().getIdentifier(imageName, "drawable", getPackageName());
            if (resId != 0) {
                ivCarImage.setImageResource(resId);
            } else {
                ivCarImage.setImageResource(R.drawable.ic_car_placeholder);
            }
        } else {
            ivCarImage.setImageResource(R.drawable.ic_car_placeholder);
        }

        tvDescription.setText("Автомобиль в отличном состоянии. Полная комплектация. " +
                "Климат-контроль, кожаный салон, парктроники, камера заднего вида.");
    }

    private void setupListeners() {
        btnBook.setOnClickListener(v -> {
            if (!sessionManager.isLoggedIn()) {
                Toast.makeText(this, "Необходимо войти в систему", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, LoginActivity.class));
                return;
            }
            createBooking();
        });

        btnBack.setOnClickListener(v -> finish());
    }

    private void createBooking() {
        try {
            int userId = sessionManager.getUserId();

            int days = 3;
            double totalPrice = car.getPricePerDay() * days;

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String startDate = sdf.format(new Date());
            String endDate = sdf.format(new Date(new Date().getTime() + days * 86400000L));

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

                Intent intent = new Intent(this, BookingSuccessActivity.class);
                intent.putExtra("car_name", car.getBrand() + " " + car.getModel());
                intent.putExtra("total_price", totalPrice);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Ошибка при бронировании", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Ошибка: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}