package com.example.task61d;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.task61d.databinding.ActivityCardInformationBinding;

import java.util.Random;

public class CardInformationActivity extends AppCompatActivity {

    private ActivityCardInformationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityCardInformationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.tvMoney.setText("Amount: $" + new Random().nextInt(100));

        binding.btnPay.setOnClickListener(v -> {
            String s2 = binding.etCardNumber.getText().toString();
            String s1 = binding.etName.getText().toString();
            String s3 = binding.etDate.getText().toString();
            String s4 = binding.etCvc.getText().toString();
            if (TextUtils.isEmpty(s1) || TextUtils.isEmpty(s2) || TextUtils.isEmpty(s3) || TextUtils.isEmpty(s4)) {
                Toast.makeText(this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
                return;
            }
            finish();
        });
    }
}