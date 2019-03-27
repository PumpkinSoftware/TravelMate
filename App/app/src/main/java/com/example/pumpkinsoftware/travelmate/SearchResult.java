package com.example.pumpkinsoftware.travelmate;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
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

public class SearchResult extends AppCompatActivity {
    private RequestQueue mRequestQueue;
    public final static String EXTRA_QUERY = "travelmate_extra_sr_QUERY";
    private String query;
    private ProgressBar progress;
    private RecyclerView rvTrips;
    private TextView noTripText;
    private ImageView noTripImg;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        //TOOLBAR
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });



        Bundle b = getIntent().getExtras();
        query = b.getString(EXTRA_QUERY);

        noTripText = findViewById(R.id.noTripText);
        noTripImg = findViewById(R.id.noTripImg);

        rvTrips = (RecyclerView) findViewById(R.id.recyclerview);
        // Set layout manager to position the items
        rvTrips.setLayoutManager(new LinearLayoutManager(this));

        progress = findViewById(R.id.indeterminateBar);
        user = FirebaseAuth.getInstance().getCurrentUser();
        updateLayout();

        final SwipeRefreshLayout swipe = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe.setRefreshing(true);
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        updateLayout();
                        swipe.setRefreshing(false);
                    }
                },1500);
            }
        });
    }

    private void updateLayout() {
        mRequestQueue = Volley.newRequestQueue(this);
        user.getIdToken(true)
                .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                    public void onComplete(@NonNull Task<GetTokenResult> task) {
                        if (task.isSuccessful()) {
                            String idToken = task.getResult().getToken();
                            // Send token to your backend via HTTPS
                            new GetTripInteraction(SearchResult.this, rvTrips, progress, idToken).getTripsFromServer(query, mRequestQueue, noTripText, noTripImg);

                        } else {
                            // Handle error -> task.getException();
                            Toast.makeText(SearchResult.this, "Riprova", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

}
