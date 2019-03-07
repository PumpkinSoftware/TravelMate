package com.example.pumpkinsoftware.travelmate.trip;

import com.example.pumpkinsoftware.travelmate.client_server_interaction.GetUserByUid;
import com.example.pumpkinsoftware.travelmate.client_server_interaction.ServerCallback;
import com.example.pumpkinsoftware.travelmate.user.User;

import org.json.JSONObject;

import java.util.ArrayList;

public class Trip {
    private final String id;
    private String trip_image;
    private String trip_name;
    private String descr;
    private String departure;
    private String dest;
    private String budget_number;
    private String start_date;
    private String end_date;
    private String group_number;
    private String tag;
    private String vehicle;
    private String owner;
    //private User[] partecipants;

    public Trip(String id, String t_image, String name, String description, String dep,
                String destination, int budget, String start, String end, String group, String s, String v, String ow) {
        this.id = id;
        trip_image = t_image;
        trip_name = name;
        descr = description;
        departure = dep;
        dest = destination;
        budget_number = Integer.toString(budget);
        start_date = start;
        end_date = end;
        group_number = group;
        tag=s;
        vehicle=v;
        owner = ow;
    }

    public String getId() {
        return id;
    }

    public String getImage(){
        return trip_image;
    }

    public String getName(){
        return trip_name;
    }

    public String getDescr() {
        return descr;
    }

    public String getDeparture() {
        return departure;
    }

    public String getDest() {
        return dest;
    }

    public String getBudget() {
        return budget_number;
    }

    public String getStartDate() {
        return start_date;
    }

    public String getEndDate() {
        return end_date;
    }

    public String getGroup() {
        return group_number;
    }

    public String getTag(){
        return tag;
    }

    public String getVehicle(){
        return vehicle;
    }

    public String getOwner() {
        return owner;
    }

    /*public String getPartecipants() {
        for(int i=0; i<group_number; i++)
    }*/

    public void setDescr(String description) {
        descr = description;
    }

    public void setGroup(String group) {
        group_number = group;
    }

    public void setBudget(String budget) {
        budget_number = budget;
    }

    public void setOwner(String o) {
        owner = o;
    }

}
