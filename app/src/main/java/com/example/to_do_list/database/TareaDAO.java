package com.example.to_do_list.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.to_do_list.models.Tarea;

import java.util.ArrayList;
import java.util.List;

public class TareaDAO {
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    public TareaDAO(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public void open() {
        database = databaseHelper.getWritableDatabase();
    }

    public void close() {
        if (database != null && database.isOpen()) {
            database.close();
        }
    }

    public List<Tarea> obtenerTodos() {
        List<Tarea> tareas = new ArrayList<>();
    }
}
