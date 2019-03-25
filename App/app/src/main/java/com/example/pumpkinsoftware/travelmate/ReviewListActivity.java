package com.example.pumpkinsoftware.travelmate;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.pumpkinsoftware.travelmate.client_server_interaction.GetUserByUid;
import com.example.pumpkinsoftware.travelmate.trip.Trip;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class ReviewListActivity extends AppCompatActivity {
    private Context context;
    private RequestQueue mRequestQueue;
    private String URL="https://debugtm.herokuapp.com/user/leftReviews?userUid=";

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_review_list);

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

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user == null) return;

            final String uid = user.getUid();
            final RecyclerView rvUser = (RecyclerView) findViewById(R.id.recyclerview_review);
            // Set layout manager to position the items
            rvUser.setLayoutManager(new LinearLayoutManager(context));
            rvUser.addItemDecoration(new DividerItemDecoration(rvUser.getContext(), DividerItemDecoration.VERTICAL));

            mRequestQueue= Volley.newRequestQueue(context);
            new GetUserByUid(context,rvUser).getUserReviewFromServer(URL+uid,mRequestQueue);

        }
}
