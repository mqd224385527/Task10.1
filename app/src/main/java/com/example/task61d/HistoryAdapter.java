package com.example.task61d;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.task61d.databinding.ItemHistoryBinding;
import com.example.task61d.databinding.ItemQuestionBinding;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private Context context;
    private List<History> dataList;

    public HistoryAdapter(Context context, List<History> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        History history = dataList.get(position);
        holder.binding.tvNum.setText((position + 1) + ".Question " + (position + 1));
        holder.binding.tvQuestion.setText(history.getQuestion());
        holder.binding.rbA.setText(history.getFirstOption());
        holder.binding.rbB.setText(history.getSecondOption());
        holder.binding.rbC.setText(history.getFirstOption());
        holder.binding.rbD.setText(history.getFirstOption());
        holder.binding.tvCorrectAnswer.setText(history.getCorrectAnswer());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ItemHistoryBinding binding;

        public ViewHolder(View itemView) {
            super(itemView);
            binding = ItemHistoryBinding.bind(itemView);
        }
    }
}