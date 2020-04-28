package com.example.mobileproject;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class login_activity extends AppCompatActivity {

    static SQLiteDatabase database;
    String database_name;
    String table_name;

    static SimpleDatabaseHelper databaseHelper;

    Button button_login;
    Button button_regist;

    EditText edit_id, edit_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);
            button_login = findViewById(R.id.login_Button1);
            button_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (database != null) {
                        Cursor cursor = database.rawQuery("SELECT id, nickname, password FROM userdata Where id = " + "'" + edit_id.getText().toString() + "'" + ";", null);
                        //Cursor cursor = database.rawQuery("SELECT id, nickname, password FROM userdata Where id = 'multicell'", null);
                        cursor.moveToFirst();
                        System.out.println(cursor.getCount());

                        System.out.println("id: " + cursor.getString(0));
                        System.out.println("nickname: " + cursor.getString(1));
                        System.out.println("p/w: " + cursor.getString(2));

                        String strEdit_id = edit_id.getText().toString();
                        String strEdit_password = edit_password.getText().toString();
                        String user_nickname;   //!< 로그인한 유저의 닉네임이 저장될 변수

                        //!< 로그인 인증 성공시
                        if (edit_id.getText().toString().equals(cursor.getString(0)) &&
                                edit_password.getText().toString().equals(cursor.getString(2))) {
                            //Toast.makeText(getApplicationContext(), "id: " + cursor.getString(0) +
                            //        ", p/w: " + cursor.getString(1), Toast.LENGTH_SHORT).show();
                            user_nickname = cursor.getString(1);
                            Intent intent = new Intent();
                            intent.putExtra("NICKNAME", user_nickname);
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                        cursor.close();
                    }
                }
            });
            button_regist = findViewById(R.id.login_Button2);
            button_regist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), join_member_activity.class);
                    startActivity(intent);
                }
            });

            edit_id = findViewById(R.id.login_edit_id);
            edit_password = findViewById(R.id.login_edit_password);

            database_name = "hotplace";
            try {
                if (database == null) {
                    databaseHelper = new SimpleDatabaseHelper(getApplicationContext(),
                            database_name, null, 2);
                    database = databaseHelper.getWritableDatabase();
                    //Toast.makeText(this, "DB 생성", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            table_name = "userdata";
            try {
                if (database != null) {
                    database.execSQL("Create Table if not exists " + table_name + "(" + "user_number integer PRIMARY KEY autoincrement," +
                            "id text NOT NULL UNIQUE," +
                            "nickname text NOT NULL UNIQUE, " +
                            "password text" + ")");
                    //Toast.makeText(this, "Table 생성", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            //databaseHelper.insertData(database, 1, "admin1", "hotplace", "2019");

    }
}
