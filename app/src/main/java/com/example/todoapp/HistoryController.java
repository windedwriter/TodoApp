package com.example.todoapp;

import android.content.Context;
import java.util.Date;
import java.util.List;
public class HistoryController {
    private final HistoryDao historyDao;

    public HistoryController(Context context) {
        TaskDatabase db = TaskDatabase.getInstance(context);
        historyDao = db.historyDao();
    }
    public void log(String action, String details) {


        if (action == null || action.trim().isEmpty()) {
            throw new IllegalArgumentException("El campo 'action' no puede estar vacío.");
        }


        Date now = new Date();
        if (now == null) {
            throw new IllegalArgumentException("El campo 'created_at' no puede ser nulo.");
        }

        History h = new History();
        h.action = action;
        h.details = details;
        h.created_at = now;

        historyDao.insert(h);
    }

    public List<History> getAllHistory() {
        return historyDao.getAllHistory();
    }
}
