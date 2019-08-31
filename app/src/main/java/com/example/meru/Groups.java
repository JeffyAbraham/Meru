package com.example.meru;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Groups implements Serializable {
    public String GroupName;
    public String Organizer;
    public String Description;
    public String Location;
    public String Interests;
    String OrganizerId;




    public Groups(String GroupName, String Organizer, String Description, String Location, String Interests, String OrganizerId ) {


        this.GroupName = GroupName;
        this.Organizer = Organizer;
        this.Description=Description;
        this.Location=Location;
        this.Interests=Interests;
        this.OrganizerId=OrganizerId;

    }

}
