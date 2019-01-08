package com.example.fiorella.gastoapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "GastosApp.db";

    private static final String TABLE_CLASSIFICATION ="create table classification(classification_id integer primary key autoincrement," +
            " name text, limitt int)";
    private static final String TABLE_EXPENSE ="create table expense(expense_id integer primary key autoincrement," +
            "concept_name text, amount real, dateb date, classification_id int," +
            " FOREIGN KEY (classification_id) references classification(classification_id))";
    private static final String TABLE_GENERAL_CONFIGURATION = "create table general_configuration(general_limit integer)";

    public AdminSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CLASSIFICATION);
        db.execSQL(TABLE_EXPENSE);
        db.execSQL(TABLE_GENERAL_CONFIGURATION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("DROP TABLE IF EXISTS classification" );
//        db.execSQL("DROP TABLE IF EXISTS expense" );
//        db.execSQL("DROP TABLE IF EXISTS general_configuration");
    }
}
