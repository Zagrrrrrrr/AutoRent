package com.example.autorentnew.adapters;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.autorent.R;
import com.example.autorentnew.activities.CarDetailActivity;
import com.example.autorentnew.models.Car;

import java.util.List;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.CarViewHolder> {

    private Context context;
    private List<Car> carList;
    private OnCarClickListener listener;

    public interface OnCarClickListener {
        void onBookClick(Car car);
    }

    public CarAdapter(Context context, List<Car> carList, OnCarClickListener listener) {
        this.context = context;
        this.carList = carList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_car, parent, false);
        return new CarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarViewHolder holder, int position) {
        Car car = carList.get(position);

        holder.tvCarName.setText(car.getBrand() + " " + car.getModel());
        holder.tvYear.setText(car.getYear() + " г.в");
        holder.tvEngine.setText(String.format("%.1f л. %s", car.getEngineVolume(), car.getEngineType()));
        holder.tvPricePerDay.setText((int)car.getPricePerDay() + "$/сутки");

        // Клик по всей карточке - открываем детали
        holder.cardView.setOnClickListener(v -> {
            Intent intent = new Intent(context, CarDetailActivity.class);
            intent.putExtra("car_id", car.getId());
            context.startActivity(intent);
        });

        // Клик по кнопке бронирования
        holder.btnBook.setOnClickListener(v -> {
            if (listener != null) {
                listener.onBookClick(car);
            }
        });
    }

    @Override
    public int getItemCount() {
        return carList.size();
    }

    public static class CarViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView tvCarName, tvYear, tvEngine, tvPricePerDay;
        Button btnBook;

        public CarViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            tvCarName = itemView.findViewById(R.id.tvCarName);
            tvYear = itemView.findViewById(R.id.tvYear);
            tvEngine = itemView.findViewById(R.id.tvEngine);
            tvPricePerDay = itemView.findViewById(R.id.tvPricePerDay);
            btnBook = itemView.findViewById(R.id.btnBook);
        }
    }
}