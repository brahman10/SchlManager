package com.example.schlmanager;

public class Doubt {

    String Question , id, post;


    public Doubt() {
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public Doubt(String question, String id, String post) {
        Question = question;
        this.id = id;
        this.post = post;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }
}
