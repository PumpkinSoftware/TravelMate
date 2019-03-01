package com.example.pumpkinsoftware.travelmate;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.pumpkinsoftware.travelmate.client_server_interaction.ClientServerInteraction;
import com.example.pumpkinsoftware.travelmate.trip.Trip;

import java.util.ArrayList;

public class SearchResult extends Activity {
    private RequestQueue mRequestQueue;
    private ArrayList<Trip> trips;
    public final static String EXTRA_QUERY = "travelmate_extra_sr_QUERY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        //TOOLBAR
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
       /* setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Risultati");
        toolbar.setTitle("Risultati");
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));*/
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });
        //FINE

        Bundle b = getIntent().getExtras();
        final String query = b.getString(EXTRA_QUERY);

        final RecyclerView rvTrips = (RecyclerView) findViewById(R.id.recyclerview);
        // Set layout manager to position the items
        rvTrips.setLayoutManager(new LinearLayoutManager(this));
        trips = new ArrayList<Trip>();

        mRequestQueue= Volley.newRequestQueue(this);
        new ClientServerInteraction(this,rvTrips).getTripsFromServer(query,mRequestQueue,trips);

        final SwipeRefreshLayout swipe = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe.setRefreshing(true);
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        rvTrips.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        trips = new ArrayList<Trip>();
                        mRequestQueue= Volley.newRequestQueue(getApplicationContext());
                        new ClientServerInteraction(getApplication(),rvTrips).getTripsFromServer(query,mRequestQueue,trips);
                        swipe.setRefreshing(false);
                    }
                },1500);
            }
        });
    }

}
