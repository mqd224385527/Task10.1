package com.example.task61d;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.example.task61d.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    private String avatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.ivAvatar.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            startActivityForResult(intent, 100);
        });

        binding.btnCreate.setOnClickListener(v -> {
            String username = binding.etUsername.getText().toString();
            String email = binding.etEmail.getText().toString();
            String confirmEmail = binding.etConfirmEmail.getText().toString();
            String password = binding.etPassword.getText().toString();
            String confirmPassword = binding.etConfirmPassword.getText().toString();
            String phone = binding.etPhoneNumber.getText().toString();
            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(email) || TextUtils.isEmpty(confirmEmail) || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword) || TextUtils.isEmpty(phone)) {
                Toast.makeText(this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!email.equals(confirmEmail)) {
                Toast.makeText(this, "Emails do not match", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!password.equals(confirmPassword)) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }
            User user = new User();
            user.setUsername(username);
            user.setEmail(email);
            user.setPassword(password);
            user.setPhone(phone);
            user.setAvatar(avatar);
            // 异步插入数据库
            new Thread(() -> {
                AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, "user-database").build();
                db.userDao().insert(user);
                runOnUiThread(() -> {
                    startActivity(new Intent(RegisterActivity.this, InterestActivity.class));
                    finish();
                });
            }).start();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("TAG", "onActivityResult: " + data.getData());
        Uri imageData = data.getData();
        if (imageData == null) {
            ToastUtils.showShort("图片选择失败");
            return;
        }

        avatar = data.getDataString();
        Glide.with(this)
                .load(avatar)
                .circleCrop()
                .into(binding.ivAvatar);
    }

}