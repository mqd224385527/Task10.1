package com.example.task61d;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.task61d.databinding.ActivityQuestionBinding;

public class QuestionActivity extends AppCompatActivity {

    private ActivityQuestionBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityQuestionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.recycler.setLayoutManager(new LinearLayoutManager(this));
        binding.recycler.setAdapter(new QuestionAdapter(this, MainActivity.quizBeanList));

        binding.btnSubmit.setOnClickListener(v -> {
            startActivity(new Intent(this, ResultActivity.class));
            finish();
        });

        binding.btnBack.setOnClickListener(v->finish());
    }


}