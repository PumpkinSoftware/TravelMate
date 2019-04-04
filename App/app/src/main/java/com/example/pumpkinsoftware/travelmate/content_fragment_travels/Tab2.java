package com.example.pumpkinsoftware.travelmate.content_fragment_travels;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.example.pumpkinsoftware.travelmate.R;
import com.example.pumpkinsoftware.travelmate.utils.Utils;
import com.example.pumpkinsoftware.travelmate.trip.Trip;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class Tab2 extends Fragment {
    private final static String URL= Utils.SERVER_PATH + "trip/allTrips/";
    private Context context;
    private RequestQueue mRequestQueue;
    private ArrayList<Trip> trips;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recyclerview_travel, container, false);
        context = getContext();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null) return view;

        user.getUid();

        /*final ProgressBar progress = view.findViewById(R.id.indeterminateBar); //xml da controllare
        final RecyclerView rvTrips = (RecyclerView) view.findViewById(R.id.recyclerview);
        // Set layout manager to position the items
        rvTrips.setLayoutManager(new LinearLayoutManager(context));
        trips=new ArrayList<Trip>();

        mRequestQueue= Volley.newRequestQueue(context);
        new GetTripInteraction(context, rvTrips, progress).getTripsFromServer(URL, mRequestQueue, trips);

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
                        new GetTripInteraction(context, rvTrips, progress).getTripsFromServer(URL,mRequestQueue,trips);
                        swipe.setRefreshing(false);

                    }
                },1500);
            }
        });*/
        return view;
    }


}