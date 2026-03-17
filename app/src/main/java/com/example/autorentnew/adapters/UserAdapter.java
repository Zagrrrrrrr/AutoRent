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

import com.example.autorentnew.activities.AdminEditUserActivity;
import com.example.autorentnew.models.User;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private Context context;
    private List<User> userList;
    private OnUserActionListener listener;

    public interface OnUserActionListener {
        void onEditClick(User user);
        void onDeleteClick(User user);
    }

    public UserAdapter(Context context, List<User> userList, OnUserActionListener listener) {
        this.context = context;
        this.userList = userList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(
                context.getResources().getIdentifier("item_user", "layout", context.getPackageName()),
                parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);

        holder.tvName.setText(user.getFullName());
        holder.tvLogin.setText("Логин: " + user.getLogin());
        holder.tvRole.setText("Роль: " + user.getRole());
        holder.tvEmail.setText("Email: " + user.getEmail());

        holder.btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(context, AdminEditUserActivity.class);
            intent.putExtra("user_id", user.getId());
            context.startActivity(intent);
        });

        holder.btnDelete.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDeleteClick(user);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView tvName, tvLogin, tvRole, tvEmail;
        Button btnEdit, btnDelete;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            // Находим view через getIdentifier (без R)
            cardView = (CardView) itemView;
            tvName = itemView.findViewById(
                    itemView.getContext().getResources().getIdentifier("tvName", "id", itemView.getContext().getPackageName()));
            tvLogin = itemView.findViewById(
                    itemView.getContext().getResources().getIdentifier("tvLogin", "id", itemView.getContext().getPackageName()));
            tvRole = itemView.findViewById(
                    itemView.getContext().getResources().getIdentifier("tvRole", "id", itemView.getContext().getPackageName()));
            tvEmail = itemView.findViewById(
                    itemView.getContext().getResources().getIdentifier("tvEmail", "id", itemView.getContext().getPackageName()));
            btnEdit = itemView.findViewById(
                    itemView.getContext().getResources().getIdentifier("btnEdit", "id", itemView.getContext().getPackageName()));
            btnDelete = itemView.findViewById(
                    itemView.getContext().getResources().getIdentifier("btnDelete", "id", itemView.getContext().getPackageName()));
        }
    }
}