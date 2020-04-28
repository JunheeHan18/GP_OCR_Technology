package data;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class TransTextData {
    @SerializedName("title")
    String title;

    @SerializedName("content")
    String content;

    public TransTextData(String content){
        Date today = new Date();
        this.title=Integer.toString(today.getMonth())+"월 "+Integer.toString(today.getDay())+"일";
        this.content=content;
    }

    public TransTextData(String title,String content){
        this.title=title;
        this.content=content;
    }
}
