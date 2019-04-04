package com.example.pumpkinsoftware.travelmate;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.pumpkinsoftware.travelmate.client_server_interaction.GetUserByUid;
import com.example.pumpkinsoftware.travelmate.utils.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;

public class ReviewListActivity extends AppCompatActivity {
    private Context context;
    private RequestQueue mRequestQueue;
    private TextView noTripText;
    private ImageView noTripImg;
    private String URL=Utils.SERVER_PATH + "user/leftReviews?";
    FirebaseUser user;
    RecyclerView rvUser;

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

            noTripText = findViewById(R.id.noTripText);
            noTripImg = findViewById(R.id.noTripImg);

            rvUser = (RecyclerView) findViewById(R.id.recyclerview_review);
            // Set layout manager to position the items
            rvUser.setLayoutManager(new LinearLayoutManager(context));
            rvUser.addItemDecoration(new DividerItemDecoration(rvUser.getContext(), DividerItemDecoration.VERTICAL));

            mRequestQueue= Volley.newRequestQueue(context);
            user = FirebaseAuth.getInstance().getCurrentUser();
            user.getIdToken(true)
                    .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                        public void onComplete(@NonNull Task<GetTokenResult> task) {
                            if (task.isSuccessful()) {
                                 String idToken = task.getResult().getToken();
                                // Send token to your backend via HTTPS
                                new GetUserByUid(context,rvUser,idToken).getUserReviewFromServer(URL, mRequestQueue,
                                        noTripText, noTripImg);
                                // ...
                            } else {
                                // Handle error -> task.getException();
                                Toast.makeText(context, "Riprova", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


        }
    @Override
    protected void onResume() {
        super.onResume();
        user = FirebaseAuth.getInstance().getCurrentUser();
        user.getIdToken(true)
                .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                    public void onComplete(@NonNull Task<GetTokenResult> task) {
                        if (task.isSuccessful()) {
                            String idToken = task.getResult().getToken();
                            // Send token to your backend via HTTPS
                            new GetUserByUid(context,rvUser,idToken).getUserReviewFromServer(URL, mRequestQueue,
                                    noTripText, noTripImg);
                            // ...
                        } else {
                            // Handle error -> task.getException();
                            Toast.makeText(context, "Riprova", Toast.LENGTH_SHORT).show();
                        }
                    }
                });



    }
}
