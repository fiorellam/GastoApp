package com.example.fiorella.gastoapp;

import android.content.ContentValues;
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



        if(!concept_string.equals("") && !amount_string.equals("") && !classification_selection.equals("")){
            try{
                double amount_double = Double.parseDouble(amount_string);
                ContentValues register_expense = new ContentValues();
                register_expense.put("concept_name", concept_string);
                register_expense.put("amount", amount_double);
                register_expense.put("dateb", date);
                register_expense.put("classification_id", classification_selection);

                dataBase.insert("expense", null, register_expense);

                dataBase.close();
                System.out.println("concepto: " + concept_string + "\n" + amount_string + "\n" + classification_selection + "\n" + date + "\n");

                Toast.makeText(this, "Gasto Agregado", Toast.LENGTH_SHORT).show();
            }catch (SQLException sqlE){
                sqlE.printStackTrace();
            }
        }
        else{
            Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
        }


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
