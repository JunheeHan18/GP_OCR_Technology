package com.example.mobileproject;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class join_member_activity extends AppCompatActivity {

    SQLiteDatabase database;
    SimpleDatabaseHelper databaseHelper;


    EditText edit_id, edit_nickname, edit_password;
    Button btn_submit, btn_cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_member_activity);

        database = login_activity.database;
        databaseHelper = login_activity.databaseHelper;

        edit_id = findViewById(R.id.join_edit_id);
        edit_nickname = findViewById(R.id.join_edit_nickname);
        edit_password = findViewById(R.id.join_edit_password);

        btn_submit = findViewById(R.id.join_button_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelper.insertDataWithoutPrimaryKey(database,
                        edit_id.getText().toString(),
                        edit_nickname.getText().toString(),
                        edit_password.getText().toString());
                finish();
            }
        });
        btn_cancel = findViewById(R.id.join_button_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
