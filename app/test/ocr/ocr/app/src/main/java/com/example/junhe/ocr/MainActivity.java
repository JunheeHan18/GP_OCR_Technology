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

import static java.lang.Boolean.TRUE;

public class MainActivity extends AppCompatActivity {
    Socket socket, socket_py;
    Intent i;
    SpeechRecognizer mRecognizer;
    int flag =0;
    final int NORMALMODE = 101;
    final int READINGMODE = 102;
    final int TAKEPIC = 103;
    String[] m_SavedtoSearchWord = new String[10]; //검색내용 답변에 대해 저장할 문자배열
    int m_StringArrayCountNum =0; //답변 저장배열 카운터 변수
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn_Nomal = (Button)findViewById(R.id.btn_normalmode);
        Button btn_Reading = (Button)findViewById(R.id.btn_readingmode);
        Button btn_TakePic = (Button)findViewById(R.id.btn_takePic);
        Button btn_Search = (Button)findViewById(R.id.btn_searchInpic);
        m_SavedtoSearchWord[m_StringArrayCountNum++] = "검색하신 내용에 대해서 말씀드릴게요. "; // 검색 결과 첫마디 세팅
        //버튼활용 음성인식 부분
        Button btn_voice = (Button)findViewById(R.id.btn_voiceRecognize);
        Toast.makeText(MainActivity.this,"음성인식 서비스 실행됨..",Toast.LENGTH_SHORT);
        i =  new Intent(RecognizerIntent.ACTION_WEB_SEARCH);
        i.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,getPackageName());
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"ko-KR");

        //버튼활용 독서모드 부분
        btn_Reading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent readintent = new Intent(MainActivity.this,BookmarkActivity.class);
                startActivity(readintent);
                finish();
            }
        });


        btn_voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecognizer.startListening(i);
            }
        });

        //백그라운드 음성인식 부분 보류,, 해결못함
        //startService(new Intent(this,VoiceService.class));


        //서버 소켓연결 부분
        try {
            socket = IO.socket("http://52.78.200.48:4000");
        }catch (Exception e) {
            e.printStackTrace();
        }
        try {
            socket_py = IO.socket("http://192.168.219.104:5000"); //라즈베리파이 소켓연결 부분
        }catch (Exception e) {
            e.printStackTrace();
        }
/***************************************************라즈베리 소켓*********************************************************/
        socket_py.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                socket_py.emit("message_from_android", "Android Connected...");
            }
        }).on("message_from_raspi", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (flag == 0){
                               Toast.makeText(MainActivity.this, args[0].toString(), Toast.LENGTH_SHORT).show();
                              Intent intent = new Intent(MainActivity.this, TransActivity.class);
                              String content = args[0].toString();
                              intent.putExtra("content", content);  //문자열 전달
                              startActivity(intent);
                              flag=1;
                         }
                    }
                });
            }
        });

        socket_py.connect();
/***********************************************************************************************************************/
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
        }).on("result_of_trans_text", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                            //촬영버튼 결과 수신 수 동작 설정할 코드 부분
                        //args[0];
                    }
                });
            }
        }).on("result_of_search_text", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //검색버튼 결과 수신 수 동작 설정할 코드 부분
                        //m_SavedtoSearchWord[m_StringArrayCountNum++]=args[0].toString();
                    }
                });
            }
        });
        socket.connect();


        btn_Nomal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                socket.emit("set_to_normal", NORMALMODE);
            }
        });

        btn_Reading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                socket.emit("set_to_reading", READINGMODE);
            }
        });

        btn_TakePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                socket_py.emit("take_a_pic","pic");
                flag=0;
            }
        });

        btn_Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecognizer.startListening(i);
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

            if(rs[0].equals("번역")||rs[0].equals("북마크")) {
                Intent intent = new Intent(getApplicationContext(), TransActivity.class);
                startActivity(intent);
            }else if(rs[0].equals("책갈피")) {
                Intent intent1 = new Intent(getApplicationContext(), BookmarkActivity.class);
                startActivity(intent1);
            }else if(rs[0].equals("촬영")) {
                socket.emit("take_a_pic", TAKEPIC);
            }else{
                socket.emit("take_a_pic_and_search",rs[0]);
                m_SavedtoSearchWord[m_StringArrayCountNum++] = rs[0];
            }
            /*
                switch (rs[0]) {
                    case "번역":
                        Intent intent = new Intent(getApplicationContext(), TransActivity.class);
                        startActivity(intent);
                        break;
                    case "책갈피":
                        Intent intent1 = new Intent(getApplicationContext(), BookmarkActivity.class);
                        startActivity(intent1);
                        break;
                    case "촬영":
                        socket.emit("take_a_pic", TAKEPIC);
                        break;
                }*/
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
