package com.example.pumpkinsoftware.travelmate.trip;

import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

public class Trip {
    private String trip_image;
    private String trip_name;
    private String group_number;
    //private ImageView group_image;
    private String budget_number;
    /*private ImageView budget_image;
    private Button more_button;
    private ImageView sharing_image;*/

    public Trip(String t_image, String n, String g, ImageView g_image, String b,
                ImageView b_image, Button btn, ImageView s_image) {
        trip_image = t_image;
        trip_name = n;
        group_number = g;
        //group_image = g_image;
        budget_number = b;
        //budget_image = b_image;
        //more_button = btn;
        //sharing_image = s_image;
    }

    public Trip(String t_image, String n, String g, int b) {
        trip_image = t_image;
        trip_name = n;
        group_number = g;
        budget_number = Integer.toString(b);
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

    private static int lastTripId = 0;

    // PER PROVA
    public static ArrayList<Trip> createTripsList(int n) {
        ArrayList<Trip> trips = new ArrayList<Trip>();

        for (int i = 1; i <= n; i++) {
            double d = (Math.random()*10);
            int max = i + (int) d;
            trips.add(new Trip("Trip " + ++lastTripId, "Trip " + lastTripId, i+"/"+max, i*10+10*max));
        }

        return trips;
    }

}
