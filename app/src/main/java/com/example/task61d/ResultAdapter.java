package com.example.task61d;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.task61d.databinding.ItemResultBinding;

import java.util.List;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ViewHolder> {
    private Context context;
    private List<Question.QuizBean> dataList;

    public ResultAdapter(Context context, List<Question.QuizBean> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_result, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Question.QuizBean quizBean = dataList.get(position);
        holder.binding.tvNum.setText((position + 1) + ".Question " + (position + 1));
        holder.binding.tvResult.setText(quizBean.getCorrect_answer());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ItemResultBinding binding;

        public ViewHolder(View itemView) {
            super(itemView);
            binding = ItemResultBinding.bind(itemView);
        }
    }
}