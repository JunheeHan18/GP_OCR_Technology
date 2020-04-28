package com.example.mobileproject;

import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements ViewFlipperAction.ViewFlipperCallback {
    ImageView event_Image;
    TextView water_Text,camping_Text,leisure_Text,title_text,nickName_text;
    Button login_Button;
    private boolean b_CheckLogin = false;
    final static int NICKNAME =1;
    //뷰플리퍼
    ViewFlipper flipper;
    //인덱스
    List<ImageView> indexes;
    private int touchX,touchY;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == NICKNAME) {
            if (resultCode == RESULT_OK) {
                b_CheckLogin=true;
                nickName_text.setText(data.getStringExtra("NICKNAME")+"님");
                nickName_text.setVisibility(View.VISIBLE);
                login_Button.setText("로그아웃");
                Toast.makeText(getApplicationContext(),nickName_text.getText()+" 환영합니다 !!",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        nickName_text = (TextView)findViewById(R.id.text_nickname);

        water_Text=findViewById(R.id.water_main);
        water_Text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent water_Intent=new Intent(MainActivity.this,water_page.class);
                startActivity(water_Intent);
            }
        });
        camping_Text=findViewById(R.id.camping_main);
        camping_Text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent water_Intent=new Intent(MainActivity.this,camping_page.class);
                startActivity(water_Intent);
            }
        });
        leisure_Text=findViewById(R.id.leisure_main);
        leisure_Text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent water_Intent=new Intent(MainActivity.this,leisure_page.class);
                startActivity(water_Intent);
            }
        });
        event_Image=findViewById(R.id.event_Image);
        event_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent event_Intent=new Intent(MainActivity.this, event_activity.class);
                startActivity(event_Intent);
            }
        });
        login_Button=findViewById(R.id.login_Button);


        water_Text.setTypeface(Typeface.createFromAsset(getAssets(),"HoonPinkpungchaR.otf"));
        camping_Text.setTypeface(Typeface.createFromAsset(getAssets(),"HoonPinkpungchaR.otf"));
        leisure_Text.setTypeface(Typeface.createFromAsset(getAssets(),"HoonPinkpungchaR.otf"));
        title_text=(TextView)findViewById(R.id.text_hotplace);
        title_text.setTypeface(Typeface.createFromAsset(getAssets(),"HoonPinkpungchaR.otf"));

        //UI
        flipper = (ViewFlipper)findViewById(R.id.flipper);
        ImageView index0 = (ImageView)findViewById(R.id.imgIndex0);
        ImageView index1 = (ImageView)findViewById(R.id.imgIndex1);
        ImageView index2 = (ImageView)findViewById(R.id.imgIndex2);

        //인덱스리스트
        indexes = new ArrayList<>();
        indexes.add(index0);
        indexes.add(index1);
        indexes.add(index2);

        //xml을 inflate 하여 flipper view에 추가하기
        //inflate
        LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        View view1 = inflater.inflate(R.layout.viewflipper1, flipper, false);
        View view2 = inflater.inflate(R.layout.viewflipper2, flipper, false);
        View view3 = inflater.inflate(R.layout.viewflipper3, flipper, false);
        //inflate 한 view 추가
        flipper.addView(view1);
        flipper.addView(view2);
        flipper.addView(view3);

        //리스너설정 - 좌우 터치시 화면넘어가기
        flipper.setOnTouchListener(new ViewFlipperAction(this, flipper));
    }


   /* public void onFlipperClick(View v){
        if(touchY==touchX&&touchY!=0) {
            switch (v.getId()) {
                case R.id.hongbo_water:
                    Intent intent = new Intent(getApplicationContext(), water_page.class);
                    startActivity(intent);
                    break;
                case R.id.hongbo_camping:
                    Intent intent1 = new Intent(getApplicationContext(), camping_page.class);
                    startActivity(intent1);
                    break;
                case R.id.hongbo_leisure:
                    Intent intent2 = new Intent(getApplicationContext(), leisure_page.class);
                    startActivity(intent2);
                    break;
            }
        }
    }*/

   /* @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                touchX = (int)event.getX(0);
                break;
            case MotionEvent.ACTION_UP:
                touchY = (int)event.getY(0);
                break;
        }
        invalidate();
        return super.onTouchEvent(event);
    }*/

    public void onLoginButtonClick(View v){
        switch(v.getId()) {
            case R.id.login_Button:
                if (b_CheckLogin) {
                    login_Button.setText("로그인");
                    nickName_text.setVisibility(View.INVISIBLE);
                    nickName_text.setText("");
                    b_CheckLogin = false;
                } else {
                    Intent login_Intent = new Intent(MainActivity.this, login_activity.class);
                    startActivityForResult(login_Intent, NICKNAME);
                }
                break;
        }
    }
    //인덱스 업데이트
    @Override
    public void onFlipperActionCallback(int position) {
        Log.d("ddd", ""+position);
        for(int i=0; i<indexes.size(); i++){
            ImageView index = indexes.get(i);
            //현재화면의 인덱스 위치면 녹색
            if(i == position){
                index.setImageResource(R.drawable.btn_black);
            }
            //그외
            else{
                index.setImageResource(R.drawable.btn_white);
            }
        }
    }

}