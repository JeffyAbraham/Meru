package com.example.meru;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ModelEvent extends JSONObject implements Serializable {

    public String EventName;
    public String Organizer;
    public String Date;
    public String Location;
    public String Description;
    public String GroupName;

    Map<String,String> GoingUsers=new HashMap<String, String>();






    public ModelEvent(String EventName, String Organizer, String Description, String Location,Map GoingUsers,String Date,String GrouName ) {


        this.EventName = EventName;
        this.Organizer = Organizer;
        this.Description=Description;
        this.Location=Location;
        this.GoingUsers=GoingUsers;
        this.GroupName=GrouName;
        this.Date=Date;


    }

}
