package com.utt.financesorganizer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FinancesOrganizerDB extends SQLiteOpenHelper {

    public static final String TABLE_INCOME ="income";
    public static final String COLUMN_INCOME_ID = "id";
    public static final String COLUMN_INCOME_NAME = "name";
    public static final String COLUMN_INCOME_AMOUNT = "amount";
    public static final String COLUMN_INCOME_DATE = "date";
    public static final String COLUMN_INCOME_CAT = "cat_id";

    public static final String TABLE_EXPENSE ="expense";
    public static final String COLUMN_EXPENSE_ID = "id";
    public static final String COLUMN_EXPENSE_NAME = "name";
    public static final String COLUMN_EXPENSE_AMOUNT = "amount";
    public static final String COLUMN_EXPENSE_DATE = "date";
    public static final String COLUMN_EXPENSE_CAT = "cat_id";

    public static final String TABLE_CATEGORY ="category";
    public static final String COLUMN_CATEGORY_ID = "id";
    public static final String COLUMN_CATEGORY_NAME = "name";

    private static final String DATABASE_NAME = "financesorganizer.db";
    private static final int DATABASE_VERSION = 1;

    // Database table creation
    private static final String CAT_CREATE =
            "create table " + TABLE_CATEGORY + "(" +
                    COLUMN_CATEGORY_ID + " int not null primary key, " +
                    COLUMN_CATEGORY_NAME + " text not null);";

    private static final String INCOME_CREATE =
            "create table " + TABLE_INCOME + "(" +
                    COLUMN_INCOME_ID + " INTEGER not null primary key autoincrement, " +
                    COLUMN_INCOME_NAME + " text not null, " +
                    COLUMN_INCOME_AMOUNT + " float not null, " +
                    COLUMN_INCOME_DATE + " datetime not null, " +
                    COLUMN_INCOME_CAT + " int not null, " +
                    "foreign key (" + COLUMN_INCOME_CAT + ") references " + TABLE_CATEGORY + "(" + COLUMN_CATEGORY_ID + "));";

    private static final String EXPENSE_CREATE =
            "create table " + TABLE_EXPENSE + "(" +
                    COLUMN_EXPENSE_ID + " INTEGER not null primary key autoincrement, " +
                    COLUMN_EXPENSE_NAME + " text not null, " +
                    COLUMN_EXPENSE_AMOUNT + " float not null, " +
                    COLUMN_EXPENSE_DATE + " datetime not null, " +
                    COLUMN_EXPENSE_CAT + " int not null, " +
                    "foreign key (" + COLUMN_EXPENSE_CAT + ") references " + TABLE_CATEGORY + "(" + COLUMN_CATEGORY_ID + "));";

    private static final String INSERT_CAT =
            "INSERT INTO " +TABLE_CATEGORY + "("+ COLUMN_CATEGORY_ID+","+ COLUMN_CATEGORY_NAME +") VALUES\n" +
                    "                (0, \"Salary\"),\n" +
                    "                (1, \"Other income\"),\n" +
                    "                (2, \"Food\"),\n" +
                    "                (3, \"Travel\"),\n" +
                    "                (4, \"Leisure\"),\n" +
                    "                (5, \"Accommodation\"),\n" +
                    "                (6, \"Other expense\");";


    public FinancesOrganizerDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.getReadableDatabase();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CAT_CREATE);
        db.execSQL(INCOME_CREATE);
        db.execSQL(EXPENSE_CREATE);
        db.execSQL(INSERT_CAT);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }


    public long insertIncome(String title, String date, String cat, String amount) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_INCOME_NAME, title);
        values.put(COLUMN_INCOME_AMOUNT, amount);
        values.put(COLUMN_INCOME_DATE, date);
        values.put(COLUMN_INCOME_CAT, cat);

        SQLiteDatabase db = this.getWritableDatabase();
        long id = db.insert(TABLE_INCOME, null, values);
        db.close();
        return id;
    }

    public long insertExpense(String title, String date, String cat, String amount) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_EXPENSE_NAME, title);
        values.put(COLUMN_EXPENSE_AMOUNT, amount);
        values.put(COLUMN_EXPENSE_DATE, date);
        values.put(COLUMN_EXPENSE_CAT, cat);

        SQLiteDatabase db = this.getWritableDatabase();
        long id = db.insert(TABLE_EXPENSE, null, values);
        db.close();
        return id;
    }

    public double getTotalExpense() {

        double total = 0;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(amount) as Total FROM expense", null);

        if (cursor.moveToFirst()) {
            total = cursor.getDouble(cursor.getColumnIndex("Total"));// get final total
        }
        return total;
    }

    public double getTotalIncome() {

        double total = 0;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(amount) as Total FROM income", null);

        if (cursor.moveToFirst()) {
            total = cursor.getDouble(cursor.getColumnIndex("Total"));// get final total
        }
        return total;
    }

    public List<MouvMoney> getExpense(String fromEx, String toEx) {
        List<MouvMoney> le = new ArrayList<>();

        String GET_EXPENSE =
                "SELECT " + TABLE_EXPENSE + "." +COLUMN_EXPENSE_NAME + " AS exname, " + TABLE_EXPENSE + "." + COLUMN_EXPENSE_AMOUNT + ", " + TABLE_EXPENSE + "." + COLUMN_EXPENSE_DATE + ", " + TABLE_CATEGORY + "." +
                        COLUMN_CATEGORY_NAME + " AS catname FROM " + TABLE_EXPENSE + ", " + TABLE_CATEGORY + " WHERE " + TABLE_EXPENSE + "." + COLUMN_EXPENSE_CAT + " = " + TABLE_CATEGORY + "." + COLUMN_CATEGORY_ID + " AND " +
                        TABLE_EXPENSE + "." + COLUMN_EXPENSE_DATE + " BETWEEN \"" + fromEx + "\" AND \"" + toEx + "\" ORDER BY date DESC";

        //select all query
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(GET_EXPENSE, null);

        //looping all the list
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndex("exname"));
                double amount = cursor.getDouble(cursor.getColumnIndex(COLUMN_EXPENSE_AMOUNT));
                String date = cursor.getString(cursor.getColumnIndex(COLUMN_EXPENSE_DATE));
                String cat = cursor.getString(cursor.getColumnIndex("catname"));

                Expense expense = new Expense(name, amount, date, cat);

                le.add(expense);
            } while(cursor.moveToNext());
        }
        return le;
    }

    public List<MouvMoney> getIncome(String fromIn, String toIn) {
        List<MouvMoney> li = new ArrayList<>();

        String GET_INCOME =
                "SELECT " + TABLE_INCOME + "." +COLUMN_INCOME_NAME + " AS inname, " + TABLE_INCOME + "." + COLUMN_INCOME_AMOUNT + ", " + TABLE_INCOME + "." + COLUMN_INCOME_DATE + ", " + TABLE_CATEGORY + "." +
                        COLUMN_CATEGORY_NAME + " AS catname FROM " + TABLE_INCOME + ", " + TABLE_CATEGORY + " WHERE " + TABLE_INCOME + "." + COLUMN_INCOME_CAT + " = " + TABLE_CATEGORY + "." + COLUMN_CATEGORY_ID  + " AND " +
                        TABLE_INCOME + "." + COLUMN_INCOME_DATE + " BETWEEN \"" + fromIn + "\" AND \"" + toIn + "\" ORDER BY date DESC";

        //select all query
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(GET_INCOME, null);

        //looping all the list
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndex("inname"));
                double amount = cursor.getDouble(cursor.getColumnIndex(COLUMN_INCOME_AMOUNT));
                String date = cursor.getString(cursor.getColumnIndex(COLUMN_INCOME_DATE));
                String cat = cursor.getString(cursor.getColumnIndex("catname"));

                Income income = new Income(name, amount, date, cat);

                li.add(income);
            } while(cursor.moveToNext());
        }
        return li;
    }

}