package com.example.pumpkinsoftware.travelmate;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.pumpkinsoftware.travelmate.client_server_interaction.GetTripInteraction;
import com.example.pumpkinsoftware.travelmate.trip.Trip;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;

import java.util.ArrayList;

public class SearchResult extends Activity {
    private RequestQueue mRequestQueue;
    private ArrayList<Trip> trips;
    public final static String EXTRA_QUERY = "travelmate_extra_sr_QUERY";
    FirebaseUser user;

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

        final ProgressBar progress = findViewById(R.id.indeterminateBar);
        mRequestQueue = Volley.newRequestQueue(this);
        user = FirebaseAuth.getInstance().getCurrentUser();
        user.getIdToken(true)
                .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                    public void onComplete(@NonNull Task<GetTokenResult> task) {
                        if (task.isSuccessful()) {
                            String idToken = task.getResult().getToken();
                            // Send token to your backend via HTTPS
                            new GetTripInteraction(getApplicationContext(), rvTrips, progress,idToken).getTripsFromServer(query, mRequestQueue, trips);
                            // ...
                        } else {
                            // Handle error -> task.getException();
                            Toast.makeText(getApplicationContext(), "Riprova", Toast.LENGTH_SHORT).show();
                        }
                    }
                });




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
                        user.getIdToken(true)
                                .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                                    public void onComplete(@NonNull Task<GetTokenResult> task) {
                                        if (task.isSuccessful()) {
                                            String idToken = task.getResult().getToken();
                                            // Send token to your backend via HTTPS
                                            new GetTripInteraction(getApplicationContext(), rvTrips, progress,idToken).getTripsFromServer(query, mRequestQueue, trips);
                                            // ...
                                        } else {
                                            // Handle error -> task.getException();
                                            Toast.makeText(getApplicationContext(), "Riprova", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                        swipe.setRefreshing(false);
                    }
                },1500);
            }
        });
    }

}
