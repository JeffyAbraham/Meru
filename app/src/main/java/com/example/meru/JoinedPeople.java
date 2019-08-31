package com.example.meru;

public class JoinedPeople {

    public String username;
    public String UserId;


    public JoinedPeople() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public JoinedPeople(String username, String UserId) {
        this.username = username;
        this.UserId = UserId;

    }

}
