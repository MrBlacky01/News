package com.example.mrblacky.bsuirnews;

class ShortNewsFromMainPage {
    private String href;
    private String src;
    private String alt;
    private String date;
    private String theme;

    ShortNewsFromMainPage(){
        href = "null";
        src = "null";
        alt = "null";
        date = "null";
        theme = "null";
    }

    ShortNewsFromMainPage(String h, String s, String a, String date,String t){
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
