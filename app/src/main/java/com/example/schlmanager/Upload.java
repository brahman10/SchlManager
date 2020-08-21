package com.example.schlmanager;

public class Upload {

    String Name;
    String URL;
    String DOP;

    public Upload() {

    }


    public Upload(String Dop, String Url, String Name) {
        this.Name = Name;
        this.URL = Url;
        this.DOP = Dop;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getUrl() {
        return URL;
    }


    public void setUrl(String url) {
        this.URL = url;
    }

    public String getDop() {
        return DOP;
    }

    public void setDop(String Dop) {
        this.DOP = Dop;
    }

    public String toString(){
        return  "Name "+ Name+ " dop "+DOP+" url "+URL;
    }
}




