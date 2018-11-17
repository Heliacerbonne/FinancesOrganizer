package com.utt.financesorganizer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Dashboard extends AppCompatActivity implements View.OnClickListener {

    protected FinancesOrganizerDB db;
    protected String firstName;
    protected String surName;
    protected TextView helloTxt;
    protected Button newIncome;
    protected Button newExpense;
    protected Button seeIn;
    protected Button seeEx;
    protected TextView totalTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        //if it not exist, create db
        db = new FinancesOrganizerDB(this);

        //show first and last name in dashboard
        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.file), Context.MODE_PRIVATE);
        firstName = sharedPref.getString("FirstName", "default value");
        surName = sharedPref.getString("LastName", "default value");
        helloTxt = (TextView) findViewById(R.id.helloTxt);
        helloTxt.setText("Hello, " + firstName + " " + surName + "!");

        //show total money
        double totalIn = db.getTotalIncome();
        double totalEx = db.getTotalExpense();
        double totalDep = totalIn - totalEx;
        totalTxt = (TextView) findViewById(R.id.totalTxt);
        totalTxt.setText("Total : " + totalDep + " €");


        //set listeners
        newIncome = (Button) findViewById(R.id.incomeBtn);
        newIncome.setOnClickListener(this);
        newExpense = (Button) findViewById(R.id.expenseBtn);
        newExpense.setOnClickListener(this);
        seeIn = (Button) findViewById(R.id.seeIn);
        seeIn.setOnClickListener(this);
        seeEx = (Button) findViewById(R.id.seeEx);
        seeEx.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Log.e( "test", v.getId() + " " + R.id.expenseBtn + " " + R.id.incomeBtn);

        if (R.id.expenseBtn == v.getId()) {
            //change activity
            Log.e("coucou","coucou");
            Intent j = new Intent(Dashboard.this, ExpenseForm.class);
            startActivity(j);
        }
        else if (R.id.incomeBtn == v.getId()) {
            //change activity
            Log.e("ici", "ici");
            Intent k = new Intent(Dashboard.this, IncomeForm.class);
            startActivity(k);
        }
        else if (R.id.seeEx == v.getId()) {
            //change activity
            Log.e("la", "la");
            Intent k = new Intent(Dashboard.this, ExpenseList.class);
            startActivity(k);
        }
        else if (R.id.seeIn == v.getId()) {
            //change activity
            Log.e("la bas", "la bas");
            Intent k = new Intent(Dashboard.this, IncomeList.class);
            startActivity(k);
        }
        else{
            Log.e("fail", "fail");
        }
    }
}
