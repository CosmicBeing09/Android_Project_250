package com.example.raihan.hobbies;

public class comment_object {

    String text,commentor,uri;
    public comment_object(String text,String commentor,String uri)
    {
        this.text =text;
        this.commentor = commentor;
        this.uri = uri;
    }
    public comment_object(){}

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCommentor() {
        return commentor;
    }

    public void setCommentor(String commentor) {
        this.commentor = commentor;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
