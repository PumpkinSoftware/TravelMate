package com.example.pumpkinsoftware.travelmate;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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

public class ChatActivity extends AppCompatActivity {
    private Context context;
    private RequestQueue mRequestQueue;
    private String URL="https://debugtm.herokuapp.com/user/getProgressTripsByUser?";
    private ArrayList<Trip> trips;
    private String idToken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        //toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });
        context = this;

        final TextView noTripText = findViewById(R.id.noTripText);
        final ImageView noTripImg = findViewById(R.id.noTripImg);

        final ProgressBar progress = findViewById(R.id.indeterminateBar);
        final RecyclerView rvTrips = (RecyclerView) findViewById(R.id.recyclerview_chat);
        // Set layout manager to position the items
        rvTrips.setLayoutManager(new LinearLayoutManager(context));
        rvTrips.addItemDecoration(new DividerItemDecoration(rvTrips.getContext(), DividerItemDecoration.VERTICAL));
        trips = new ArrayList<Trip>();
        mRequestQueue = Volley.newRequestQueue(context);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        user.getIdToken(true)
                .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                    public void onComplete(@NonNull Task<GetTokenResult> task) {
                        if (task.isSuccessful()) {
                            idToken = task.getResult().getToken();
                            // Send token to your backend via HTTPS
                            new GetTripInteraction(context, rvTrips, progress,idToken).getChatTripsFromServer(URL, mRequestQueue, trips, noTripText,
                                    noTripImg);
                            // ...
                        } else {
                            // Handle error -> task.getException();
                            Toast.makeText(context, "Riprova", Toast.LENGTH_SHORT).show();
                        }
                    }
                });




    }
}
