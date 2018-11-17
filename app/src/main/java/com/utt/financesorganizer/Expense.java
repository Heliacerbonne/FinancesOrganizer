package com.utt.financesorganizer;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;

public class Expense extends MouvMoney {

    public Expense(String name, double amount, String date, String cat){
        super(name, amount, date,cat);
    }
}
