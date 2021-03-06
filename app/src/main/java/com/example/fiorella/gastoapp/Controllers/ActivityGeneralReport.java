package com.example.fiorella.gastoapp.Controllers;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.fiorella.gastoapp.Database.AdminSQLiteOpenHelper;
import com.example.fiorella.gastoapp.Model.Expense;
import com.example.fiorella.gastoapp.R;

import java.util.ArrayList;

public class ActivityGeneralReport extends AppCompatActivity {

    ListView listView_general;
    TextView txt_total_general;
    ArrayList<String> list_expense_string;
    ArrayList<Expense> expense_list_expense;
    Double total_expense;
    AdminSQLiteOpenHelper admin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_general_report);

        consultExpenseList();

        listView_general = (ListView)findViewById(R.id.listView_general);
        txt_total_general = (TextView)findViewById(R.id.txt_total_classification);


        total_expense = getTotal();

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, list_expense_string);
        listView_general.setAdapter(adapter);
        txt_total_general.setText(String.valueOf(total_expense));
    }


    private void consultExpenseList() {
        //Abrimos base de datos en modo lectura y escritura
        admin= new AdminSQLiteOpenHelper(this, "gastoApp", null, 1);
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

//            Log.i("id", String.valueOf(expense.get_id()));
//            Log.i("Nombre",expense.get_concept());
//            Log.i("Monto", String.valueOf(expense.get_amount()));
//            Log.i("Fecha", expense.get_date());
        }
        getList();
        dataBase.close();
    }

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
     * Metodo para obtener el total general gastado
     * @return double gasto total
     */
    public double getTotal(){
        double amount;
        admin= new AdminSQLiteOpenHelper(this, "gastoApp", null, 1);
        SQLiteDatabase db = admin.getReadableDatabase();
        Cursor c ;
        c = db.rawQuery("select sum(amount) from expense", null);
        if(c.moveToFirst())
            amount = c.getInt(0);
        else
            amount = -1;
        c.close();
        return amount;
    }

}
