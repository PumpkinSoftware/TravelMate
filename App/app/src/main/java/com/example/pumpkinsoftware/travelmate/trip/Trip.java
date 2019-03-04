package com.example.pumpkinsoftware.travelmate.trip;

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
    //private User[] partecipants;

    public Trip(String id, String t_image, String name, String description, String dep,
                String destination, int budget, String start, String end, String group, String s) {
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

    private static int lastTripId = 0;

    //PER PROVA
    // Non posso togliere causa una dipendenza con ContentFragmentTravels
    public static ArrayList<Trip> createTripsList(int n) {
        ArrayList<Trip> trips = new ArrayList<Trip>();

        for (int i = 1; i <= n; i++) {
            double d = (Math.random()*10);
            int max = i + (int) d;
            trips.add(new Trip("Trip " + ++lastTripId, "Trip " + lastTripId, "Trip " + lastTripId,
                    "Splendido viaggio", "Roma","Venezia", i*10+10*max , "2019-06-03T00:00:00.000Z",
                    "23/4/19", i+"/"+max, "" + "/" ));
        }

        return trips;
    }

}
