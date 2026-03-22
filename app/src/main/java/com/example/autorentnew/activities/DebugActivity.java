package com.example.autorentnew.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.autorentnew.database.DatabaseHelper;
import com.example.autorentnew.models.Booking;
import com.example.autorentnew.models.Car;
import com.example.autorentnew.models.CarStat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class DebugActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private LinearLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ScrollView scrollView = new ScrollView(this);
        container = new LinearLayout(this);
        container.setOrientation(LinearLayout.VERTICAL);
        container.setPadding(16, 16, 16, 16);
        scrollView.addView(container);
        setContentView(scrollView);

        dbHelper = new DatabaseHelper(this);

        checkDatabase();
    }

    private void checkDatabase() {
        addText("=== ПРОВЕРКА БАЗЫ ДАННЫХ ===");

        // Проверяем бронирования
        List<Booking> bookings = dbHelper.getAllBookings();
        addText("Всего бронирований: " + bookings.size());

        if (bookings.isEmpty()) {
            addText("❌ НЕТ НИ ОДНОГО БРОНИРОВАНИЯ!");
        } else {
            for (Booking b : bookings) {
                addText("--- БРОНЬ ID: " + b.getId() + " ---");
                addText("  carId: " + b.getCarId());
                addText("  userId: " + b.getUserId());
                addText("  startDate: " + b.getStartDate());
                addText("  endDate: " + b.getEndDate());
                addText("  createdAt: " + b.getCreatedAt());
                addText("  totalPrice: " + b.getTotalPrice());
                addText("  status: " + b.getStatus());
            }
        }

        // Проверяем автомобили
        List<Car> cars = dbHelper.getAllCars();
        addText("\nВсего автомобилей: " + cars.size());
        for (Car c : cars) {
            addText("  " + c.getBrand() + " " + c.getModel() + " - " + (int)c.getPricePerDay() + "$/день");
        }

        // Проверяем статистику за последний месяц
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Calendar cal = Calendar.getInstance();
        String endDate = sdf.format(cal.getTime());
        cal.add(Calendar.MONTH, -1);
        String startDate = sdf.format(cal.getTime());

        addText("\n=== СТАТИСТИКА ЗА ПОСЛЕДНИЙ МЕСЯЦ ===");
        addText("Период: " + startDate + " - " + endDate);

        double profit = dbHelper.getProfitByPeriod(startDate, endDate);
        addText("💰 Прибыль: " + profit + "$");

        List<CarStat> stats = dbHelper.getPopularCarsStats(startDate, endDate);
        addText("Популярные авто: " + stats.size());
        for (CarStat stat : stats) {
            addText("  " + stat.getBrand() + " " + stat.getModel() + " - " + stat.getBookingCount() + " бронирований");
        }
    }

    private void addText(String text) {
        TextView tv = new TextView(this);
        tv.setText(text);
        tv.setTextSize(14);
        tv.setPadding(0, 8, 0, 8);
        container.addView(tv);
        Log.d("DEBUG_DB", text);
    }
}