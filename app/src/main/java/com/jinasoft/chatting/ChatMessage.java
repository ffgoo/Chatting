package com.jinasoft.chatting;

public class ChatMessage {
    private String id;
    private String text;
    private String name;
    private String photourl;
    private String imageurl;
    private String date;


    public ChatMessage() {

    }

    public ChatMessage(String text, String name, String photourl,String imageurl,String date){
        this.text = text;

        this.name = name;
        this.photourl=photourl;
        this.imageurl = imageurl;
        this.date = date;

    }



    public void setId(String id){
        this.id=id;
    }
    public void setText(String text){
        this.text=text;
    }
    public void setName(String name){
        this.name=name;
    }
    public void setPhotourl(String photourl){
        this.photourl=photourl;
    }
    public void setImageurl(String imageurl){
        this.imageurl=imageurl;
    }
    public void setDate(String date){
        this.date = date;
    }


    public String getId(){
        return id;
    }
    public  String getText(){
        return text;
    }
    public  String getName(){
        return name;
    }
    public  String getPhotourl(){
        return photourl;
    }
    public  String getImageurl(){
        return imageurl;
    }
    public String getDate(){
        return date;
    }


}
