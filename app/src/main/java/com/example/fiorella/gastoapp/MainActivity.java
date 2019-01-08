package com.example.fiorella.gastoapp;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton fab_addb, fab_config;
//    TODO: cargar los gastos desde la base de datos en un listview

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab_addb = (FloatingActionButton)findViewById(R.id.fab_add);
        fab_config = (FloatingActionButton)findViewById(R.id.fab_setting);
        fab_config.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ActivityConfiguration.class));
            }
        });
        fab_addb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                startActivity(new Intent(MainActivity.this, ActivityAddExpense.class));
            }
        });
    }
}
