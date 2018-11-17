package com.utt.financesorganizer;

import android.app.DatePickerDialog;
import android.content.Intent;
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


public class IncomeForm extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    protected Button okIncome;
    private EmptyTextWatcher tiwatch = new EmptyTextWatcher();
    private EmptyTextWatcher aiwatch = new EmptyTextWatcher();
    private EditText titleIncome;
    private EditText amountIncome;
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
       okIncome.setEnabled(!tiwatch.empty && !aiwatch.empty);
        if (okIncome.isEnabled()){
            okIncome.setBackgroundColor(0xFFAA66CC);
        }
        else{
            okIncome.setBackgroundColor(0xFFB1A0BA);
        }
    }

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
        ((TextView) findViewById(R.id.dateShow)).setText(dateFormat.format(calendar.getTime()));
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.salary:
                if (checked)
                    catSql = "0";
                    break;
            case R.id.other_income:
                if (checked)
                    catSql = "1";
                    break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.income_form);

        okIncome = (Button) findViewById(R.id.okIncome);
        okIncome.setEnabled(false);
        okIncome.setBackgroundColor(0xFFB1A0BA);
        okIncome.setOnClickListener(this);

        titleIncome = (EditText) findViewById(R.id.titleIncome);
        amountIncome = (EditText) findViewById(R.id.amountIncome);
        titleIncome.addTextChangedListener(tiwatch);
        amountIncome.addTextChangedListener(aiwatch);

    }

    @Override
    public void onClick(View v) {
        if (R.id.okIncome == v.getId()) {


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
            titleSql = titleIncome.getText().toString();

            //récupérer le Montant
            amountSql = amountIncome.getText().toString();

            //récupérer la catégorie
            Log.e("test" , catSql);

            //insert sql
            FinancesOrganizerDB db = new FinancesOrganizerDB(this);
            db.insertIncome(titleSql, dateSql, catSql, amountSql);


            //change activity
            Intent k = new Intent(IncomeForm.this, Dashboard.class);
            startActivity(k);
        }
    }
}
