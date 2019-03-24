package com.example.pumpkinsoftware.travelmate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.pumpkinsoftware.travelmate.client_server_interaction.GetTripInteraction;
import com.example.pumpkinsoftware.travelmate.client_server_interaction.GetUserByUid;
import com.example.pumpkinsoftware.travelmate.client_server_interaction.ServerCallback;
import com.example.pumpkinsoftware.travelmate.trip.Trip;
import com.example.pumpkinsoftware.travelmate.user.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONObject;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private Context context;
    private RequestQueue mRequestQueue;
    private String URL="https://debugtm.herokuapp.com/trip/lastTripsCreatedWithUser?limit=50&userUid=";
    FirebaseUser user;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        context = getContext();
        setHasOptionsMenu(true);

        final TextView noTripText = view.findViewById(R.id.noTripText);
        final ImageView noTripImg = view.findViewById(R.id.noTripImg);

        final ProgressBar progress = view.findViewById(R.id.indeterminateBar);
        final RecyclerView rvTrips = (RecyclerView) view.findViewById(R.id.recyclerview);
        // Set layout manager to position the items
        rvTrips.setLayoutManager(new LinearLayoutManager(context));

        user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null) return view;
        final String uid = user.getUid();

        mRequestQueue = Volley.newRequestQueue(context);
        new GetTripInteraction(context, rvTrips, progress).getTripsFromServer(URL+uid, mRequestQueue, noTripText, noTripImg);

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

                        mRequestQueue = Volley.newRequestQueue(context);
                        new GetTripInteraction(context, rvTrips, progress).getTripsFromServer(URL+uid, mRequestQueue, noTripText, noTripImg);
                        swipe.setRefreshing(false);

                    }
                },1500);
            }
        });

        ImageButton chat=(ImageButton) view.findViewById(R.id.button_chat);
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,ChatActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.sharing_menu, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:

                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBodyText = "Check it out. Your message goes here";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"Subject here");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
                context.startActivity(Intent.createChooser(sharingIntent, "Sharing Option"));
                return true;

            default:
                return false;
        }
    }
}