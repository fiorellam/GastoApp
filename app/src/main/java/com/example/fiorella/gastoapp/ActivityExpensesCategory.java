package com.example.fiorella.gastoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ActivityExpensesCategory extends AppCompatActivity {

    ListView listView_settings;
    String [] options = {"Escuela", "Comida", "Cena", "Desayuno"};
//    TODO: Cargar spinner desde la base de datos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses_category);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item, options);

        listView_settings = (ListView)findViewById(R.id.listView_expenses_category);
        listView_settings.setAdapter(adapter);

//        listView_settings.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//        }
    }
    public void addNewClassification(View view){
        Intent intent = new Intent(ActivityExpensesCategory.this, ActivityAddClassification.class );
        startActivity(intent);
    }
}
