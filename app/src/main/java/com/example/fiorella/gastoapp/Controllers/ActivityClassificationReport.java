package com.example.fiorella.gastoapp.Controllers;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fiorella.gastoapp.Database.AdminSQLiteOpenHelper;
import com.example.fiorella.gastoapp.Model.Classification;
import com.example.fiorella.gastoapp.Model.Expense;
import com.example.fiorella.gastoapp.R;

import java.util.ArrayList;

public class ActivityClassificationReport extends AppCompatActivity {

    ListView listView_classification_report;
    ArrayList<String> list_classification_string;
    ArrayList<Classification> classification_list_classification;
    TextView txt_total_classification;
    ArrayList<String> list_expense_string;
    ArrayList<Expense> expense_list_expense;
    private Spinner sp_classification;
    int classification_id;
    AdminSQLiteOpenHelper admin;
    String classification_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classification_report);

        sp_classification = (Spinner)findViewById(R.id.spinner_classification_report);
        listView_classification_report = (ListView)findViewById(R.id.listView_date_report);
        txt_total_classification = (TextView)findViewById(R.id.txt_total_classification);

        consultClassificationList();
        //conexion entre el arreglo con lo que se va a desplegar en el componente spinner
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, list_classification_string);
        sp_classification.setAdapter(adapter);

    }
    private void consultClassificationList(){
        admin = new AdminSQLiteOpenHelper(this, "gastoApp", null, 1);
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


    public void search(View view){
        String classification_string = sp_classification.getSelectedItem().toString();
        consult(classification_string);
        double total = getTotal(classification_id);
        String total_string = String.valueOf(total);
        txt_total_classification.setText(total_string);
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, list_expense_string);
        listView_classification_report.setAdapter(adapter);

    }
    public void consult(String classification_selection){
        admin= new AdminSQLiteOpenHelper(this, "gastoApp", null, 1);
        SQLiteDatabase dataBase = admin.getReadableDatabase();
//        String classification_selection = sp_classification.getSelectedItem().toString();
        try {
            Expense expense = null;
            expense_list_expense = new ArrayList<Expense>();
            Cursor cursor_classification = dataBase.rawQuery("select * from classification where name =" + "'" + classification_selection + "'", null);
            while (cursor_classification.moveToNext()) {
                classification_id = cursor_classification.getInt(0);
                classification_name = cursor_classification.getString(1);

                Cursor cursor_expense = dataBase.rawQuery("select * from expense where classification_id=" + classification_id, null);
                while (cursor_expense.moveToNext()) {
                    expense = new Expense();
                    expense.set_id(cursor_expense.getInt(0));
                    expense.set_concept(cursor_expense.getString(1));
                    expense.set_amount(cursor_expense.getDouble(2));
                    expense.set_date(cursor_expense.getString(3));
                    expense.set_id(cursor_expense.getInt(4));
                    expense_list_expense.add(expense);

                }

            }

        }
        catch(SQLException sql){
            sql.printStackTrace();
            Toast.makeText(this, "No existen gastos en esta clasificacion", Toast.LENGTH_LONG).show();
            }

//            Toast.makeText(this, "METODO CONSULT", Toast.LENGTH_LONG).show();
            getListExpense();

        }

    private void getListExpense(){
        list_expense_string = new ArrayList<String>();

        for(int i = 0; i< expense_list_expense.size(); i++){
            list_expense_string.add(
                    "  " + expense_list_expense.get(i).get_concept()
                            + "                "    + expense_list_expense.get(i).get_amount()
//                            + string_class.get(i)
//                            + "  " +expense_list_expense.get(i).get_date()
            );
//            System.out.println("LALALALALALALALA" + list_expense_string.get(i));
            Log.i("AYUDAAA", "eindfceabnfuaer");
            System.out.println("QUE PEDOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO1!!!!!!!!!!!!!");
        }
    }

    public double getTotal(int classification_id){
        double amount;
        admin= new AdminSQLiteOpenHelper(this, "gastoApp", null, 1);
        SQLiteDatabase db = admin.getReadableDatabase();
        Cursor c ;
        c = db.rawQuery("select sum(amount) from expense where classification_id=" + classification_id , null);
        if(c.moveToFirst())
            amount = c.getInt(0);
        else
            amount = -1;
        c.close();
        return amount;
    }


}
