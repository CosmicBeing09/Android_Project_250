package com.example.raihan.hobbies;

public class profile_info {

String register_ImageUri,register_name,address,phone_no,user_name,hobby,occupation;
public profile_info(String register_ImageUri,String register_name,String address,String phone_no,
                    String user_name,String hobby,String occupation)
{
    this.register_ImageUri = register_ImageUri;
    this.address = address;
    this.register_name = register_name;
    this.phone_no = phone_no;
    this.hobby = hobby;
    this.occupation = occupation;
    this.user_name = user_name;
}
public profile_info(){}

    public String getRegister_ImageUri() {
        return register_ImageUri;
    }

    public void setRegister_ImageUri(String register_ImageUri) {
        this.register_ImageUri = register_ImageUri;
    }

    public String getRegister_name() {
        return register_name;
    }

    public void setRegister_name(String register_name) {
        this.register_name = register_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }
}
