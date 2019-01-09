package com.example.fiorella.gastoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ActivityConfiguration extends AppCompatActivity {

    ListView listView_settings;
    String[] options = {"Categorias de Gastos","Limite General"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item, options);

        listView_settings = (ListView)findViewById(R.id.listView_settings);
        listView_settings.setAdapter(adapter);

        listView_settings.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    startActivity(new Intent(ActivityConfiguration.this, ActivityExpensesClassification.class));
                }else if(position == 1){
                    startActivity(new Intent(ActivityConfiguration.this, ActivityGeneralLimit.class ));
                }
            }
        });
    }

    @Override
    public void onBackPressed(){
        startActivity(new Intent(ActivityConfiguration.this, MainActivity.class));
    }
}
