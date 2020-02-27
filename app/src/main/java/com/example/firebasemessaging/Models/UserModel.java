package com.example.firebasemessaging.Models;

public class UserModel {
    public String id;
    public String imageurl;
    private String username;

    public  UserModel(){

    }


    public String getUserId() {
        return id;
    }

    public void setUserId(String userId) {
        this.id = userId;
    }

    public String getUserDp() {
        return imageurl;
    }

    public void setUserDp(String userDp) {
        this.imageurl = userDp;
    }

    public String getUserName() {
        return username;
    }

    public void setUserName(String userName) {
        this.username = userName;
    }
}
