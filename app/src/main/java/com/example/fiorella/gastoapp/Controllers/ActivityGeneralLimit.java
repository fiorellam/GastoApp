package com.example.fiorella.gastoapp.Controllers;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fiorella.gastoapp.Database.AdminSQLiteOpenHelper;
import com.example.fiorella.gastoapp.R;

public class ActivityGeneralLimit extends AppCompatActivity {

    private EditText txt_limit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_limit);

        txt_limit = (EditText)findViewById(R.id.txt_limit);
    }


    /**
     * Metodo para guardar el limite general de gastos
     * @param view
     */
    public void saveGeneralLimit(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "gastoApp", null, 1);
        SQLiteDatabase dataBase = admin.getWritableDatabase();

        String string_limit = txt_limit.getText().toString();
        if(!string_limit.equals("")){
            int int_limit = Integer.parseInt(string_limit);

            ContentValues limitModification = new ContentValues();
            limitModification.put("general_limit", int_limit);

            dataBase.update("general_configuration", limitModification,"general_limit=" + int_limit, null);
            Toast.makeText(this, "El limite de gastos ha sido cambiado", Toast.LENGTH_SHORT).show();
            dataBase.close();
            startActivity(new Intent(ActivityGeneralLimit.this, ActivityConfiguration.class));
        }else{
            Toast.makeText(this, "Debes llenar el campo", Toast.LENGTH_SHORT).show();
        }
        dataBase.close();
    }
    @Override
    public void onBackPressed(){
        startActivity(new Intent(ActivityGeneralLimit.this, ActivityConfiguration.class));
    }
}
