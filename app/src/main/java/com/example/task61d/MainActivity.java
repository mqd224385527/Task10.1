package com.example.task61d;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.blankj.utilcode.util.SPUtils;
import com.bumptech.glide.Glide;
import com.example.task61d.databinding.ActivityMainBinding;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    public static List<Question.QuizBean> quizBeanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.tvUsername.setText("Hello,\n" + SPUtils.getInstance().getString("username"));
        Glide.with(this)
                .load(SPUtils.getInstance().getString("avatar"))
                .circleCrop()
                .into(binding.ivAvatar);

        binding.btnNext.setOnClickListener(v -> {
            v.setEnabled(false);
            loadQuestion();
            v.postDelayed(() -> v.setEnabled(true), 2000);
        });

        binding.llInfo.setOnClickListener(v -> startActivity(new Intent(this, ProfileActivity.class)));
    }

    private void loadQuestion() {
        new Thread(() -> {
            AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class, "user-database").build();

            User user = db.userDao().getUserById(SPUtils.getInstance().getInt("id"));

            runOnUiThread(() -> {
                String topic = "movies";
                if (!TextUtils.isEmpty(user.getInterest())) {
                    String[] split = user.getInterest().split(";");
                    Random random = new Random();
                    topic = split[random.nextInt(split.length)];
                }

                showLoadingDialog();

                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(1000, TimeUnit.SECONDS)
                        .readTimeout(1000, TimeUnit.SECONDS)
                        .retryOnConnectionFailure(false)
                        .build();
                Request request = new Request.Builder()
                        .url("http://192.168.50.185:5001/getQuiz?topic=" + topic)
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        if (response.isSuccessful()) {
                            String responseData = response.body().string();
                            runOnUiThread(() -> {
                                quizBeanList = new Gson().fromJson(responseData, Question.class).getQuiz();
                                AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                                        AppDatabase.class, "user-database").build();
                                Executors.newSingleThreadExecutor().execute(() -> {
                                    for (Question.QuizBean quizBean : quizBeanList) {
                                        History history = new History();
                                        history.setUserId(SPUtils.getInstance().getInt("id"));
                                        history.setQuestion(quizBean.getQuestion());
                                        history.setFirstOption(quizBean.getOptions().get(0));
                                        history.setSecondOption(quizBean.getOptions().get(1));
                                        history.setThirdOption(quizBean.getOptions().get(2));
                                        history.setFourOption(quizBean.getOptions().get(3));
                                        history.setCorrectAnswer(quizBean.getCorrect_answer());
                                        db.historyDao().insert(history);
                                    }
                                    runOnUiThread(() -> {
                                        startActivity(new Intent(MainActivity.this, QuestionActivity.class));
                                        loadingDialog.dismiss();
                                    });
                                });
                            });
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        runOnUiThread(() -> {
                            Toast.makeText(MainActivity.this, "network error", Toast.LENGTH_SHORT);
                            loadingDialog.dismiss();
                        });
                    }
                });
            });
        }).start();
    }

    private AlertDialog loadingDialog;

    private void showLoadingDialog() {
        runOnUiThread(() -> {
            ProgressBar progressBar = new ProgressBar(this);
            progressBar.setPadding(50, 50, 50, 50);

            loadingDialog = new AlertDialog.Builder(this)
                    .setView(progressBar)
                    .setMessage("Loading questions...")
                    .setCancelable(false)
                    .create();
            loadingDialog.show();
        });
    }

}