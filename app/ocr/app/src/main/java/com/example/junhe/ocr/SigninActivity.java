package com.example.junhe.ocr;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import data.SigninData;
import data.SigninResponse;
import network.RetrofitClient;
import network.ServiceApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SigninActivity extends AppCompatActivity {
    private EditText mIDView;
    private EditText mPasswordView;
    private Button mSigninButton;
    private Button mSignupButton;
    private ProgressBar mProgressView;
    private ServiceApi service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        mIDView = (EditText) findViewById(R.id.edit_signin_id);
        mPasswordView = (EditText) findViewById(R.id.edit_signin_password);
        mSigninButton = (Button) findViewById(R.id.btn_signin);
        mSignupButton = (Button) findViewById(R.id.btn_to_signup);
        mProgressView = (ProgressBar) findViewById(R.id.login_progress);

        service = RetrofitClient.getClient().create(ServiceApi.class);

        mSigninButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptSignin();
            }
        });
        mSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent);
            }
        });
    }

    private void attemptSignin() {
        mIDView.setError(null);
        mPasswordView.setError(null);

        String id = mIDView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // 패스워드의 유효성 검사
        if (password.isEmpty()) {
            mIDView.setError("비밀번호를 입력해주세요.");
            focusView = mIDView;
            cancel = true;
        } else if (!isPasswordValid(password)) {
            mPasswordView.setError("6자 이상의 비밀번호를 입력해주세요.");
            focusView = mPasswordView;
            cancel = true;
        }

        // 이메일의 유효성 검사
        if (id.isEmpty()) {
            mIDView.setError("아이디를 입력해주세요.");
            focusView = mIDView;
            cancel = true;
        } else if (!isIdValid(id)) {
            mIDView.setError("유효한 아이디 입력해주세요.");
            focusView = mIDView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            startLogin(new SigninData(id, password));
            showProgress(true);
        }
    }

    private void startLogin(SigninData data) {
        service.memberSignin(data).enqueue(new Callback<SigninResponse>() {
            @Override
            public void onResponse(Call<SigninResponse> call, Response<SigninResponse> response) {
                SigninResponse result = response.body();
                Toast.makeText(SigninActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                showProgress(false);
            }

            @Override
            public void onFailure(Call<SigninResponse> call, Throwable t) {
                Toast.makeText(SigninActivity.this, "로그인 에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("로그인 에러 발생", t.getMessage());
                showProgress(false);
            }
        });
    }

    private boolean isIdValid(String id) {
        return id.length() >= 6;
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= 6;
    }

    private void showProgress(boolean show) {
        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}