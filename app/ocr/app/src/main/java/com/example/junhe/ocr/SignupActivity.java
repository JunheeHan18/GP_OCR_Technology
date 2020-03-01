package com.example.junhe.ocr;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import data.SignupData;
import data.SignupResponse;
import network.RetrofitClient;
import network.ServiceApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {
    private EditText mIdView;
    private EditText mPasswordView;
    private Button mSignupButton;
    private ProgressBar mProgressView;
    private ServiceApi service;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mIdView = (EditText) findViewById(R.id.edit_signup_id);
        mPasswordView = (EditText) findViewById(R.id.edit_signup_password);
        mSignupButton = (Button) findViewById(R.id.btn_signup);
        mProgressView = (ProgressBar) findViewById(R.id.join_progress);

        service = RetrofitClient.getClient().create(ServiceApi.class);

        mSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptSignup();
            }
        });
    }

    private void attemptSignup() {
        mIdView.setError(null);
        mPasswordView.setError(null);

        String id = mIdView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // 패스워드의 유효성 검사
        if (password.isEmpty()) {
            mIdView.setError("비밀번호를 입력해주세요.");
            focusView = mIdView;
            cancel = true;
        } else if (!isPasswordValid(password)) {
            mPasswordView.setError("6자 이상의 비밀번호를 입력해주세요.");
            focusView = mPasswordView;
            cancel = true;
        }

        // 아이디의 유효성 검사
        if (id.isEmpty()) {
            mIdView.setError("아이디를 입력해주세요.");
            focusView = mIdView;
            cancel = true;
        } else if (!isIdValid(id)) {
            mIdView.setError("5자리 이상의 유효한 아이디를 입력해주세요.");
            focusView = mIdView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            startJoin(new SignupData(id, password));
            showProgress(true);
        }
    }

    private void startJoin(SignupData data) {
        service.memberSignup(data).enqueue(new Callback<SignupResponse>() {
            @Override
            public void onResponse(Call<SignupResponse> call, Response<SignupResponse> response) {
                SignupResponse result = response.body();
                Toast.makeText(SignupActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                showProgress(false);

                if (result.getCode() == 200) {
                    finish();
                }
            }

            @Override
            public void onFailure(Call<SignupResponse> call, Throwable t) {
                Toast.makeText(SignupActivity.this, "회원가입 에러 발생:"+t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("회원가입 에러 발생", t.getMessage());
                showProgress(false);
            }
        });
    }

    private boolean isIdValid(String id) {
        return id.length()>=5;
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= 6;
    }

    private void showProgress(boolean show) {
        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}