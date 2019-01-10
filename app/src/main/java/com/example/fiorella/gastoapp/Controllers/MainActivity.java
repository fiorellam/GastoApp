package com.example.fiorella.gastoapp.Controllers;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.fiorella.gastoapp.Database.AdminSQLiteOpenHelper;
import com.example.fiorella.gastoapp.Model.Expense;
import com.example.fiorella.gastoapp.R;

import java.util.ArrayList;

/**
 * Controlador de layout ActivityMain
 */

public class MainActivity extends AppCompatActivity {

    FloatingActionButton fab_addb, fab_config;
    ListView listView_espenses;
    ArrayList<String> list_expense_string;
    ArrayList<Expense> expense_list_expense;
    AdminSQLiteOpenHelper admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView_espenses = (ListView)findViewById(R.id.listView_expenses);

        consultExpenseList();

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

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, list_expense_string);
        listView_espenses.setAdapter(adapter);

    }

    /**
     * Metodo que hace consulta a la base de datos y va asignando sus valores
     * al Arraylist expense_list_expense
     */
    private void consultExpenseList() {
        admin = new AdminSQLiteOpenHelper(this, "gastoApp", null, 1);
        //Abrimos base de datos en modo lectura y escritura
        SQLiteDatabase dataBase = admin.getReadableDatabase();

        Expense expense = null;
        expense_list_expense = new ArrayList<Expense>();

        Cursor cursor = dataBase.rawQuery("select * from expense", null);
        while(cursor.moveToNext()){
            expense = new Expense();
            expense.set_id(cursor.getInt(0));
            expense.set_concept(cursor.getString(1));
            expense.set_amount(cursor.getDouble(2));
            expense.set_date(cursor.getString(3));

            expense_list_expense.add(expense);
        }
        dataBase.close();
        getList();
    }

    /**
     * Metodo que recorre el ArrayList expense_list_expense y asigna
     * valores a Arraylist list_expense_string para poder llenar listView
     */
    private void getList(){
        list_expense_string = new ArrayList<String>();

        for(int i = 0; i< expense_list_expense.size(); i++){
            list_expense_string.add(
                            "  " + expense_list_expense.get(i).get_concept()
                            + "                "    + expense_list_expense.get(i).get_amount()
            );
        }
    }

    /**
     * Metodo que va a ActivityReports
     * @param view El parametro view define el componente recibido
     */
    public void goToReports(View view){
        startActivity(new Intent(MainActivity.this, ActivityReports.class));
    }
}
