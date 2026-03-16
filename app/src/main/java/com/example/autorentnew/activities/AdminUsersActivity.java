package com.example.autorentnew.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.autorent.R;
import com.example.autorentnew.adapters.UserAdapter;
import com.example.autorentnew.database.DatabaseHelper;
import com.example.autorentnew.models.User;

import java.util.List;

public class AdminUsersActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Button btnBack;
    private DatabaseHelper dbHelper;
    private UserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_users);

        dbHelper = new DatabaseHelper(this);

        recyclerView = findViewById(R.id.recyclerView);
        btnBack = findViewById(R.id.btnBack);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadUsers();

        btnBack.setOnClickListener(v -> finish());
    }

    private void loadUsers() {
        List<User> userList = dbHelper.getAllUsers();

        adapter = new UserAdapter(this, userList, new UserAdapter.OnUserActionListener() {
            @Override
            public void onEditClick(User user) {
                Toast.makeText(AdminUsersActivity.this, "Редактирование: " + user.getLogin(), Toast.LENGTH_SHORT).show();
                // TODO: реализовать редактирование
            }

            @Override
            public void onDeleteClick(User user) {
                boolean deleted = dbHelper.deleteUser(user.getId());
                if (deleted) {
                    Toast.makeText(AdminUsersActivity.this, "Пользователь удален", Toast.LENGTH_SHORT).show();
                    loadUsers();
                } else {
                    Toast.makeText(AdminUsersActivity.this, "Ошибка удаления", Toast.LENGTH_SHORT).show();
                }
            }
        });

        recyclerView.setAdapter(adapter);
    }
}