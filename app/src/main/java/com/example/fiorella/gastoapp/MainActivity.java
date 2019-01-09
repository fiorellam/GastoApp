package com.example.fiorella.gastoapp;

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

import java.util.ArrayList;

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

        admin = new AdminSQLiteOpenHelper(this, "gastoApp", null, 1);

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

    private void consultExpenseList() {
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

            Log.i("id", String.valueOf(expense.get_id()));
            Log.i("Nombre",expense.get_concept());
            Log.i("Monto", String.valueOf(expense.get_amount()));
            Log.i("Fecha", expense.get_date());
        }
        getList();
    }

    private void getList(){
        list_expense_string = new ArrayList<String>();

        for(int i = 0; i< expense_list_expense.size(); i++){
            list_expense_string.add(
//                    "id: " + expense_list_expense.get(i).get_id() +
                            "  " + expense_list_expense.get(i).get_concept()
                            + "                "    + expense_list_expense.get(i).get_amount()
//                            + "  " +expense_list_expense.get(i).get_date()
            );
//            System.out.println("LALALALALALALALA" + list_expense_string.get(i));
        }
    }
}
