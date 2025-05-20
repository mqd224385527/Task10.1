package com.example.task61d;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.example.task61d.databinding.ActivityProfileBinding;
import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.List;
import java.util.concurrent.Executors;

public class ProfileActivity extends AppCompatActivity {

    private ActivityProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.ibBack.setOnClickListener(v -> finish());

        binding.tvUsername.setText(SPUtils.getInstance().getString("username"));
        binding.tvEmail.setText(SPUtils.getInstance().getString("email"));
        Glide.with(this)
                .load(SPUtils.getInstance().getString("avatar"))
                .circleCrop()
                .into(binding.ivAvatar);

        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "user-database").build();
//        List<History> historyList = db.historyDao().getAllHistory(SPUtils.getInstance().getInt("id"));
//        binding.tvTotalQuestions.setText(String.valueOf(historyList.size()));
        Executors.newSingleThreadExecutor().execute(() -> {
            List<History> historyList = db.historyDao().getAllHistory(SPUtils.getInstance().getInt("id"));
            runOnUiThread(() -> binding.tvTotalQuestions.setText(String.valueOf(historyList.size())));
        });

        binding.cardInfo.setOnClickListener(v -> startActivity(new Intent(this, UpgradeActivity.class)));
        binding.cardTotal.setOnClickListener(v -> startActivity(new Intent(this, HistoryActivity.class)));

        binding.btnShare.setOnClickListener(v -> {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            ImageView imageViewQrCode = new ImageView(this);


            Executors.newSingleThreadExecutor().execute(() -> {
                User user = db.userDao().getUserById(SPUtils.getInstance().getInt("id"));
                runOnUiThread(() -> {
                    Bitmap qrCodeBitmap = generateQrCodeBitmap(user.toString());

                    if (qrCodeBitmap != null) {
                        imageViewQrCode.setImageBitmap(qrCodeBitmap);
                        dialogBuilder.setView(imageViewQrCode);
                        dialogBuilder.setTitle("Share");
                        dialogBuilder.create().show();
                    } else {
                        ToastUtils.showShort("Failed!");
                    }
                });
            });
        });
    }

    private Bitmap generateQrCodeBitmap(String content) {
        try {
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            return barcodeEncoder.encodeBitmap(content,
                    BarcodeFormat.QR_CODE, 500, 500);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}