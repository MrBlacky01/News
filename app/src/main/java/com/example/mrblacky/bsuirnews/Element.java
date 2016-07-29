package com.example.mrblacky.bsuirnews;

import android.graphics.Bitmap;

/**
 * Created by Mr.Blacky on 25.07.2016.
 */
public class Element {
    private String href;
    private String src;
    private String alt;
    private String date;
    private String theme;

    Element(){
        href = "null";
        src = "null";
        alt = "null";
        date = "null";
        theme = "null";
    }

    Element(String h, String s, String a, String date,String t){
        href = h;
        src = s;
        alt = a;
        this.date = date;
        theme = t;
    }

    String getHref(){
        return href;
    }
    String getSrc(){
        return src;
    }
    String getAlt(){
        return alt;
    }
    String getDate()
    {
        return  date;
    }
    String getTheme()
    {
        return  theme;
    }

    void setHref(String h){
        href = h;
    }
    void setSrc(String s){
        src = s;
    }
    void setAlt(String a){
        alt = a;
    }
    void setDate(String d)
    {
        date = d;
    }
    void setTheme(String t)
    {
        theme = t;
    }

}
