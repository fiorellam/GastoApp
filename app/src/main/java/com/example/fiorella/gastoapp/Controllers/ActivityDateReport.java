package com.example.fiorella.gastoapp.Controllers;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fiorella.gastoapp.Database.AdminSQLiteOpenHelper;
import com.example.fiorella.gastoapp.Model.Expense;
import com.example.fiorella.gastoapp.R;

import java.util.ArrayList;

public class ActivityDateReport extends AppCompatActivity {

    ListView listView_general;
    TextView txt_total_general;
    ArrayList<String> list_expense_string;
    ArrayList<Expense> expense_list_expense;
    EditText date1, date2;
    AdminSQLiteOpenHelper admin;
    int classification_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_report);

        date1 = (EditText)findViewById(R.id.editText);
        date2 = (EditText)findViewById(R.id.editText2);
        txt_total_general = (TextView)findViewById(R.id.txt_total_classification);
        listView_general = (ListView)findViewById(R.id.listView_date_report);
    }

    private void consultExpenseList(String date1, String date2) {
        //Abrimos base de datos en modo lectura y escritura
        admin= new AdminSQLiteOpenHelper(this, "gastoApp", null, 1);
        SQLiteDatabase dataBase = admin.getReadableDatabase();

        Expense expense = null;
        expense_list_expense = new ArrayList<Expense>();

        Cursor cursor = dataBase.rawQuery("select * from expense where dateb between" + "'"+date1 + "' AND " + "'"+date2+"'"  , null);

        while(cursor.moveToNext()){
            expense = new Expense();
            expense.set_id(cursor.getInt(0));
            expense.set_concept(cursor.getString(1));
            expense.set_amount(cursor.getDouble(2));
            expense.set_date(cursor.getString(3));

            classification_id = cursor.getInt(4);

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
                    "  " + expense_list_expense.get(i).get_concept()
                            + "                "    + expense_list_expense.get(i).get_amount());
        }
    }

    public double getTotal(String date1, String date2){
        double amount;
        admin= new AdminSQLiteOpenHelper(this, "gastoApp", null, 1);
        SQLiteDatabase db = admin.getReadableDatabase();
        Cursor c ;
        c = db.rawQuery("select sum(amount) from expense where dateb between " + "'"+date1+ "' AND " + "'"+date2+"'" , null);
        if(c.moveToFirst())
            amount = c.getInt(0);
        else
            amount = -1;
        c.close();
        return amount;
    }

    public void search(View view){
        String date1_string = date1.getText().toString();
        String date2_string = date2.getText().toString();
        consultExpenseList(date1_string, date2_string);
        if(!date1_string.equals("") && !date2_string.equals("")){
            double total = getTotal(date1_string, date2_string);
            txt_total_general.setText(String.valueOf(total));

            ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, list_expense_string);
            listView_general.setAdapter(adapter);
        }else{
            Toast.makeText(this, "Se deben ingresar las fechas", Toast.LENGTH_SHORT).show();
        }
    }
}
