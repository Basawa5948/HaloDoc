package com.droid.basawa.halodoc;

public class POJO {

    String created_at;
    String title;
    String url;
    String author;

    public POJO(String created_at,String title,String url,String author){
        this.created_at = created_at;
        this.author = author;
        this.title = title;
        this.url = url;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
