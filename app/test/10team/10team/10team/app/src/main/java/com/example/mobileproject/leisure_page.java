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

public class leisure_page extends AppCompatActivity {

    ListView list;
    String[] titles={
            "가오리 빙의 재미~! 플라잉피시!","6m 까지 뜬다! 볼룹점프!","계곡판 인디아나 존스!! 레프팅!","이 땅콩보트에서 떨어지지마~!","운동신경 좀 되니~?","뭐? 여름에도 스키를 탄다고!?"
    };
    String[] places={
            "가평","가평","삼척","가평","가평","삼척"
    };
    String[] prices={
            "1인당 15,000","1인당 10,000","1인당 25,000","1인당 18,000","1인당 17,000","1인당 20,000",
    };
    Integer[] images={R.drawable.flyingfish,R.drawable.jumppad,R.drawable.lefting,R.drawable.peanutboat,R.drawable.wakeboard,R.drawable.waterski};
    private String title;
    DatePickerDialog datePickerDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leisure_main);
        Water_CustomList adapter=new Water_CustomList(leisure_page.this);
        list=findViewById(R.id.list);
        list.setAdapter(adapter);
        if(!leisure_page.this.isFinishing()){
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    final Calendar c = Calendar.getInstance();
                    int mYear = c.get(Calendar.YEAR);
                    int mMonth = c.get(Calendar.MONTH);
                    int mDay = c.get(Calendar.DAY_OF_MONTH);
                    title = titles[position];
                    datePickerDialog = new DatePickerDialog(leisure_page.this, new DatePickerDialog.OnDateSetListener() {
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