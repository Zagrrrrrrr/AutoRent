package com.example.autorentnew.activities;


import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.autorent.R;
import com.example.autorentnew.adapters.BookingAdapter;
import com.example.autorentnew.database.DatabaseHelper;
import com.example.autorentnew.models.Booking;
import com.example.autorentnew.utils.SessionManager;

import java.util.List;

public class MyBookingsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView tvTotalSpent;
    private Button btnBack;
    private DatabaseHelper dbHelper;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bookings);

        dbHelper = new DatabaseHelper(this);
        sessionManager = new SessionManager(this);

        recyclerView = findViewById(R.id.recyclerView);
        tvTotalSpent = findViewById(R.id.tvTotalSpent);
        btnBack = findViewById(R.id.btnBack);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadBookings();

        btnBack.setOnClickListener(v -> finish());
    }

    private void loadBookings() {
        int userId = sessionManager.getUserId();
        List<Booking> bookingList = dbHelper.getUserBookings(userId);

        // Подсчет общей суммы
        double total = 0;
        for (Booking booking : bookingList) {
            total += booking.getTotalPrice();
        }

        tvTotalSpent.setText("Итого потрачено: " + (int)total + "$");

        BookingAdapter adapter = new BookingAdapter(this, bookingList);
        recyclerView.setAdapter(adapter);
    }
}