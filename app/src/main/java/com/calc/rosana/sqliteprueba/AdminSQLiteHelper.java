package com.calc.rosana.sqliteprueba;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by rosana on 16/11/2016.
 */

public class AdminSQLiteHelper extends SQLiteOpenHelper {

    public String sentence = "CREATE TABLE articulos(codigo INTEGER PRIMARY KEY, descripcion TEXT, precio REAL)";


    public AdminSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        if (db.isReadOnly()) {
            db = getWritableDatabase();
        }
        db.execSQL(sentence);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            db.execSQL("DROP TABLE IF EXISTS articulos");
            db.execSQL(sentence);
        }
    }
}
