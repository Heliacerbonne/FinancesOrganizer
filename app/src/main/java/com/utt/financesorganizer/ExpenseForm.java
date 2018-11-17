package com.utt.financesorganizer;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class ExpenseForm extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    protected Button okExpense;
    private EmptyTextWatcher tiwatch = new EmptyTextWatcher();
    private EmptyTextWatcher aiwatch = new EmptyTextWatcher();
    private EditText titleExpense;
    private EditText amountExpense;
    private int year, month, day;
    private String theMonth, theDay;
    private String dateSql, titleSql, catSql, amountSql;

    private final class EmptyTextWatcher implements TextWatcher {
        protected boolean empty = true;

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            this.empty = s.toString().trim().equals("");
            updateInputs();
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after)  {}

        @Override
        public void afterTextChanged(Editable s) {}
    }

    protected void updateInputs() {
        okExpense.setEnabled(!tiwatch.empty && !aiwatch.empty);
        if (okExpense.isEnabled()){
            okExpense.setBackgroundColor(0xFFAA66CC);
        }
        else{
            okExpense.setBackgroundColor(0xFFB1A0BA);
        }
    }

    //Date picker frag
    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
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
     * To set date on TextView
     * @param calendar
     */
    private void setDate(final Calendar calendar) {
        final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
        ((TextView) findViewById(R.id.dateShow2)).setText(dateFormat.format(calendar.getTime()));
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.food:
                if (checked)
                    catSql = "2";
                break;
            case R.id.travel:
                if (checked)
                    catSql = "3";
                break;
            case R.id.leisure:
                if (checked)
                    catSql = "4";
                break;
            case R.id.accommodation:
                if (checked)
                    catSql = "5";
                break;
            case R.id.other_expense:
                if (checked)
                    catSql = "6";
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expense_form);

        okExpense = (Button) findViewById(R.id.okExpense);
        okExpense.setEnabled(false);
        okExpense.setOnClickListener(this);
        okExpense.setBackgroundColor(0xFFB1A0BA);

        titleExpense = (EditText) findViewById(R.id.titleExpense);
        amountExpense = (EditText) findViewById(R.id.amountExpense);
        titleExpense.addTextChangedListener(tiwatch);
        amountExpense.addTextChangedListener(aiwatch);

    }

    @Override
    public void onClick(View v) {
        if (R.id.okExpense == v.getId()) {

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

            dateSql = year + "-" + theMonth + "-" + theDay ;

            //Log.e("test", dateSql);

            //récupérer le Title
            titleSql = titleExpense.getText().toString();

            //récupérer le Montant
            amountSql = amountExpense.getText().toString();

            //récupérer la catégorie
            Log.e("test" , catSql);

            //insert sql
            FinancesOrganizerDB db = new FinancesOrganizerDB(this);
            db.insertExpense(titleSql, dateSql, catSql, amountSql);


            //change activity
            Intent k = new Intent(ExpenseForm.this, Dashboard.class);
            startActivity(k);
        }
    }
}
