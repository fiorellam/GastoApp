package com.example.fiorella.gastoapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.SQLException;

public class ActivityAddClassification extends AppCompatActivity {

    private EditText txt_classification, txt_classification_limit;
    int classification_limit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_classification);

        txt_classification = (EditText)findViewById(R.id.txt_classification);
        txt_classification_limit =(EditText)findViewById(R.id.txt_classification_limit);
    }

    public void saveClassification(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "gastoApp", null, 1);
        SQLiteDatabase dataBase = admin.getWritableDatabase();

        String string_classification = txt_classification.getText().toString();
        String string_limit = txt_classification_limit.getText().toString();



        if(!string_classification.equals("") && !string_limit.equals("")){
            System.out.println(string_classification + "\n" + string_limit);

            classification_limit = Integer.parseInt(string_limit);

            ContentValues register_classification = new ContentValues();
            register_classification.put("name", string_classification);
            register_classification.put("limitt", classification_limit);

            dataBase.insert("classification", null, register_classification);
            dataBase.close();

            Toast.makeText(this, "Clasificación Agregada", Toast.LENGTH_SHORT).show();

            startActivity(new Intent(ActivityAddClassification.this, ActivityExpensesCategory.class));

        }else{
            Toast.makeText(this, "Debes ingresar todos los datos", Toast.LENGTH_SHORT).show();
        }
    }
}
