package com.example.untitled1;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

class DBHelper extends SQLiteOpenHelper {
    final String LOG_TAG = "myLogs";

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "test";

    public static final String TABLE1_NAME = "students";
    public static final String TABLE2_NAME = "groups";
    public static final String LOGIN = "login";
    public static final String PASSW = "passw";
    private static final String CREATE1_TABLE = "create table " + TABLE1_NAME + " ( _id integer primary key autoincrement, "
            + LOGIN + " TEXT, " + PASSW + " TEXT)";
    private static final String CREATE2_TABLE = "create table " + TABLE2_NAME + " ( _id integer primary key autoincrement, "
            + LOGIN + " TEXT, " + PASSW + " TEXT)";

    public DBHelper(Context context) {
        // конструктор суперкласса
        super(context, "myDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(LOG_TAG, "--- onCreate database ---");
        // создаем таблицу с полями
        db.execSQL(CREATE1_TABLE);
        db.execSQL(CREATE2_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}