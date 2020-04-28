package com.example.junhe.ocr;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.Locale;

import data.TransTextData;
import data.TransTextResponse;
import network.RetrofitClient;
import network.ServiceApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {
    private EditText text;
    private Button btn_tts;
    private Button btn_save;
    private TextToSpeech tts;
    private ServiceApi service;
    private ProgressBar mProgressBar;
    private String content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans);
        btn_tts = (Button)findViewById(R.id.btn_tts);
        btn_save = (Button)findViewById(R.id.btn_save_text);
        text = (EditText)findViewById(R.id.edit_trans_text);
        tts = new TextToSpeech(this,this);//첫번째는 Context 두번째는 리스너
        mProgressBar = (ProgressBar)findViewById(R.id.trans_progress);
        btn_tts.setEnabled(false);
        service = RetrofitClient.getClient().create(ServiceApi.class);

        //버튼 클릭하면 TTS 작동
        btn_tts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speakOutNow();
            }
        });
        //텍스트 데이터베이스에 저장
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptSaveText();
            }
        });
        content = getIntent().getExtras().getString("content");
        text.setText(content);

    }

    //앱종료시 tts를 같이 종료해 준다.
    @Override
    protected void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int language = tts.setLanguage(Locale.KOREAN);

            if (language == TextToSpeech.LANG_MISSING_DATA || language == TextToSpeech.LANG_NOT_SUPPORTED) {
                btn_tts.setEnabled(false);
                Toast.makeText(this, "지원하지 않는 언어입니다.", Toast.LENGTH_SHORT).show();
            } else {
                btn_tts.setEnabled(true);
                speakOutNow();
            }
        } else {
            Toast.makeText(this, "TTS 실패!", Toast.LENGTH_SHORT).show();
        }
    }

    //Speak out...
    private void speakOutNow() {
        String sText = text.getText().toString();
        //tts.setPitch((float) 0.1); //음량
        tts.setSpeechRate((float) 0.5); //재생속도
        tts.speak(sText, TextToSpeech.QUEUE_FLUSH, null);
    }


    ////////////////////////////////////////////////////////////////////
    //텍스트 저장 로직...
    private void attemptSaveText() {
        text.setError(null);

        String mText = text.getText().toString();

        boolean cancel = false;
        View focusView = null;

        //텍스트 유효성 검사
        if (mText.isEmpty()) {
            text.setError("저장할 내용이 없습니다..");
            focusView = text;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            startSaveText(new TransTextData(mText));
            showProgress(true);
        }
    }

    private void startSaveText(TransTextData data) {
        service.transTextSave(data).enqueue(new Callback<TransTextResponse>() {
            @Override
            public void onResponse(Call<TransTextResponse> call, Response<TransTextResponse> response) {
                TransTextResponse result = response.body();
                Toast.makeText(TransActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                showProgress(false);

                if (result.getCode() == 200) {
                    finish();
                }
            }

            @Override
            public void onFailure(Call<TransTextResponse> call, Throwable t) {
                Toast.makeText(TransActivity.this, "텍스트 저장 에러 발생:"+t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("텍스트 저장 에러 발생", t.getMessage());
                showProgress(false);
            }
        });
    }
    private void showProgress(boolean show) {
        mProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }


}
