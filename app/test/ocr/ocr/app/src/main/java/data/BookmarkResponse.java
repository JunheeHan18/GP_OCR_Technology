package data;

import com.google.gson.annotations.SerializedName;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Result;

import retrofit2.Call;

public class BookmarkResponse {

        private List<ResultItem> resultItem;

        @SerializedName("idx")
        private Long idx;
        @SerializedName("title")
        private String title;

        @SerializedName("content")
        private String content;

        @SerializedName("save_date")
        private String save_date;

        public void setIdx(Long idx){this.idx = idx;}
        public Long getIdx(){return idx;}
        public String getTitle() {
            return title;
        }
        public String getContent() {
            return content;
        }
        public String getSave_date(){ return save_date; }
        public void setTitle(String title){this.title = title;}
        public void setContent(String content){this.content = content;}
        public void setSave_date(String save_date){this.save_date=save_date;}
    public List<ResultItem> getResultItem() {
        return resultItem;
    }

    public class ResultItem {

        @SerializedName("idx")
        private Long idx;
        @SerializedName("title")
        private String title;

        @SerializedName("content")
        private String content;

        @SerializedName("save_date")
        private String save_date;

        public Long getIdx(){

            return idx;
        }

        public String getTitle() {
            return title;
        }

        public String getContent() {
            return content;
        }

        public String getSave_date() {
            return save_date;
        }

        public void setIdx(Long idx){
            this.idx = idx;
        }

        public void setTitle(String title){
            this.title = title;
        }

        public void setContent(String content){
            this.content = content;
        }

        public void setSave_date(String save_date){
            this.save_date = save_date;
        }

    }
}


//참고사이트("https://ppizil.tistory.com/27")