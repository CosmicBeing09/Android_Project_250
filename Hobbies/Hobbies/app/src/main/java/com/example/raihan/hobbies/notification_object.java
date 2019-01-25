package com.example.raihan.hobbies;

public class notification_object {
    String user_name,user_imageUri,post_imageUri;
    String key;
    public notification_object(String user_name,String user_imageUri,String post_imageUri,String key)
    {

        this.user_name = user_name;
        this.user_imageUri = user_imageUri;
        this.post_imageUri = post_imageUri;
        this.key = key;
    }
    public notification_object(){}

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_imageUri() {
        return user_imageUri;
    }

    public void setUser_imageUri(String user_imageUri) {
        this.user_imageUri = user_imageUri;
    }

    public String getPost_imageUri() {
        return post_imageUri;
    }

    public void setPost_imageUri(String post_imageUri) {
        this.post_imageUri = post_imageUri;
    }
}
