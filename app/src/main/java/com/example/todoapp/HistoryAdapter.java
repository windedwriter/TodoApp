package com.example.todoapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    private final List<History> historyList = new ArrayList<>();
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());

    public void setData(List<History> items) {
        historyList.clear();
        if (items != null) {
            historyList.addAll(items);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_history, parent, false);
        return new HistoryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        History h = historyList.get(position);
        holder.tvAction.setText(h.action);
        holder.tvDetails.setText(h.details != null ? h.details : "");
        holder.tvDate.setText(h.created_at != null ? sdf.format(h.created_at) : "");
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    static class HistoryViewHolder extends RecyclerView.ViewHolder {
        TextView tvAction, tvDetails, tvDate;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAction = itemView.findViewById(R.id.tvHistoryAction);
            tvDetails = itemView.findViewById(R.id.tvHistoryDetails);
            tvDate = itemView.findViewById(R.id.tvHistoryDate);
        }
    }
}
