package com.example.pumpkinsoftware.travelmate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
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
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.pumpkinsoftware.travelmate.client_server_interaction.GetTripInteraction;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;

import io.apptik.widget.Util;

public class HomeFragment extends Fragment {
    private Context context;
    private RequestQueue mRequestQueue;
    private String URL= Utils.SERVER_PATH + "trip/lastTripsCreatedWithUser?limit=50";
    FirebaseUser user;
    private ProgressBar progress;
    private RecyclerView rvTrips;
    private TextView noTripText;
    private ImageView noTripImg;
    public final static String EXTRA_RV_POS = "travelmate_extra_hf_RV_POS";
    public static final int REQUEST_CODE = 2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        context = getContext();
        setHasOptionsMenu(true);

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
                        //temporaneo
                        updateLayout();
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

        ImageButton review=(ImageButton) view.findViewById(R.id.button_review);
        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,ReviewListActivity.class);
                startActivity(intent);
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
                            String idToken = task.getResult().getToken();
                            // Send token to your backend via HTTPS
                            new GetTripInteraction(context, rvTrips, progress,idToken).getTripsFromServer(URL, mRequestQueue, noTripText, noTripImg);

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
                            String idToken = task.getResult().getToken();
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
    }

}