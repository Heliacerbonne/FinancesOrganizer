package com.utt.financesorganizer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EmptyTextWatcher fnwatch = new EmptyTextWatcher();
    private EmptyTextWatcher lnwatch = new EmptyTextWatcher();
    private Button btnRegister;
    private EditText firstName;
    private EditText lastName;

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
        btnRegister.setEnabled(!fnwatch.empty && !lnwatch.empty);
        if (btnRegister.isEnabled()){
            btnRegister.setBackgroundColor(0xFFAA66CC);
        }
        else{
            btnRegister.setBackgroundColor(0xFFB1A0BA);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.file), Context.MODE_PRIVATE);

        //change activity if there is no known FirstName
        if (!sharedPref.getString("FirstName", "").equals("")) {
            Intent k = new Intent(MainActivity.this, Dashboard.class);
            startActivity(k);
            return;
        }

        // Code normal ici

            //disabled button by default and check editText state
            btnRegister = (Button) findViewById(R.id.buttonRegister);
            btnRegister.setEnabled(false);
            btnRegister.setBackgroundColor(0xFFB1A0BA);
            btnRegister.setOnClickListener(this);

            firstName = (EditText) findViewById(R.id.editFirstname);
            lastName = (EditText) findViewById(R.id.editLastname);
            firstName.addTextChangedListener(fnwatch);
            lastName.addTextChangedListener(lnwatch);

    }

    @Override
    public void onClick(View v) {
        if (R.id.buttonRegister == v.getId()) {
            firstName = (EditText) findViewById(R.id.editFirstname);
            lastName = (EditText) findViewById(R.id.editLastname);

            //stock firstname and lastname with SharedPreferences
            SharedPreferences sharedPref = getSharedPreferences(
                    getString(R.string.file), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("FirstName",firstName.getText().toString());
            editor.putString("LastName",lastName.getText().toString());
            editor.apply();
            //Log.e("test", sharedPref.getString("FirstName", "default value"));

            //change activity
            Intent k = new Intent(MainActivity.this, Dashboard.class);
            startActivity(k);
        }
    }
}