package com.example.online_exam;

public class putPdf {


    public String name ;
    public String Url ;

    public putPdf() {

    }

    public putPdf(String name, String url) {
        this.name = name;
        Url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }
}
