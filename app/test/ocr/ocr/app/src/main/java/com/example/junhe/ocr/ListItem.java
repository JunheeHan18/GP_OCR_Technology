package com.example.junhe.ocr;

public class ListItem {
    private String mytitle;
    private String mycontent;
    private String mydate;
    public ListItem(){

    }
    public ListItem(String mytitle, String mycontent,String mydate){
        this.mytitle = mytitle;
        this.mycontent = mycontent;
        this.mydate = mydate;
    }
    public void setTitle(String title){
        mytitle = title;
    }
    public void setContent(String content){
        mycontent = content;
    }
    public void setDate(String date){
        mydate = date;
    }
    public String getTitle(){
        return this.mytitle;
    }
    public String getContent(){
        return this.mycontent;
    }
    public String getDate(){
        return this.mydate;
    }
}
