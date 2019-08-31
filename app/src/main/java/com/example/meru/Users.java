package com.example.meru;

public class Users {

    public String username;
    public String UserId;
    public String FacebookId;

    public Users() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Users(String username, String UserId,String FacebookId) {
        this.username = username;
        this.UserId = UserId;
        this.FacebookId=FacebookId;
    }

}