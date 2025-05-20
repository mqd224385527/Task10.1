package com.example.task61d;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.task61d.databinding.ActivityUpgradeBinding;

public class UpgradeActivity extends AppCompatActivity {

    private ActivityUpgradeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityUpgradeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.ibBack.setOnClickListener(v->finish());

        binding.btnPurchase.setOnClickListener(v->startActivity(new Intent(this, CardInformationActivity.class)));
        binding.btnPurchase2.setOnClickListener(v->startActivity(new Intent(this, CardInformationActivity.class)));
        binding.btnPurchase3.setOnClickListener(v->startActivity(new Intent(this, CardInformationActivity.class)));
    }
}