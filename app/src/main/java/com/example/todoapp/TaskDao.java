package com.example.todoapp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaskDao {

    @Insert
    void insert(Task task);

    @Update
    void update(Task task);

    @Delete
    void delete(Task task);


    @Query("SELECT * FROM Tasks")
    List<Task> getAllTasks();
    @Query("UPDATE Tasks SET is_completed = :completed WHERE id = :taskId")
    void updateCompletedStatus(int taskId, boolean completed);

    @Query("SELECT * FROM Tasks WHERE is_completed = 1 ORDER BY created_at DESC")
    List<Task> getCompletedTasks();

    @Query("SELECT * FROM Tasks WHERE is_completed = 0 ORDER BY created_at DESC")
    List<Task> getPendingTasks();
}


