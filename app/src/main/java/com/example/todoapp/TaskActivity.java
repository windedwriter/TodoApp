package com.example.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class TaskActivity extends AppCompatActivity {

    private TaskAdapter taskAdapter;
    private TaskController taskController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_task);

        RecyclerView recyclerViewTasks = findViewById(R.id.rvTasks);
        recyclerViewTasks.setLayoutManager(new LinearLayoutManager(this));

        taskAdapter = new TaskAdapter();
        recyclerViewTasks.setAdapter(taskAdapter);

        taskController = new TaskController(this);
        List<Task> tasks = taskController.getAllTasks();
        taskAdapter.setData(tasks);

        FloatingActionButton fab = findViewById(R.id.fabAddTask);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(TaskActivity.this, AddTaskActivity.class);
            startActivity(intent);
        });
        taskAdapter.setOnTaskDeleteListener(task -> {
            taskController.deleteTask(task);
            List<Task> updated = taskController.getAllTasks();
            taskAdapter.setData(updated);
        });
        taskAdapter.setOnTaskEditListener(task -> {
            Intent intent = new Intent(TaskActivity.this, AddTaskActivity.class);
            intent.putExtra("task", task);
            startActivity(intent);
        });
        taskAdapter.setOnTaskStatusChangeListener((task, completed) -> {
            task.is_completed = completed;
            taskController.updateTask(task);
            Toast.makeText(this, completed ? "Tarea completada" : "Tarea pendiente", Toast.LENGTH_SHORT).show();
        });
        FloatingActionButton fabHistory = findViewById(R.id.fabHistory);
        fabHistory.setOnClickListener(v -> {
            Intent intent = new Intent(TaskActivity.this, HistoryActivity.class);
            startActivity(intent);
        });

    }

}
