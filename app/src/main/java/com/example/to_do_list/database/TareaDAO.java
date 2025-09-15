package com.example.to_do_list.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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

    //Obtener todas las tareas
    public List<Tarea> obtenerTodos() {
        List<Tarea> tareas = new ArrayList<>();
        Cursor cursor =database.query(DatabaseHelper.TABLA_TAREAS,
                null,
                null,
                null,
                null,
                null,
                DatabaseHelper.COLUMNA_TITULO + " ASC"
        );

        if (cursor.moveToFirst()) {
            do {
                Tarea tarea = new Tarea();
                tarea.setIdTarea(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMNA_ID)));
                tarea.setNombre(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMNA_TITULO)));
                tarea.setDescripcion(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMNA_DESCRIPCION)));
                tarea.setFechaCreado(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMNA_FECHA_CREADO)));
                tareas.add(tarea);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return tareas;
    }

    //Insertar nueva tarea
    public long insertarTarea(String nombre, String descripcion) {
        ContentValues values =new ContentValues();
        values.put(DatabaseHelper.COLUMNA_TITULO, nombre);
        values.put(DatabaseHelper.COLUMNA_DESCRIPCION, descripcion);
        return database.insert(DatabaseHelper.TABLA_TAREAS, null, values);
    }
}
