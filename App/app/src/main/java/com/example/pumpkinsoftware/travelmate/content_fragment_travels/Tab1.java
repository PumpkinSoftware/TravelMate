package com.example.pumpkinsoftware.travelmate.content_fragment_travels;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.pumpkinsoftware.travelmate.R;
import com.example.pumpkinsoftware.travelmate.client_server_interaction.GetTripInteraction;
import com.example.pumpkinsoftware.travelmate.trip.Trip;
import com.example.pumpkinsoftware.travelmate.trips_adapter.TripsAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class Tab1 extends Fragment {
    private final static String URL="https://debugtm.herokuapp.com/user/getProgressTripsByUser?userUid=";
    private Context context;
    private RequestQueue mRequestQueue;
    private ArrayList<Trip> trips;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recyclerview_travel2, container, false);
        context = getContext();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null) return view;
        final String uid=user.getUid();

        final ProgressBar progress = view.findViewById(R.id.indeterminateBar);
        final RecyclerView rvTrips = (RecyclerView) view.findViewById(R.id.recyclerview);
        // Set layout manager to position the items
        rvTrips.setLayoutManager(new LinearLayoutManager(context));
        trips=new ArrayList<Trip>();

        mRequestQueue= Volley.newRequestQueue(context);
        new GetTripInteraction(context, rvTrips, progress).getTripsFromServer(URL+uid, mRequestQueue, trips);

        //swipe da finire
        final SwipeRefreshLayout swipe = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe.setRefreshing(true);
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //temporaneo
                        rvTrips.setLayoutManager(new LinearLayoutManager(context));
                        trips=new ArrayList<Trip>();
                        mRequestQueue= Volley.newRequestQueue(context);
                        new GetTripInteraction(context, rvTrips, progress).getTripsFromServer(URL+uid,mRequestQueue,trips);
                        swipe.setRefreshing(false);

                    }
                },1500);
            }
        });
        return view;
    }


}