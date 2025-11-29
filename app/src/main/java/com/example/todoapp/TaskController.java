package com.example.todoapp;

import android.content.Context;
import android.util.Log;

import java.util.List;

public class TaskController {
    private final TaskDao taskDao;

    public TaskController(Context context) {
        TaskDatabase database = TaskDatabase.getInstance(context);
        taskDao = database.taskDao();
    }

    // Create a TASK
    public boolean addTask(String title, String description, java.util.Date createdAt){
        try{
            Task task = new Task();
            task.task_title = title;
            task.task_description = description;
            task.created_at = createdAt;

            taskDao.insert(task);

            Log.i("TASK_SAVE","La tarea se agregó correctamente");
            return true;

        }catch (Exception e){
            Log.e("TASK_ERROR","Error al agregar tarea: " + e.getMessage());
            return false;
        }
    }

    // Get all tasks
    public List<Task> getAllTasks(){
        return taskDao.getAllTasks();
    }

    // Update task
    public void updateTask(Task task){
        taskDao.update(task);
    }

    // Delete task
    public void deleteTask(Task task){
        taskDao.delete(task);
    }
}
