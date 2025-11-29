package com.example.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddTaskActivity extends AppCompatActivity {

    private EditText etTitle;
    private EditText etDescription;
    private EditText etCreated_at;
    private Button btnSave;


    private Task editableTask;

    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.addtask);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();


        if (getIntent() != null && getIntent().hasExtra("task")) {
            editableTask = (Task) getIntent().getSerializableExtra("task");
            fillFormWithTask(editableTask);
            btnSave.setText("Actualizar tarea");
        }

        btnSave.setOnClickListener(view -> {
            String title = etTitle.getText().toString().trim();
            String description = etDescription.getText().toString().trim();
            String createdAtStr = etCreated_at.getText().toString().trim();

            if (title.isEmpty() || createdAtStr.isEmpty()) {
                Toast.makeText(this, "Faltan campos por llenar", Toast.LENGTH_SHORT).show();
                return;
            }

            Date createdAt;
            try {
                createdAt = sdf.parse(createdAtStr);
            } catch (ParseException e) {
                Toast.makeText(this, "Formato de fecha inválido (usa dd/MM/yyyy)", Toast.LENGTH_SHORT).show();
                return;
            }

            if (editableTask == null) {

                saveTask(title, description, createdAt);
            } else {

                updateTask(editableTask, title, description, createdAt);
            }
        });
    }

    private void fillFormWithTask(Task task) {
        etTitle.setText(task.task_title);
        etDescription.setText(task.task_description != null ? task.task_description : "");
        etCreated_at.setText(sdf.format(task.created_at));
    }

    private void saveTask(String title, String description, Date createdAt) {
        TaskController taskController = new TaskController(this);
        boolean result = taskController.addTask(title, description, createdAt);

        if (result) {
            Toast.makeText(this, getString(R.string.task_saved_success), Toast.LENGTH_SHORT).show();
            clearForm();
            showTaskActivity();
        } else {
            Toast.makeText(this, getString(R.string.error_task_save), Toast.LENGTH_SHORT).show();
        }
    }

    private void updateTask(Task task, String title, String description, Date createdAt) {
        TaskController taskController = new TaskController(this);

        task.task_title = title;
        task.task_description = description;
        task.created_at = createdAt;

        taskController.updateTask(task);

        Toast.makeText(this, "Tarea actualizada correctamente", Toast.LENGTH_SHORT).show();
        showTaskActivity();
    }

    private void showTaskActivity() {
        Intent intent = new Intent(AddTaskActivity.this, TaskActivity.class);
        startActivity(intent);
        finish();
    }

    private void clearForm() {
        etTitle.setText("");
        etDescription.setText("");
        etCreated_at.setText("");
    }

    private void initViews() {
        etTitle = findViewById(R.id.etTitle);
        etDescription = findViewById(R.id.etDescription);
        etCreated_at = findViewById(R.id.etCreated_at);
        btnSave = findViewById(R.id.btnSave);
    }
}
