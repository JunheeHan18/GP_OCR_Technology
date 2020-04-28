package com.example.junhe.ocr;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import data.BookmarkData;
import data.BookmarkResponse;
import data.SigninData;
import data.SigninResponse;
import data.SignupResponse;
import network.RetrofitClient;
import network.ServiceApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookmarkActivity extends AppCompatActivity {
    private ServiceApi service;
    private bookmarkAdapter adapter;
    private  ListItem[] listitem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);
        service = RetrofitClient.getClient().create(ServiceApi.class);
        ListView listview;

        // Adapter 생성
        adapter = new bookmarkAdapter();
        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) findViewById(R.id.bmList);
        listview.setAdapter(adapter);

        makeList();

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(BookmarkActivity.this, TransActivity.class);
                String content = listitem[position].getContent();
                intent.putExtra("content",content);  //문자열 전달

                startActivity(intent);
            }
        });
        //Toast.makeText(getApplicationContext(),listitem[5].getContent(),Toast.LENGTH_SHORT).show();
        //initAdapter(listitem);
        // thread.start();


        // for(int i=0;i<listitem.length;i++)
        //adapter.addItem(listitem[i].getTitle(),listitem[i].getContent(),listitem[i].getDate());



    }
        private void initAdapter (ListItem[]listitem){
            adapter.addItem("1", "2", "3");
            if (listitem.length > 0) {
                for (int i = 0; i < listitem.length; i++) {
                    adapter.addItem(listitem[i].getTitle(), listitem[i].getContent(), listitem[i].getDate());
                }
            }
        }


    private void makeList() {
            BookmarkData data = new BookmarkData("aa","aa","aas");
            service.getSaveContent(data).enqueue(new Callback<BookmarkResponse[]>() {
                @Override
                public void onResponse(Call<BookmarkResponse[]> call, Response<BookmarkResponse[]> response) {
                    if(response.isSuccessful()) {//check for Response status
                        BookmarkResponse[] result = response.body();
                        int idx = result.length;
                        listitem = new ListItem[idx];
                        for(int i=0;i<idx;i++) {
                            listitem[i] = new ListItem(result[i].getTitle(), result[i].getContent(), result[i].getSave_date());
                            adapter.addItem(listitem[i].getTitle(),listitem[i].getContent(),listitem[i].getDate());
                        }
                        adapter.notifyDataSetChanged();
                       Toast.makeText(getApplicationContext(),listitem[5].getContent(),Toast.LENGTH_SHORT).show();

                        //initAdapter(listitem);
                        //Toast.makeText(getApplicationContext(),"길이:"+result.length,Toast.LENGTH_SHORT).show();
                       /* for(int i=0;i<result.length;i++) {

                           adapter.addItem(result[i].getTitle(),
                           result[i].getContent(),
                                   result[i].getSave_date());
                           if(i==5){
                               Toast.makeText(getApplicationContext(),"수행:"+result.length,Toast.LENGTH_SHORT).show();
                           }
                        }*/
                    }
                }

                @Override
                public void onFailure(Call<BookmarkResponse[]> call, Throwable t) {
                    Log.e("로그인 에러 발생", t.getMessage());
                    //showProgress(false);
                }
            });
        }




}
