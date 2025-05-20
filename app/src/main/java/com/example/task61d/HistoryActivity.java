package com.example.task61d;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;

import com.blankj.utilcode.util.SPUtils;
import com.example.task61d.databinding.ActivityHistoryBinding;

import java.util.List;
import java.util.concurrent.Executors;

public class HistoryActivity extends AppCompatActivity {

    private ActivityHistoryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.ibBack.setOnClickListener(v -> finish());
        binding.recycler.setLayoutManager(new LinearLayoutManager(this));

        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "user-database").build();
        Executors.newSingleThreadExecutor().execute(() -> {
            List<History> historyList = db.historyDao().getAllHistory(SPUtils.getInstance().getInt("id"));
            runOnUiThread(() -> binding.recycler.setAdapter(new HistoryAdapter(this, historyList)));
        });

    }
}