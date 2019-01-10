package com.example.fiorella.gastoapp.Controllers;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fiorella.gastoapp.Database.AdminSQLiteOpenHelper;
import com.example.fiorella.gastoapp.Model.Classification;
import com.example.fiorella.gastoapp.R;

public class ActivityEditClassification extends AppCompatActivity {

    private EditText txt_classification, txt_classification_limit;
    AdminSQLiteOpenHelper admin;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_classification);

        txt_classification = (EditText)findViewById(R.id.txt_classification);
        txt_classification_limit =(EditText)findViewById(R.id.txt_classification_limit);

        Classification classification = (Classification) getIntent().getExtras().getSerializable("classification");
        id = classification.getClassification_id();
        txt_classification.setText(classification.getNamee());
        txt_classification_limit.setText(classification.getLimitt().toString());
    }

    public void updateClassification(View view){
        admin = new AdminSQLiteOpenHelper(this, "gastoApp", null, 1);
        SQLiteDatabase dataBase = admin.getWritableDatabase();
        String string_classification = txt_classification.getText().toString();
        String string_limit = txt_classification_limit.getText().toString();

        int int_limit = Integer.parseInt(string_limit);

        Cursor consult = dataBase.rawQuery("select * from classification where classification_id=" + id, null);
        if(consult.moveToFirst()){

            ContentValues modification = new ContentValues();
            modification.put("name", string_classification);
            modification.put("limitt", int_limit);

            dataBase.update("classification", modification, "classification_id=" + id, null);

            Toast.makeText(this, "Los datos de la clasificacion se han actualizado", Toast.LENGTH_SHORT).show();

        }
        dataBase.close();
        Intent intent = new Intent(ActivityEditClassification.this, ActivityExpensesClassification.class);
        startActivity(intent);
    }
    public void deleteClassification(View view){
        admin = new AdminSQLiteOpenHelper(this, "gastoApp", null, 1);
        SQLiteDatabase dataBase = admin.getWritableDatabase();
        dataBase.delete("classification", "classification_id="+ id,null);
        dataBase.close();

        Toast.makeText(this, "Clasificacion Eliminada", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(ActivityEditClassification.this, ActivityExpensesClassification.class));
    }
    @Override
    public void onBackPressed(){

    }
}
