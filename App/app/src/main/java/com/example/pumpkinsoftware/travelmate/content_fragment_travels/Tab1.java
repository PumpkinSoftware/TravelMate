package com.example.pumpkinsoftware.travelmate.content_fragment_travels;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.pumpkinsoftware.travelmate.R;
import com.example.pumpkinsoftware.travelmate.client_server_interaction.GetTripInteraction;
import com.example.pumpkinsoftware.travelmate.client_server_interaction.ServerCallback;
import com.example.pumpkinsoftware.travelmate.trip.Trip;
import com.example.pumpkinsoftware.travelmate.trips_adapter.TripsAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONObject;

import java.util.ArrayList;

public class Tab1 extends Fragment {
    private final static String URL="https://debugtm.herokuapp.com/user/getProgressTripsByUser?userUid=";
    private Context context;
    private RequestQueue mRequestQueue;
    private String uid;
    private ProgressBar progress;
    private RecyclerView rvTrips;
    private TextView noTripText;
    private ImageView noTripImg;
    public final static String EXTRA_RV_POS = "travelmate_extra_hf_RV_POS";
    public static final int REQUEST_CODE = 3;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recyclerview_travel, container, false);
        context = getContext();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null) return view;
        uid = user.getUid();

        noTripText = view.findViewById(R.id.noTripText);
        noTripImg = view.findViewById(R.id.noTripImg);

        progress = view.findViewById(R.id.indeterminateBar);
        rvTrips = (RecyclerView) view.findViewById(R.id.recyclerview);
        // Set layout manager to position the items
        rvTrips.setLayoutManager(new LinearLayoutManager(context));
        updateLayout();

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
                        updateLayout();
                        swipe.setRefreshing(false);

                    }
                },1500);
            }
        });

        return view;
    }

    private void updateLayout() {
        mRequestQueue = Volley.newRequestQueue(context);
        new GetTripInteraction(context, rvTrips, progress).getTripsFromServer(URL+uid, mRequestQueue, noTripText, noTripImg);
    }

    // To call exclusively when user cames back from TravelDetailsActivity
    private void updateLayout(int pos) {
        mRequestQueue = Volley.newRequestQueue(context);
        new GetTripInteraction(context, rvTrips, progress).getTripsFromServer(URL+uid, mRequestQueue, pos);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if(resultCode == Activity.RESULT_OK) {
                Bundle b = data.getExtras();
                int pos = b.getInt(EXTRA_RV_POS);
                updateLayout(pos);
            }
        }
    }

}