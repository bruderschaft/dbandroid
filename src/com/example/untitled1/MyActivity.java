package com.example.untitled1;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MyActivity extends Activity implements OnClickListener{
    final String LOG_TAG = "myLogs";

    Button btnAdd, btnRead, btnClear;
    EditText etName, etEmail;

    DBHelper dbHelper;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        btnRead = (Button) findViewById(R.id.btnRead);
        btnRead.setOnClickListener(this);

        btnClear = (Button) findViewById(R.id.btnClear);
        btnClear.setOnClickListener(this);

        etName = (EditText) findViewById(R.id.etName);
        etEmail = (EditText) findViewById(R.id.etEmail);

        // создаем объект для создания и управления версиями БД
        dbHelper = new DBHelper(this);
    }


    @Override
    public void onClick(View v) {

        // создаем объект для данных
        ContentValues cv = new ContentValues();

        // получаем данные из полей ввода
        String name = etName.getText().toString();
        String email = etEmail.getText().toString();

        // подключаемся к БД
        SQLiteDatabase db = dbHelper.getWritableDatabase();


        switch (v.getId()) {
            case R.id.btnAdd:
                Log.d(LOG_TAG, "--- Insert in mytable: ---");
                // подготовим данные для вставки в виде пар: наименование столбца - значение

                cv.put("name", name);
                cv.put("email", email);
                // вставляем запись и получаем ее ID
                long rowID = db.insert("mytable", null, cv);
                Log.d(LOG_TAG, "row inserted, ID = " + rowID);
                break;
            case R.id.btnRead:
                Log.d(LOG_TAG, "--- Rows in mytable: ---");
                // делаем запрос всех данных из таблицы mytable, получаем Cursor
                Cursor c = db.query("mytable", null, null, null, null, null, null);

                // ставим позицию курсора на первую строку выборки
                // если в выборке нет строк, вернется false
                if (c.moveToFirst()) {


                    // определяем номера столбцов по имени в выборке
                    int idColIndex = c.getColumnIndex("id");
                    int nameColIndex = c.getColumnIndex("name");
                    int emailColIndex = c.getColumnIndex("email");

                    do {
                        // получаем значения по номерам столбцов и пишем все в лог
                        Log.d(LOG_TAG,
                                "ID = " + c.getInt(idColIndex) +
                                        ", name = " + c.getString(nameColIndex) +
                                        ", email = " + c.getString(emailColIndex));
                        // переход на следующую строку
                        // а если следующей нет (текущая - последняя), то false - выходим из цикла
                    } while (c.moveToNext());
                } else
                    Log.d(LOG_TAG, "0 rows");
                c.close();
                break;
            case R.id.btnClear:
                Log.d(LOG_TAG, "--- Clear mytable: ---");
                // удаляем все записи
                int clearCount = db.delete("mytable", null, null);
                Log.d(LOG_TAG, "deleted rows count = " + clearCount);
                break;
        }
        // закрываем подключение к БД
        dbHelper.close();
    }



    class DBHelper extends SQLiteOpenHelper {
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
}
