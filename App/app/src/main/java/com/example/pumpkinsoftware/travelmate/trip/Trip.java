package com.example.pumpkinsoftware.travelmate.trip;

import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

public class Trip {
    private String id;
    private String trip_image;
    private String trip_name;
    private String group_number;
    private String budget_number;

    public Trip(String id, String t_image, String name, String group, int budget) {
        this.id = id;
        trip_image = t_image;
        trip_name = name;
        group_number = group;
        budget_number = Integer.toString(budget);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return trip_name;
    }

    public String getGroup() {
        return group_number;
    }

    public String getBudget() {
        return budget_number;
    }

    public void setGroup(String group) {
        group_number = group;
    }

    public void setBudget(String budget) {
        budget_number = budget;
    }

    private static int lastTripId = 0;

    // PER PROVA
    public static ArrayList<Trip> createTripsList(int n) {
        ArrayList<Trip> trips = new ArrayList<Trip>();

        for (int i = 1; i <= n; i++) {
            double d = (Math.random()*10);
            int max = i + (int) d;
            trips.add(new Trip("Trip " + ++lastTripId, "Trip " + lastTripId, "Trip " + lastTripId, i+"/"+max, i*10+10*max));
        }

        return trips;
    }

}
