package com.example.fiorella.gastoapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ActivityExpensesCategory extends AppCompatActivity {

    ListView listView_settings;
//    String [] options = {"Escuela", "Comida", "Cena", "Desayuno"};
    ArrayList<String> list_classification_string;
    ArrayList<Classification> classification_list_classification;
    AdminSQLiteOpenHelper admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses_category);

        admin = new AdminSQLiteOpenHelper(this, "gastoApp", null, 1);

        listView_settings = (ListView)findViewById(R.id.listView_expenses_category);
        consultClassificationList();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item, list_classification_string);


        listView_settings.setAdapter(adapter);


    }
    public void addNewClassification(View view){
        Intent intent = new Intent(ActivityExpensesCategory.this, ActivityAddClassification.class );
        startActivity(intent);
    }

    private void consultClassificationList(){
        //Abrimos base de datos en modo lectura y escritura
        SQLiteDatabase dataBase = admin.getReadableDatabase();

        Classification classification = null;
        classification_list_classification = new ArrayList<Classification>();

        Cursor cursor = dataBase.rawQuery("select * from classification", null);
        while(cursor.moveToNext()){
            classification = new Classification();
            classification.setClassification_id(cursor.getInt(0));
            classification.setNamee(cursor.getString(1));
            classification.setLimitt(cursor.getInt(2));

            classification_list_classification.add(classification);

            Log.i("id", classification.getClassification_id().toString());
            Log.i("Nombre", classification.getNamee());
            Log.i("Limite", classification.getLimitt().toString());
        }
        getList();
    }

    private void getList(){
        list_classification_string = new ArrayList<String>();

        for(int i = 0; i< classification_list_classification.size(); i++){
            list_classification_string.add(classification_list_classification.get(i).getNamee());
            System.out.println("LALALALALALALALA" + list_classification_string.get(i));
        }
    }
}
