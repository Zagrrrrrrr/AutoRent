package com.example.autorentnew.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.autorent.R;
import com.example.autorentnew.models.CarStat;

import java.util.List;

public class CarStatAdapter extends RecyclerView.Adapter<CarStatAdapter.StatViewHolder> {

    private Context context;
    private List<CarStat> statList;

    public CarStatAdapter(Context context, List<CarStat> statList) {
        this.context = context;
        this.statList = statList;
    }

    @NonNull
    @Override
    public StatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_car_stat, parent, false);
        return new StatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StatViewHolder holder, int position) {
        CarStat stat = statList.get(position);

        holder.tvCarName.setText(stat.getCarName());
        holder.tvCount.setText("Бронирований: " + stat.getBookingCount());
        holder.tvPosition.setText(String.valueOf(position + 1));
    }

    @Override
    public int getItemCount() {
        return statList.size();
    }

    public static class StatViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView tvPosition, tvCarName, tvCount;

        public StatViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            tvPosition = itemView.findViewById(R.id.tvPosition);
            tvCarName = itemView.findViewById(R.id.tvCarName);
            tvCount = itemView.findViewById(R.id.tvCount);
        }
    }
}