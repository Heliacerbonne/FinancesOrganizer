package com.utt.financesorganizer;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class ExpenseList extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private Button seeIncomes;
    private Button backEx;
    private double total;
    private TextView totalEx;
    private ListView lvEx;
    private List<MouvMoney> le = new ArrayList<>();
    private int year, month, day;
    private int fromto;
    private String from, to;
    private String theDay, theMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_expense);

        //initialize from and to
        from = "1900-01-01";
        to = "2100-01-01";

        //récup db
        FinancesOrganizerDB db = new FinancesOrganizerDB(this);
        total = db.getTotalExpense();
        totalEx = (TextView) findViewById(R.id.txtEx);
        totalEx.setText("Total expense : " + total + " €");

        //set listView
        lvEx = (ListView) findViewById(R.id.lvEx);
        le = db.getExpense(from, to);
        lvEx.setAdapter(new MyAdapter(this,le));


        //set listeners
        seeIncomes = (Button) findViewById(R.id.seeIncomes);
        seeIncomes.setOnClickListener(this);
        backEx = (Button) findViewById(R.id.backEx);
        backEx.setOnClickListener(this);

    }

    //Date picker frag
    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
        fromto = v.getId();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        Calendar cal = new GregorianCalendar(year, month, day);
        this.year = year;
        this.month = month;
        this.day = day;
        setDate(cal);
    }

    /**
     * To set date on Button
     * @param calendar
     */
    private void setDate(final Calendar calendar) {
        final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
        if (R.id.fromEx == fromto) {
            ((Button) findViewById(R.id.fromEx)).setText("From : " + dateFormat.format(calendar.getTime()));

            //récupérer la date
            if(day < 10){
                theDay = "0" + day;
            }
            else{
                theDay = day + "";
            }

            month = month + 1;

            if(month < 10){
                theMonth = "0" + month;
            }
            else{
                theMonth = month + "";
            }

            from = year + "-" + theMonth + "-" + theDay ;
        }

        else if(R.id.toEx == fromto) {
            ((Button) findViewById(R.id.toEx)).setText("To : " + dateFormat.format(calendar.getTime()));

            //récupérer la date
            if(day < 10){
                theDay = "0" + day;
            }
            else{
                theDay = day + "";
            }

            month = month + 1;

            if(month < 10){
                theMonth = "0" + month;
            }
            else{
                theMonth = month + "";
            }

            to = year + "-" + theMonth + "-" + theDay ;

        }

        FinancesOrganizerDB db = new FinancesOrganizerDB(this);
        lvEx = (ListView) findViewById(R.id.lvEx);
        le = db.getExpense(from, to);
        lvEx.setAdapter(new MyAdapter(this,le));
        Log.e("truc", from + " to " + to);

    }

    @Override
    public void onClick(View v) {

        if (R.id.seeIncomes == v.getId()) {
            //change activity
            Log.e("coucou","coucou");
            Intent j = new Intent(ExpenseList.this, IncomeList.class);
            startActivity(j);
        }
        else if (R.id.backEx == v.getId()) {
            //change activity
            Log.e("ici", "ici");
            Intent k = new Intent(ExpenseList.this, Dashboard.class);
            startActivity(k);
        }
    }

}
