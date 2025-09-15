package com.example.to_do_list.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "todolist";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLA_TAREAS = "tareas";
    public static final String COLUMNA_ID = "idTarea";
    public static final String COLUMNA_TITULO = "titulo";
    public static final String COLUMNA_DESCRIPCION = "descripcion";
    public static final String COLUMNA_FECHA_CREADO = "fechaCreado";

    private static final String CREAR_TABLA_TAREAS =
            "CREATE TABLE " + TABLA_TAREAS + "(" +
                    COLUMNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMNA_TITULO + " TEXT NOT NULL," +
                    COLUMNA_DESCRIPCION + " TEXT NOT NULL," +
                    COLUMNA_FECHA_CREADO + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                    ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREAR_TABLA_TAREAS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLA_TAREAS);
    }
}
