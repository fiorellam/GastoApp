package com.example.fiorella.gastoapp.Controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.fiorella.gastoapp.R;

public class ActivityReports extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);
    }

    public void goToGeneralReport(View view){
        startActivity(new Intent(ActivityReports.this, ActivityGeneralReport.class));
    }
    public void goToClassificationReport(View view){
        startActivity(new Intent(ActivityReports.this, ActivityClassificationReport.class));
    }
    public void goToDateReport(View view){
        startActivity(new Intent(ActivityReports.this, ActivityDateReport.class));
    }
}
