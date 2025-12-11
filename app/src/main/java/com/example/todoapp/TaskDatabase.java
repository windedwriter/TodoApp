package com.example.todoapp;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Task.class, History.class}, version = 3, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class TaskDatabase extends RoomDatabase {

    private static TaskDatabase INSTANCE;

    public abstract TaskDao taskDao();
    public abstract HistoryDao historyDao();



    public static synchronized TaskDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            TaskDatabase.class,
                            "task_database"
                    )
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }
}
