package com.jinasoft.chatting;

public class ChatMessage {
    private String email;
    private String text;
    private String name;
    private String photourl;
    private String imageurl;
    private String date;

    private int viewType;


    public ChatMessage() {

    }

    public ChatMessage(String text,String email, String name, String photourl,String imageurl,String date){
        this.text = text;

        this.email = email;
        this.name = name;
        this.photourl=photourl;
        this.imageurl = imageurl;
        this.date = date;

        this.viewType = viewType;

    }



    public void setEmail(String id){
        this.email=email;
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


    public String getEmail(){
        return email;
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
    public int getviewType() {
        return viewType;
    }


}
