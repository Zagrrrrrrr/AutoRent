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

import com.example.autorentnew.activities.AdminEditCarActivity;
import com.example.autorentnew.models.Car;

public class AdminCarAdapter extends RecyclerView.Adapter<AdminCarAdapter.CarViewHolder> {

    private Context context;
    private java.util.List<Car> carList;
    private OnCarActionListener listener;

    public interface OnCarActionListener {
        void onEditClick(Car car);
        void onDeleteClick(Car car);
    }

    public AdminCarAdapter(Context context, java.util.List<Car> carList, OnCarActionListener listener) {
        this.context = context;
        this.carList = carList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(
                context.getResources().getIdentifier("item_admin_car", "layout", context.getPackageName()),
                parent, false);
        return new CarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarViewHolder holder, int position) {
        Car car = carList.get(position);

        holder.tvName.setText(car.getBrand() + " " + car.getModel());
        holder.tvYear.setText("Год: " + car.getYear());
        holder.tvEngine.setText(String.format("%.1f л. %s", car.getEngineVolume(), car.getEngineType()));
        holder.tvPrice.setText((int)car.getPricePerDay() + "$/сутки");
        holder.tvStatus.setText(car.isAvailable() ? "Доступен" : "Забронирован");

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

        holder.btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(context, AdminEditCarActivity.class);
            intent.putExtra("car_id", car.getId());
            context.startActivity(intent);
        });

        holder.btnDelete.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDeleteClick(car);
            }
        });
    }

    @Override
    public int getItemCount() {
        return carList.size();
    }

    public static class CarViewHolder extends RecyclerView.ViewHolder {
        ImageView ivCarImage;
        TextView tvName, tvYear, tvEngine, tvPrice, tvStatus;
        Button btnEdit, btnDelete;

        public CarViewHolder(@NonNull View itemView) {
            super(itemView);
            ivCarImage = itemView.findViewById(
                    itemView.getContext().getResources().getIdentifier("ivCarImage", "id", itemView.getContext().getPackageName()));
            tvName = itemView.findViewById(
                    itemView.getContext().getResources().getIdentifier("tvName", "id", itemView.getContext().getPackageName()));
            tvYear = itemView.findViewById(
                    itemView.getContext().getResources().getIdentifier("tvYear", "id", itemView.getContext().getPackageName()));
            tvEngine = itemView.findViewById(
                    itemView.getContext().getResources().getIdentifier("tvEngine", "id", itemView.getContext().getPackageName()));
            tvPrice = itemView.findViewById(
                    itemView.getContext().getResources().getIdentifier("tvPrice", "id", itemView.getContext().getPackageName()));
            tvStatus = itemView.findViewById(
                    itemView.getContext().getResources().getIdentifier("tvStatus", "id", itemView.getContext().getPackageName()));
            btnEdit = itemView.findViewById(
                    itemView.getContext().getResources().getIdentifier("btnEdit", "id", itemView.getContext().getPackageName()));
            btnDelete = itemView.findViewById(
                    itemView.getContext().getResources().getIdentifier("btnDelete", "id", itemView.getContext().getPackageName()));
        }
    }
}