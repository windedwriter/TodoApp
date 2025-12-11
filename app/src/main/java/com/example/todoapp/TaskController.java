package com.example.todoapp;

import android.content.Context;
import android.util.Log;

import java.util.List;

public class TaskController {
    private final TaskDao taskDao;
    private final HistoryController historyController;


    public TaskController(Context context) {
        TaskDatabase database = TaskDatabase.getInstance(context);
        taskDao = database.taskDao();
        historyController = new HistoryController(context);
    }


    public boolean addTask(String title, String description, java.util.Date createdAt) {
        try {
            Task task = new Task();
            task.task_title = title;
            task.task_description = description;
            task.created_at = createdAt;

            taskDao.insert(task);
            historyController.log("insert_task", "Tarea '" + title + "' creada");


            Log.i("TASK_SAVE", "La tarea se agregó correctamente");
            return true;

        } catch (Exception e) {
            Log.e("TASK_ERROR", "Error al agregar tarea: " + e.getMessage());
            return false;
        }
    }

    // Get all tasks
    public List<Task> getAllTasks() {
        return taskDao.getAllTasks();
    }

    // Update task
    public void updateTask(Task task) {
        taskDao.update(task);
        historyController.log("update_task", "Tarea '" + task.task_title + "' actualizada");
    }

    // Delete task
    public void deleteTask(Task task) {
        taskDao.delete(task);
        historyController.log("delete_task", "Tarea '" + task.task_title + "' eliminada");
    }

    public void updateCompletedStatus(Task task, boolean completed) {
        task.is_completed = completed;
        taskDao.update(task);

        String action = completed ? "complete_task" : "uncomplete_task";
        historyController.log(action, "Tarea: " + task.task_title);
    }
}
