package com.example.meru;

import android.widget.ImageView;

public class ModelJoinedGroups {
    public String GroupName;
    public String Link;


    public ModelJoinedGroups() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public ModelJoinedGroups(String GroupName,String Link) {
        this.GroupName = GroupName;
        this.Link = Link;

    }
}
