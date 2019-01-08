package com.example.fiorella.gastoapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ActivityAddExpense extends AppCompatActivity {

    private EditText txt_concept, txt_amount;
    private Spinner sp_classification;
    private TextView tv1, tv2, tv3, tv4, tv5;
    ArrayList<String> list_classification;
    ArrayList<Classification> classification_list;

    //    private TextView tv_date;
    String [] options = {"Escuela", "Comida", "Cena", "Desayuno"};
//    TODO: cargar spinner desde la base de datos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        txt_concept = (EditText)findViewById(R.id.txt_concept);
        txt_amount = (EditText)findViewById(R.id.txt_amount);
        sp_classification = (Spinner)findViewById(R.id.sp_classification);
        tv1 = (TextView)findViewById(R.id.tv_id);
        tv2 = (TextView)findViewById(R.id.tv_concept);
        tv3 = (TextView)findViewById(R.id.tv_amount);
        tv4 = (TextView)findViewById(R.id.tv_classification);
        tv5 = (TextView)findViewById(R.id.tv_date);
//        tv_date = (TextView)findViewById(R.id.tv_date);

        consultClassificationList();


        //conexion entre el arreglo con lo que se va a desplegar en el componente spinner
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this, R.layout.support_simple_spinner_dropdown_item, list_classification);
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
            System.out.println("concepto: " + concept_string + "\n" + amount_string + "\n" + classification_selection + "\n" + date + "\n");
            //            TODO: Agregar nuevo gasto a bdSQLite y un toast con mensaje: "Se ha guardado nuevo gasto"
            try{
                ContentValues register_expense = new ContentValues();
                register_expense.put("concept_name", concept_string);
                register_expense.put("amount", amount_string);
                register_expense.put("dateb", date);
                register_expense.put("classification_id", classification_selection);

                dataBase.insert("expense", null, register_expense);

                dataBase.close();

                Toast.makeText(this, "Gasto Agregado", Toast.LENGTH_SHORT).show();
            }catch (SQLException sqlE){
                sqlE.printStackTrace();
            }
        }
        else{
            Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
        }


    }

//    public void showCalendar(View view){
//        Intent intent = new Intent(Activity_amount_view.this, ActivityCalendar.class);
//        startActivity(intent);
//    }

    public String setDate(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        String fecha = dateFormat.format(date);
        System.out.println(fecha);
        return fecha;
    }

    private void consultClassificationList(){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "gastoApp", null, 1);
        //Abrimos base de datos en modo lectura y escritura
        SQLiteDatabase dataBase = admin.getWritableDatabase();

        Classification classification = null;
        classification_list = new ArrayList<Classification>();

        Cursor cursor = dataBase.rawQuery("select * from classification", null);
        while(cursor.moveToNext()){
            classification = new Classification();
            classification.setClassification_id(cursor.getInt(0));
            classification.setNamee(cursor.getString(1));
            classification.setLimitt(cursor.getInt(2));

            classification_list.add(classification);

            Log.i("id", classification.getClassification_id().toString());
            Log.i("Nombre", classification.getNamee());
            Log.i("Limite", classification.getLimitt().toString());
        }
        getList();

    }

    private void getList(){
        list_classification = new ArrayList<String>();
        list_classification.add("Seleccione");

        for(int i=0; i<classification_list.size(); i++){
            list_classification.add(classification_list.get(i).getNamee());
        }
    }
}
