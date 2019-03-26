package com.example.pumpkinsoftware.travelmate.trip;

import java.io.Serializable;

public class Trip implements Serializable {
    private final String id;
    private String trip_image;
    private String trip_name;
    private String descr;
    private String departure;
    private String dest;
    private String budget_number;
    private String start_date;
    private String end_date;
    private int partecipants_number;
    private int group_number;
    private String tag;
    private String vehicle;
    private String owner;
    //private String[] partecipants; // String because we need only users' id

    public Trip(String id, String t_image, String name, String description, String dep,
                String destination, int budget, String start, String end, int partecipants_number, int group_number,
                String s, String v, String ow) {
        this.id = id;
        trip_image = t_image;
        trip_name = name;
        descr = description;
        departure = dep;
        dest = destination;
        budget_number = Integer.toString(budget);
        start_date = start;
        end_date = end;
        this.partecipants_number = partecipants_number;
        this.group_number = group_number;
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

    public int getPartecipantsNumber() {
        return partecipants_number;
    }

    public int getGroupNumber() {
        return group_number;
    }

    public String getGroup() {
        return partecipants_number+"/"+group_number;
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

    public void setImage(String image) { trip_image = image; }

    public void setName(String name) { trip_name = name; }

    public void setDescr(String description) { descr = description; }

    public void setDeparture(String dep) { departure = dep; }

    public void setDest(String destination) { dest = destination; }

    public void setBudget(String budget) { budget_number = budget; }

    public void setStartDate(String startDate) { start_date = startDate; }

    public void setEndDate(String endDate) { end_date = endDate; }

    public void setPartecipantsNumber(int p) {
        partecipants_number = p;
    }

    public void setGroup_number(int group) {
        group_number = group;
    }

    public void setTag(String t) {
        tag = t;
    }

    public void setVehicle(String v) {
        vehicle = v;
    }

    public void setOwner(String o) {
        owner = o;
    }
}
