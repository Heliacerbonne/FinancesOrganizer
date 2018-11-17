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

public class IncomeList extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener  {

    private Button seeExpenses;
    private Button backIn;
    private double total;
    private TextView totalIn;
    private ListView lvIn;
    private List<MouvMoney> li = new ArrayList<>();
    private int year, month, day;
    private int fromto;
    private String from, to;
    private String theDay, theMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_income);

        //initialize from and to
        from = "1900-01-01";
        to = "2100-01-01";

        //récup db
        FinancesOrganizerDB db = new FinancesOrganizerDB(this);
        total = db.getTotalIncome();
        totalIn = (TextView) findViewById(R.id.txtIn);
        totalIn.setText("Total income : " + total + " €");

        //set listView
        lvIn = (ListView) findViewById(R.id.lvIn);
        li = db.getIncome(from, to);
        lvIn.setAdapter(new MyAdapter(this,li));

        //set listeners
        seeExpenses = (Button) findViewById(R.id.seeExpenses);
        seeExpenses.setOnClickListener(this);
        backIn = (Button) findViewById(R.id.backIn);
        backIn.setOnClickListener(this);

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
        if (R.id.fromIn == fromto) {
            ((Button) findViewById(R.id.fromIn)).setText("From : " + dateFormat.format(calendar.getTime()));

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

        else if(R.id.toIn == fromto) {
            ((Button) findViewById(R.id.toIn)).setText("To : " + dateFormat.format(calendar.getTime()));

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
        lvIn = (ListView) findViewById(R.id.lvIn);
        li = db.getIncome(from, to);
        lvIn.setAdapter(new MyAdapter(this,li));
        Log.e("truc", from + " to " + to);

    }

    @Override
    public void onClick(View v) {

        if (R.id.seeExpenses == v.getId()) {
            //change activity
            Log.e("coucou","coucou");
            Intent j = new Intent(IncomeList.this, ExpenseList.class);
            startActivity(j);
        }
        else if (R.id.backIn == v.getId()) {
            //change activity
            Log.e("ici", "ici");
            Intent k = new Intent(IncomeList.this, Dashboard.class);
            startActivity(k);
        }
    }

}
