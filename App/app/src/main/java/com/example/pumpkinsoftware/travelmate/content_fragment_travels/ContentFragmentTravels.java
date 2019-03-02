package com.example.pumpkinsoftware.travelmate.content_fragment_travels;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pumpkinsoftware.travelmate.R;
import com.example.pumpkinsoftware.travelmate.trip.Trip;
import com.example.pumpkinsoftware.travelmate.trips_adapter.TripsAdapter;

import java.util.ArrayList;

public class ContentFragmentTravels extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recyclerview_travel, container, false);
        Context context = getContext();

        RecyclerView rvContacts = (RecyclerView) view.findViewById(R.id.recyclerview);
        /*GetTripInteraction cs = new GetTripInteraction(context);
        cs.getTripsFromServer("http://localhost:8095/trip/allTrips/", );*/

        // Initialize trips
        ArrayList<Trip> trips = Trip.createTripsList(20);
        // Create adapter passing in the sample user data
        TripsAdapter adapter = new TripsAdapter(trips);
        // Attach the adapter to the recyclerview to populate items
        rvContacts.setAdapter(adapter);
        // Set layout manager to position the items
        rvContacts.setLayoutManager(new LinearLayoutManager(context));
        return view;
    }

}