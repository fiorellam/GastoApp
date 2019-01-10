package com.example.fiorella.gastoapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ActivityAddExpense extends AppCompatActivity {

    private EditText txt_concept, txt_amount;
    private Spinner sp_classification;
    ArrayList<String> list_classification_string;
    ArrayList<Classification> classification_list_classification;
    int actual_limit, classification_limi, classification_id;


    AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "gastoApp", null, 1);

//    String [] options = {"Escuela", "Comida", "Cena", "Desayuno"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        txt_concept = (EditText)findViewById(R.id.txt_concept);
        txt_amount = (EditText)findViewById(R.id.txt_amount);
        sp_classification = (Spinner)findViewById(R.id.sp_classification);

        consultClassificationList();

        //conexion entre el arreglo con lo que se va a desplegar en el componente spinner
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, list_classification_string);
        sp_classification.setAdapter(adapter);
    }


    public void addNewExpense(View view){

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "gastoApp", null, 1);
        //Abrimos base de datos en modo lectura y escritura
        SQLiteDatabase dataBase = admin.getWritableDatabase();

        String concept_string = txt_concept.getText().toString();
        String amount_string = txt_amount.getText().toString();
        String classification_selection = sp_classification.getSelectedItem().toString();
        String date = setDate();

        Integer classification_id = 0;
        String classification_name;


        if(!concept_string.equals("") && !amount_string.equals("") && !classification_selection.equals("")){
            try{

                Cursor cursor_expense = dataBase.rawQuery("select * from classification where name =" + "'"+ classification_selection + "'", null);
                while(cursor_expense.moveToNext()){
                    classification_id = cursor_expense.getInt(0);
                    classification_name = cursor_expense.getString(1);

                    classification_limi = cursor_expense.getInt(2);
                    actual_limit = cursor_expense.getInt(3);

                    Log.i("id", classification_id.toString());
                    Log.i("Nombre", classification_name);
                    Log.i("Limite", String.valueOf(cursor_expense.getInt(2)));
                    Log.i("Limite Actual", String.valueOf(cursor_expense.getInt(3)));
                }
                getList();

                if(actual_limit<classification_limi){
                    double amount_double = Double.parseDouble(amount_string);
                    ContentValues register_expense = new ContentValues();
                    register_expense.put("concept_name", concept_string);
                    register_expense.put("amount", amount_double);
                    register_expense.put("dateb", date);
                    register_expense.put("classification_id", classification_id);

                    System.out.println("CLASIFICACION ID DESPUES " + classification_id);

                    dataBase.insert("expense", null, register_expense);


                    Cursor consult_classification = dataBase.rawQuery("select * from classification where classification_id=" +classification_id, null);

                    if(consult_classification.moveToFirst()){
                        actual_limit++;

                        ContentValues modification = new ContentValues();
                        modification.put("actual_limit", actual_limit);

                        dataBase.update("classification", modification, "classification_id=" + classification_id, null);

                        Toast.makeText(this, "Los datos de la clasificacion se han actualizado", Toast.LENGTH_SHORT).show();
                    }

                    dataBase.close();
                    System.out.println("concepto: " + concept_string + "\n" + amount_string + "\n" + classification_selection + "\n" + date + "\n");

                    Toast.makeText(this, "Gasto Agregado", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(ActivityAddExpense.this, MainActivity.class);
                    startActivity(intent);
                }
                dataBase.close();

            }catch (SQLException sqlE){
                sqlE.printStackTrace();
            }
        }
        else{
            Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
        }
        dataBase.close();


    }
    public String setDate(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        String fecha = dateFormat.format(date);
        return fecha;
    }

    private void consultClassificationList(){
//        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "gastoApp", null, 1);
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

            classification_id = cursor.getInt(0);

            Log.i("id", classification.getClassification_id().toString());
            Log.i("Nombre", classification.getNamee());
            Log.i("Limite", classification.getLimitt().toString());
        }
        getList();
        dataBase.close();
    }

    private void getList(){
        list_classification_string = new ArrayList<String>();

        for(int i = 0; i< classification_list_classification.size(); i++){
            list_classification_string.add(classification_list_classification.get(i).getNamee());
//            System.out.println("LALALALALALALALA" + list_classification_string.get(i));
        }
    }

    @Override
    public void onBackPressed(){
        startActivity(new Intent(ActivityAddExpense.this, MainActivity.class));
    }

}
