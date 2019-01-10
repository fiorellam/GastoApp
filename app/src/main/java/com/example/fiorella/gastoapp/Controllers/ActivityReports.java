package com.example.fiorella.gastoapp.Controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.fiorella.gastoapp.R;

/**
 * Controlador de layout activity_reports
 */


public class ActivityReports extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);
    }

    /**
     * Metodos que inician su Actividad correspondiente
     * @param view
     */
    public void goToGeneralReport(View view){
        startActivity(new Intent(ActivityReports.this, ActivityGeneralReport.class));
    }
    public void goToClassificationReport(View view){
        startActivity(new Intent(ActivityReports.this, ActivityClassificationReport.class));
    }
    public void goToDateReport(View view){
        startActivity(new Intent(ActivityReports.this, ActivityDateReport.class));
    }

    @Override
    public void onBackPressed(){
        startActivity(new Intent(ActivityReports.this, MainActivity.class));
    }
}
