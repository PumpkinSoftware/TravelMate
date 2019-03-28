package com.example.pumpkinsoftware.travelmate.content_fragment_travels;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.example.pumpkinsoftware.travelmate.Utils;
import com.example.pumpkinsoftware.travelmate.client_server_interaction.GetTripInteraction;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;

public class Tab1 extends Fragment {
    private final static String URL= Utils.SERVER_PATH + "user/getProgressTripsByUser?";
    private Context context;
    private RequestQueue mRequestQueue;
    private String idToken;
    private ProgressBar progress;
    private RecyclerView rvTrips;
    private TextView noTripText;
    private ImageView noTripImg;
    private FirebaseUser user;
    public final static String EXTRA_RV_POS = "travelmate_extra_hf_RV_POS";
    public static final int REQUEST_CODE = 3;
    public static final int REQUEST_CODE_UPDATE = 4;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recyclerview_travel, container, false);
        context = getContext();

        noTripText = view.findViewById(R.id.noTripText);
        noTripImg = view.findViewById(R.id.noTripImg);

        progress = view.findViewById(R.id.indeterminateBar);
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
                },1500);
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
                            new GetTripInteraction(context, rvTrips, progress, idToken).getTripsFromServer(URL, mRequestQueue, noTripText, noTripImg);
                            // ...
                        } else {
                            // Handle error -> task.getException();
                            Toast.makeText(context, "Riprova", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    // To call exclusively when user cames back from TravelDetailsActivity
    private void updateLayout(final int pos, final int offset) {
        mRequestQueue = Volley.newRequestQueue(context);
        user.getIdToken(true)
                .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                    public void onComplete(@NonNull Task<GetTokenResult> task) {
                        if (task.isSuccessful()) {
                            idToken = task.getResult().getToken();
                            // Send token to your backend via HTTPS
                            new GetTripInteraction(context, rvTrips, progress, idToken).getTripsFromServer(URL, mRequestQueue, noTripText,
                                    noTripImg, pos, offset);

                        } else {
                            // Handle error -> task.getException();
                            Toast.makeText(context, "Riprova", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {
            if(resultCode == Activity.RESULT_OK) {
                /*Bundle b = data.getExtras();
                int pos = b.getInt(EXTRA_RV_POS);
                updateLayout(pos);*/

                int index = -1;
                int top = -1;

                // Read current recyclerview position
                index = ((LinearLayoutManager) rvTrips.getLayoutManager()).findFirstVisibleItemPosition();
                View v = rvTrips.getChildAt(0);
                top = (v == null) ? 0 : (v.getTop() - rvTrips.getPaddingTop());
                // Set recyclerview position
                if(index != -1)
                    updateLayout(index, top);
            }
        }

        else if(requestCode == REQUEST_CODE_UPDATE && resultCode == Activity.RESULT_OK)
            updateLayout();
    }

}