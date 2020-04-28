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

public class camping_page extends AppCompatActivity {

    ListView list;
    String[] titles={
            "가까운 거리 서울 캠핑","캠핑도 하고! 레저도 하고!","닭갈비와 캠핑","제주에서 캠핑을?!","도시 속 캠핑의 맛","캠핑도 하고 바다구경도 하고~~"
    };
    String[] places={
            "노원구","가평","춘천","제주도","마포구","강릉"
    };
    String[] prices={
            "1박 기준 1인당 30,000","1박 기준 1인당 28,000","1박 기준 1인당 32,000","1박 기준 1인당 25,000","1박 기준 1인당 26,000","1박 기준 1인당 29,000",
    };
    Integer[] images={R.drawable.camping1,R.drawable.camping2,R.drawable.camping3,R.drawable.camping4,R.drawable.camping5,R.drawable.camping6};
    private String title;
    private DatePickerDialog datePickerDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camping_main);
        Water_CustomList adapter=new Water_CustomList(camping_page.this);
        list=findViewById(R.id.list);
        list.setAdapter(adapter);
        if(!camping_page.this.isFinishing()){
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    final Calendar c = Calendar.getInstance();
                    int mYear = c.get(Calendar.YEAR);
                    int mMonth = c.get(Calendar.MONTH);
                    int mDay = c.get(Calendar.DAY_OF_MONTH);
                    title = titles[position];
                    datePickerDialog = new DatePickerDialog(camping_page.this, new DatePickerDialog.OnDateSetListener() {
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