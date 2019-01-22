package com.example.raihan.hobbies;

import android.os.Parcel;
import android.os.Parcelable;

public class profile_info implements Parcelable {

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

    protected profile_info(Parcel in) {
        register_ImageUri = in.readString();
        register_name = in.readString();
        address = in.readString();
        phone_no = in.readString();
        user_name = in.readString();
        hobby = in.readString();
        occupation = in.readString();
    }

    public static final Creator<profile_info> CREATOR = new Creator<profile_info>() {
        @Override
        public profile_info createFromParcel(Parcel in) {
            return new profile_info(in);
        }

        @Override
        public profile_info[] newArray(int size) {
            return new profile_info[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(register_ImageUri);
        parcel.writeString(register_name);
        parcel.writeString(address);
        parcel.writeString(phone_no);
        parcel.writeString(user_name);
        parcel.writeString(hobby);
        parcel.writeString(occupation);
    }
}
