package com.example.junhe.ocr;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class bookmarkAdapter extends BaseAdapter {

    private ArrayList<ListItem> UserList = new ArrayList<ListItem>();

    public bookmarkAdapter() {

    }

    @Override
    public int getCount() {
        return UserList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_main, null, true);
        }
        TextView titleTextView = (TextView) convertView.findViewById(R.id.title);
        TextView contentTextView = (TextView) convertView.findViewById(R.id.content);
        TextView dateTextView = (TextView) convertView.findViewById(R.id.date);

        ListItem listItem = UserList.get(position);

        titleTextView.setText(listItem.getTitle());
        contentTextView.setText(listItem.getContent());
        dateTextView.setText(listItem.getDate());

        return convertView;
    }

    public void addItem(String title, String Content, String date) {
        ListItem item = new ListItem();

        item.setContent(title);
        item.setTitle(Content);
        item.setDate(date);

        UserList.add(item);
    }
}