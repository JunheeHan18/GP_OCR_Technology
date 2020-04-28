package com.example.mobileproject;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

public class event_activity extends AppCompatActivity {

    private ProgressBar progressBar;
    private AlertDialog.Builder alertDialogBuilder;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_activity);
        progressBar = findViewById(R.id.progressBar);
        alertDialogBuilder = new AlertDialog.Builder(this);


        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 100) {
                    alertDialogBuilder.setCancelable(false);
                    alertDialogBuilder.setMessage("응모하시겠습니까?");
                    alertDialogBuilder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getApplicationContext(), "이벤트 쿠폰이 발급 되었습니다!", Toast.LENGTH_SHORT).show();
                            initProgressBar();
                            finish();
                        }
                    });
                    alertDialogBuilder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getApplicationContext(), "평가해주셔서 감사합니다", Toast.LENGTH_SHORT).show();
                            initProgressBar();
                            finish();
                        }
                    });
                    alertDialogBuilder.show();
                }
            }
        };
    }

    public void initProgressBar() {
        progressBar.setProgress(0);
        progressBar.setMax(100);
    }

    public void event_Click(final View v) {
        switch (v.getId()) {
            case R.id.event_button1:
                initProgressBar();
                final Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int i;
                        for (i = 0; i < 100; i++) {
                            try {
                                Thread.sleep(10);
                                progressBar.setProgress(i);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        handler.sendMessage(Message.obtain(handler, i));
                    }
                });
                thread.start();
                /*finish();*/
                break;
            case R.id.event_button2:
                finish();
            default:
                break;
        }
    }
}
