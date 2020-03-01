package com.example.junhe.ocr;

import android.content.Intent;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import java.util.ArrayList;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class MainActivity extends AppCompatActivity {
    Socket socket;
    Intent i;
    SpeechRecognizer mRecognizer;
    final int NORMALMODE = 101;
    final int READINGMODE = 102;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn_Nomal = (Button)findViewById(R.id.btn_normalmode);
        Button btn_Reading = (Button)findViewById(R.id.btn_readingmode);

        //버튼활용 음성인식 부분
        Button btn_voice = (Button)findViewById(R.id.btn_voiceRecognize);
        Toast.makeText(MainActivity.this,"음성인식 서비스 실행됨..",Toast.LENGTH_SHORT);
        i =  new Intent(RecognizerIntent.ACTION_WEB_SEARCH);
        i.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,getPackageName());
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"ko-KR");



        btn_voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecognizer.startListening(i);
            }
        });


        //백그라운드 음성인식 부분 보류,,, 해결못함
        //startService(new Intent(this,VoiceService.class));





        //서버 소켓연결 부분
        try {
            socket = IO.socket("http://52.78.200.48:4000");
        }catch (Exception e) {
            e.printStackTrace();
        }

        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                socket.emit("message_from_client", "Android Connected...");
            }
        }).on("message_from_server", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, args[0].toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        socket.connect();


        btn_Nomal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int mode = NORMALMODE;
                socket.emit("set_to_normal", mode);
            }
        });

        btn_Reading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int mode = READINGMODE;
                socket.emit("set_to_reading",mode);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        mRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        mRecognizer.setRecognitionListener(listener);

    }

    //음성인식 클래스 RecognitionListener 정의 부분, 음성인식의 해당 결과는 Results에서 얻음...
    private RecognitionListener listener = new RecognitionListener() {
        @Override
        public void onReadyForSpeech(Bundle params) {
            Toast.makeText(MainActivity.this,"ddd",Toast.LENGTH_SHORT);
        }

        @Override
        public void onBeginningOfSpeech() {

        }

        @Override
        public void onRmsChanged(float rmsdB) {

        }

        @Override
        public void onBufferReceived(byte[] buffer) {

        }

        @Override
        public void onEndOfSpeech() {
            Toast.makeText(MainActivity.this,"음성인식 끝",Toast.LENGTH_SHORT);
        }

        @Override
        public void onError(int error) {
            Toast.makeText(MainActivity.this,error,Toast.LENGTH_SHORT);
            Log.e("error: ",Integer.toString(error));
        }

        @Override
        public void onResults(Bundle results) {
            String key ="";
            key = SpeechRecognizer.RESULTS_RECOGNITION;
            ArrayList<String> mResult= results.getStringArrayList(key);
            String[] rs = new String[mResult.size()];
            mResult.toArray(rs);
            Log.e("content: ",rs[0]);

            switch(rs[0]){
                case "번역":
                    Intent intent = new Intent(getApplicationContext(),TransActivity.class);
                    startActivity(intent);
                    break;
                case "책갈피":
                    Intent intent1 = new Intent(getApplicationContext(),BookmarkActivity.class);
                    startActivity(intent1);
                    break;
            }

        }

        @Override
        public void onPartialResults(Bundle partialResults) {

        }

        @Override
        public void onEvent(int eventType, Bundle params) {

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mymenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.menu_tranform:
                Intent intent = new Intent(getApplicationContext(),TransActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_bookmark:
                Intent intent1 = new Intent(getApplicationContext(),BookmarkActivity.class);
                startActivity(intent1);
                break;
            case R.id.menu_signin:
                Intent intent2 = new Intent(getApplicationContext(),SigninActivity.class);
                startActivity(intent2);
                break;
            case R.id.menu_signup:
                Intent intent3 = new Intent(getApplicationContext(),SignupActivity.class);
                startActivity(intent3);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        mRecognizer.destroy();
        super.onPause();
    }
}
