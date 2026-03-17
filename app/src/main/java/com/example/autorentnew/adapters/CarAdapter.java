package com.example.autorentnew.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.autorentnew.activities.SelectBookingActivity;
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
        View view = LayoutInflater.from(context).inflate(
                context.getResources().getIdentifier("item_car", "layout", context.getPackageName()),
                parent, false);
        return new CarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarViewHolder holder, int position) {
        Car car = carList.get(position);

        holder.tvCarName.setText(car.getBrand() + " " + car.getModel());
        holder.tvYear.setText(car.getYear() + " г.в");
        holder.tvPricePerDay.setText((int)car.getPricePerDay() + "$/сутки");

        // Загружаем картинку
        String imageName = car.getImageUrl();
        if (imageName != null && !imageName.isEmpty()) {
            int resId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
            if (resId != 0) {
                holder.ivCarImage.setImageResource(resId);
            } else {
                holder.ivCarImage.setImageResource(
                        context.getResources().getIdentifier("ic_car_placeholder", "drawable", context.getPackageName()));
            }
        } else {
            holder.ivCarImage.setImageResource(
                    context.getResources().getIdentifier("ic_car_placeholder", "drawable", context.getPackageName()));
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, SelectBookingActivity.class);
            intent.putExtra("car_id", car.getId());
            context.startActivity(intent);
        });

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
        ImageView ivCarImage;
        TextView tvCarName, tvYear, tvPricePerDay;
        Button btnBook;

        public CarViewHolder(@NonNull View itemView) {
            super(itemView);
            ivCarImage = itemView.findViewById(
                    itemView.getContext().getResources().getIdentifier("ivCarImage", "id", itemView.getContext().getPackageName()));
            tvCarName = itemView.findViewById(
                    itemView.getContext().getResources().getIdentifier("tvCarName", "id", itemView.getContext().getPackageName()));
            tvYear = itemView.findViewById(
                    itemView.getContext().getResources().getIdentifier("tvYear", "id", itemView.getContext().getPackageName()));
            tvPricePerDay = itemView.findViewById(
                    itemView.getContext().getResources().getIdentifier("tvPricePerDay", "id", itemView.getContext().getPackageName()));
            btnBook = itemView.findViewById(
                    itemView.getContext().getResources().getIdentifier("btnBook", "id", itemView.getContext().getPackageName()));
        }
    }
}