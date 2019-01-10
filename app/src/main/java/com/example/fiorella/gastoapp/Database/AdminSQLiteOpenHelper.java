package com.example.fiorella.gastoapp.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Clase para administrar la base de datos que se creara
 */

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "GastosApp.db";


    private static final String TABLE_CLASSIFICATION ="create table classification(classification_id integer primary key autoincrement not null," +
            " name text not null, limitt int not null, actual_limit int not null)";
    private static final String TABLE_EXPENSE ="create table expense(expense_id integer primary key autoincrement not null,\n" +
            "           concept_name text not null, amount real not null, dateb date not null, classification_id int not null,\n" +
            "            FOREIGN KEY (classification_id) references classification(classification_id))";
    private static final String TABLE_GENERAL_CONFIGURATION = "create table general_configuration(general_limit integer)";

    /**
     * Inserciones con las que iniciara la aplicacion
     */
    private static final String INSERT1 = "insert into classification(name, limitt, actual_limit)values('Deporte',7,0)";
    private static final String INSERT2 = "insert into classification(name, limitt, actual_limit)values('Comida',3,0)";
    private static final String INSERT3 = "insert into classification(name, limitt, actual_limit)values('Gasolina',2,0)";
    private static final String INSERT4 = "insert into classification(name, limitt, actual_limit)values('Ropa',1,0)";
    private static final String INSERT5 = "insert into classification(name, limitt, actual_limit)values('Entretenimiento',4,0)";
    private static final String INSERT6 = "insert into classification(name, limitt, actual_limit)values('Hogar',5,0)";
    private static final String INSERTLIMIT = "insert into general_configuration (general_limit) values (150)";



    public AdminSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /**
     * Metodo que se ejecutara automaticamente cuando la base de datos no existe
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CLASSIFICATION);
        db.execSQL(TABLE_EXPENSE);
        db.execSQL(TABLE_GENERAL_CONFIGURATION);

        db.execSQL(INSERT1);
        db.execSQL(INSERT2);
        db.execSQL(INSERT3);
        db.execSQL(INSERT4);
        db.execSQL(INSERT5);
        db.execSQL(INSERT6);

        db.execSQL(INSERTLIMIT);
    }

    //Metodo que se ejecuta cuando se detecta que la version de la base de datos cambio
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("DROP TABLE IF EXISTS classification" );
//        db.execSQL("DROP TABLE IF EXISTS expense" );
//        db.execSQL("DROP TABLE IF EXISTS general_configuration");
    }
}
