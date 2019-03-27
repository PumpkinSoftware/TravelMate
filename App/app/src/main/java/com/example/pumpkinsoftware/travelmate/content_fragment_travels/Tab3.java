package com.example.pumpkinsoftware.travelmate.content_fragment_travels;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
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
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.pumpkinsoftware.travelmate.R;
import com.example.pumpkinsoftware.travelmate.client_server_interaction.GetTripInteraction;
import com.example.pumpkinsoftware.travelmate.trip.Trip;
import com.example.pumpkinsoftware.travelmate.trips_adapter.TripsAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;

import java.util.ArrayList;

public class Tab3 extends Fragment {
    private final static String URL = "https://debugtm.herokuapp.com/user/getPassedTripsByUser?";
    private Context context;
    private RequestQueue mRequestQueue;
    private String idToken;
    private ProgressBar progress;
    private RecyclerView rvTrips;
    private TextView noTripText;
    private ImageView noTripImg;
    private FirebaseUser user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recyclerview_travel, container, false);
        context = getContext();

        progress = view.findViewById(R.id.indeterminateBar);
        noTripText = view.findViewById(R.id.noTripText);
        noTripImg = view.findViewById(R.id.noTripImg);

        rvTrips = (RecyclerView) view.findViewById(R.id.recyclerview);
        // Set layout manager to position the items
        rvTrips.setLayoutManager(new LinearLayoutManager(context));
        user = FirebaseAuth.getInstance().getCurrentUser();
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
                        updateLayout();
                        swipe.setRefreshing(false);

                    }
                }, 1500);
            }
        });
        return view;
    }

    private void updateLayout() {
        mRequestQueue = Volley.newRequestQueue(context);
        user.getIdToken(true)
                .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                    public void onComplete(@NonNull Task<GetTokenResult> task) {
                        if (task.isSuccessful()) {
                            idToken = task.getResult().getToken();
                            // Send token to your backend via HTTPS
                            new GetTripInteraction(context, rvTrips, progress,idToken).getTripsFromServer(URL, mRequestQueue, noTripText, noTripImg);
                            // ...
                        } else {
                            // Handle error -> task.getException();
                            Toast.makeText(context, "Riprova", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

}