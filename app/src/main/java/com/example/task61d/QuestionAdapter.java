package com.example.task61d;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.task61d.databinding.ItemQuestionBinding;

import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> {
    private Context context;
    private List<Question.QuizBean> dataList;

    public QuestionAdapter(Context context, List<Question.QuizBean> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_question, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Question.QuizBean quizBean = dataList.get(position);
        holder.binding.tvNum.setText((position + 1) + ".Question " + (position + 1));
        holder.binding.tvQuestion.setText(quizBean.getQuestion());
        holder.binding.rbA.setText(quizBean.getOptions().get(0));
        holder.binding.rbB.setText(quizBean.getOptions().get(1));
        holder.binding.rbC.setText(quizBean.getOptions().get(2));
        holder.binding.rbD.setText(quizBean.getOptions().get(3));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ItemQuestionBinding binding;

        public ViewHolder(View itemView) {
            super(itemView);
            binding = ItemQuestionBinding.bind(itemView);
        }
    }
}