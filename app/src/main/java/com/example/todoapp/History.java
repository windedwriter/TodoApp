package com.example.todoapp;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;
@Entity (tableName = "history")
public class History implements Serializable {
    @PrimaryKey (autoGenerate = true)
    public int history_id;

    public String action;

    public Date created_at;

    public String details;

}
