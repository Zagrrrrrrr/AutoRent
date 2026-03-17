package com.example.autorentnew.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class SelectBookingActivity extends AppCompatActivity {

    private ImageView ivCarImage;
    private TextView tvCarName, tvCarDetails, tvSelectedDates, tvTotalPrice;
    private Button btnStartDate, btnEndDate, btnConfirm, btnBack;

    private DatabaseHelper dbHelper;
    private SessionManager sessionManager;
    private Car car;

    private Calendar startCalendar = Calendar.getInstance();
    private Calendar endCalendar = Calendar.getInstance();
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
    private SimpleDateFormat dbDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_booking);

        dbHelper = new DatabaseHelper(this);
        sessionManager = new SessionManager(this);

        int carId = getIntent().getIntExtra("car_id", -1);
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
        displayCarInfo();
        setupListeners();
        updateDateDisplay();
    }

    private void initViews() {
        ivCarImage = findViewById(R.id.ivCarImage);
        tvCarName = findViewById(R.id.tvCarName);
        tvCarDetails = findViewById(R.id.tvCarDetails);
        tvSelectedDates = findViewById(R.id.tvSelectedDates);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        btnStartDate = findViewById(R.id.btnStartDate);
        btnEndDate = findViewById(R.id.btnEndDate);
        btnConfirm = findViewById(R.id.btnConfirm);
        btnBack = findViewById(R.id.btnBack);

        // Устанавливаем начальную дату (сегодня) и конечную (завтра)
        startCalendar.setTime(new Date());
        endCalendar.setTime(new Date());
        endCalendar.add(Calendar.DAY_OF_MONTH, 1);
    }

    private void displayCarInfo() {
        tvCarName.setText(car.getBrand() + " " + car.getModel());
        tvCarDetails.setText(String.format("%d г.в, %.1f л, %s",
                car.getYear(), car.getEngineVolume(), car.getEngineType()));

        String imageName = car.getImageUrl();
        if (imageName != null && !imageName.isEmpty()) {
            int resId = getResources().getIdentifier(imageName, "drawable", getPackageName());
            if (resId != 0) {
                ivCarImage.setImageResource(resId);
            } else {
                ivCarImage.setImageResource(R.drawable.ic_car_placeholder);
            }
        }
    }

    private void setupListeners() {
        btnStartDate.setOnClickListener(v -> showDatePickerDialog(true));
        btnEndDate.setOnClickListener(v -> showDatePickerDialog(false));

        btnConfirm.setOnClickListener(v -> createBooking());
        btnBack.setOnClickListener(v -> finish());
    }

    private void showDatePickerDialog(boolean isStartDate) {
        Calendar calendar = isStartDate ? startCalendar : endCalendar;

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    calendar.set(year, month, dayOfMonth);

                    // Проверяем, что конечная дата не раньше начальной
                    if (endCalendar.before(startCalendar)) {
                        endCalendar.setTimeInMillis(startCalendar.getTimeInMillis());
                        endCalendar.add(Calendar.DAY_OF_MONTH, 1);
                        Toast.makeText(this, "Дата окончания не может быть раньше даты начала", Toast.LENGTH_SHORT).show();
                    }

                    updateDateDisplay();
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        // Устанавливаем минимальную дату - сегодня
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

        datePickerDialog.show();
    }

    private void updateDateDisplay() {
        btnStartDate.setText(dateFormat.format(startCalendar.getTime()));
        btnEndDate.setText(dateFormat.format(endCalendar.getTime()));

        tvSelectedDates.setText(String.format("Период: %s - %s",
                dateFormat.format(startCalendar.getTime()),
                dateFormat.format(endCalendar.getTime())));

        calculateTotalPrice();
    }

    private void calculateTotalPrice() {
        long diffInMillis = endCalendar.getTimeInMillis() - startCalendar.getTimeInMillis();
        int days = (int) TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);

        if (days < 1) days = 1;

        double total = days * car.getPricePerDay();
        tvTotalPrice.setText(String.format("Итого: %d$ за %d %s",
                (int)total,
                days,
                getDayString(days)));
    }

    private String getDayString(int days) {
        if (days % 10 == 1 && days % 100 != 11) return "день";
        if (days % 10 >= 2 && days % 10 <= 4 && (days % 100 < 10 || days % 100 >= 20)) return "дня";
        return "дней";
    }

    private void createBooking() {
        if (!sessionManager.isLoggedIn()) {
            Toast.makeText(this, "Необходимо войти в систему", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginActivity.class));
            return;
        }

        long diffInMillis = endCalendar.getTimeInMillis() - startCalendar.getTimeInMillis();
        int days = (int) TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);
        if (days < 1) days = 1;

        double totalPrice = days * car.getPricePerDay();

        Booking booking = new Booking(
                0,
                car.getId(),
                sessionManager.getUserId(),
                dbDateFormat.format(startCalendar.getTime()),
                dbDateFormat.format(endCalendar.getTime()),
                totalPrice,
                "active",
                ""
        );

        long result = dbHelper.addBooking(booking);

        if (result != -1) {
            Intent intent = new Intent(this, BookingSuccessActivity.class);
            intent.putExtra("car_name", car.getBrand() + " " + car.getModel());
            intent.putExtra("total_price", totalPrice);
            intent.putExtra("days", days);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Ошибка при бронировании", Toast.LENGTH_SHORT).show();
        }
    }
}