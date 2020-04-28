package data;

import com.google.gson.annotations.SerializedName;

import java.sql.Date;

public class BookmarkData {
    @SerializedName("title")
    String title;

    @SerializedName("content")
    String content;

    @SerializedName("save_date")
    String save_date;


    public BookmarkData(String title, String content,String save_date) {
        this.title = title;
        this.content = content;
        this.save_date = save_date;

    }
}
