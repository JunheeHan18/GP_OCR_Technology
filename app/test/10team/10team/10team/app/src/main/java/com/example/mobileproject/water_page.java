package com.example.mobileproject;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class water_page extends AppCompatActivity {
    static private String title="";
    ListView list;
    String[] titles={
            "인기 1위!! 계곡 그 자체!","대박 핫플~!","지금 아니면 못간다!!","산속 골짜기","재미 함량 1000%%","함양으로 놀러오세요~"
    };
    String[] places={
            "동계","삼척","수원","오대산","함양","함양"
    };
    String[] prices={
            "1인당 25,000","1인당 28,000","1인당 20,000","1인당 23,000","1인당 17,000","1인당 30,000",
    };
    Integer[] images={R.drawable.donggae,R.drawable.samchuck,R.drawable.suwon,R.drawable.odaesan,R.drawable.hamyang,R.drawable.hamyang2};
    DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.water_main);
        Water_CustomList adapter=new Water_CustomList(water_page.this);
        list=findViewById(R.id.list);
        list.setAdapter(adapter);
        if(!water_page.this.isFinishing()){
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    final Calendar c = Calendar.getInstance();
                    int mYear = c.get(Calendar.YEAR);
                    int mMonth = c.get(Calendar.MONTH);
                    int mDay = c.get(Calendar.DAY_OF_MONTH);
                    title = titles[position];
                    datePickerDialog = new DatePickerDialog(water_page.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            Toast.makeText(getBaseContext(), year+"년"+(month+1)+"월"+dayOfMonth+"일" + title + "상품으로 예약 완료하였습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                }
            });
        }
    }
    public class Water_CustomList extends ArrayAdapter<String> {
        private final Activity context;
        public Water_CustomList(Activity context){
            super(context,R.layout.activity_water_page,titles);
            this.context=context;
        }
        public View getView(int position, View view, ViewGroup parent){
            LayoutInflater inflater=context.getLayoutInflater();
            View rowView=inflater.inflate(R.layout.activity_water_page,null);
            ImageView imageView=rowView.findViewById(R.id.image);
            TextView title=rowView.findViewById(R.id.title);
            TextView place=rowView.findViewById(R.id.place);
            TextView price=rowView.findViewById(R.id.price);

            title.setText(titles[position]);
            imageView.setImageResource(images[position]);
            place.setText(places[position]);
            price.setText(prices[position]);
            return rowView;
        }
    }
}
