package com.example.todoapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private final List<Task> taskList = new ArrayList<>();


    public interface OnTaskDeleteListener {
        void onDelete(Task task);
    }
    public interface OnTaskStatusChangeListener {
        void onStatusChanged(Task task, boolean isCompleted);
    }

    private OnTaskStatusChangeListener statusListener;

    public void setOnTaskStatusChangeListener(OnTaskStatusChangeListener listener) {
        this.statusListener = listener;
    }

    public interface OnTaskEditListener {
        void onEdit(Task task);
    }

    private OnTaskDeleteListener deleteListener;
    private OnTaskEditListener editListener;


    public void setOnTaskDeleteListener(OnTaskDeleteListener listener) {
        this.deleteListener = listener;
    }
    public void setOnTaskEditListener(OnTaskEditListener listener) {
        this.editListener = listener;
    }

    public void setData(List<Task> tasks) {
        taskList.clear();
        if (tasks != null) {
            taskList.addAll(tasks);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = taskList.get(position);

        holder.txtTitle.setText(task.task_title);
        holder.txtDescription.setText(task.task_description);


        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String dateFormatted = sdf.format(task.created_at);
        holder.txtCreated_at.setText(dateFormatted);
        holder.switchCompleted.setChecked(task.is_completed);

        holder.switchCompleted.setOnCheckedChangeListener((buttonView, isChecked) -> {
            task.is_completed = isChecked;
            if (statusListener != null) {
                statusListener.onStatusChanged(task, isChecked);
            }
        });



        holder.btnDelete.setOnClickListener(v -> {
            if (deleteListener != null) {
                deleteListener.onDelete(task);
            }
        });
        holder.btnEdit.setOnClickListener(v -> {
            if (editListener != null) {
                editListener.onEdit(task);
            }
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle, txtDescription, txtCreated_at;
        ImageView btnDelete, btnEdit;
        Switch switchCompleted;


        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.tvTitle);
            txtDescription = itemView.findViewById(R.id.tvDescription);
            txtCreated_at = itemView.findViewById(R.id.tvCreated_at);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            switchCompleted = itemView.findViewById(R.id.switchCompleted);
        }
    }
}
