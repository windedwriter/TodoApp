package com.example.todoapp;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.Date;
import java.util.List;
@Dao
public interface HistoryDao {
    @Insert
    void insert(History history);

    @Query("SELECT * FROM history ORDER BY created_at DESC")
    List<History> getAllHistory();
    @Query("SELECT * FROM history WHERE action = :action ORDER BY created_at DESC")
    List<History> getHistoryByAction(String action);

    @Query("SELECT * FROM history WHERE created_at BETWEEN :from AND :to ORDER BY created_at DESC")
    List<History> getHistoryByDateRange(Date from, Date to);
}
