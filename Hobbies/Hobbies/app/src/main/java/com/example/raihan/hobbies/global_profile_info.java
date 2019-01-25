package com.example.raihan.hobbies;

public class global_profile_info {

    String name,location,phone_no,global_imageUri;
    public global_profile_info(String name,String location,String phone_no,String global_imageUri)
    {
        this.name = name;
        this.global_imageUri = global_imageUri;
        this.location = location;
        this.phone_no = phone_no;
    }
    public global_profile_info(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getGlobal_imageUri() {
        return global_imageUri;
    }

    public void setGlobal_imageUri(String global_imageUri) {
        this.global_imageUri = global_imageUri;
    }
}
