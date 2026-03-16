package com.example.autorentnew.adapters;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.autorent.R;
import com.example.autorentnew.models.Car;

import java.util.List;

public class AdminCarAdapter extends RecyclerView.Adapter<AdminCarAdapter.CarViewHolder> {

    private Context context;
    private List<Car> carList;
    private OnCarActionListener listener;

    public interface OnCarActionListener {
        void onEditClick(Car car);
        void onDeleteClick(Car car);
    }

    public AdminCarAdapter(Context context, List<Car> carList, OnCarActionListener listener) {
        this.context = context;
        this.carList = carList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_admin_car, parent, false);
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
        holder.tvStatus.setTextColor(car.isAvailable() ?
                context.getColor(android.R.color.holo_green_dark) :
                context.getColor(android.R.color.holo_red_dark));

        holder.btnEdit.setOnClickListener(v -> {
            if (listener != null) {
                listener.onEditClick(car);
            }
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
        CardView cardView;
        TextView tvName, tvYear, tvEngine, tvPrice, tvStatus;
        Button btnEdit, btnDelete;

        public CarViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            tvName = itemView.findViewById(R.id.tvName);
            tvYear = itemView.findViewById(R.id.tvYear);
            tvEngine = itemView.findViewById(R.id.tvEngine);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}