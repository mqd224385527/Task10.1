package com.example.task61d;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.task61d.databinding.ActivityInterestBinding;

import java.util.HashSet;
import java.util.Set;

public class InterestActivity extends AppCompatActivity {

    private ActivityInterestBinding binding;
    private Set<String> selectedInterests = new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityInterestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupButtonListeners();
    }

    private void setupButtonListeners() {
        binding.btnAlgorithms1.setOnClickListener(v -> toggleInterest("Algorithms", v));
        binding.btnDataStructures1.setOnClickListener(v -> toggleInterest("Data Structures", v));
        binding.btnWebDevelopment1.setOnClickListener(v -> toggleInterest("Web Development", v));
        binding.btnTesting1.setOnClickListener(v -> toggleInterest("Testing", v));
        binding.btnAndroid1.setOnClickListener(v -> toggleInterest("Android", v));
        binding.btnIos1.setOnClickListener(v -> toggleInterest("IOS", v));
        binding.btnMysql1.setOnClickListener(v -> toggleInterest("MySQL", v));
        binding.btnOracle1.setOnClickListener(v -> toggleInterest("Oracle", v));
        binding.btnJavascript1.setOnClickListener(v -> toggleInterest("Javascript", v));
        binding.btnReact1.setOnClickListener(v -> toggleInterest("React", v));
        binding.btnVue1.setOnClickListener(v -> toggleInterest("Vue", v));
        binding.btnNet1.setOnClickListener(v -> toggleInterest(".NET", v));


        binding.btnNext.setOnClickListener(v -> {
            if (selectedInterests.size() <= 10) {
                new Thread(() -> {
                    AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                            AppDatabase.class, "user-database").build();

                    db.userDao().updateInterest(getIntent().getIntExtra("id", 0), String.join(";", selectedInterests));

                    runOnUiThread(this::finish);
                }).start();
            } else {
                Toast.makeText(this, "You can select up to 10 interests", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void toggleInterest(String interest, View button) {
        if (selectedInterests.contains(interest)) {
            selectedInterests.remove(interest);
            button.setBackgroundColor(getResources().getColor(R.color.light_blue, null)); // 取消选中状态
        } else {
            selectedInterests.add(interest);
            button.setBackgroundColor(getResources().getColor(R.color.selected_blue, null)); // 选中状态
        }
    }


}