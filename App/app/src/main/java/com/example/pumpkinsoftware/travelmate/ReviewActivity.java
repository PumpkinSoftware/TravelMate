package com.example.pumpkinsoftware.travelmate;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.pumpkinsoftware.travelmate.client_server_interaction.PostReview;
import com.example.pumpkinsoftware.travelmate.glide.GlideApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

public class ReviewActivity extends AppCompatActivity {
    public static final String EXTRA_PHOTO ="", EXTRA_UID = "";
    private Context context;
    private final short N = 5;  // N questions
    private boolean[] isSetted = new boolean[N];
    private final static String URL = "";
    FirebaseUser user;
    private String uid,uid2,photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        context = (Context) this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)    finishAfterTransition();
                else    finish();
            }
        });

        Bundle b = getIntent().getExtras();
        uid2 = b.getString(EXTRA_UID);
        photo=b.getString(EXTRA_PHOTO);

        user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null) return;
        uid = user.getUid();


        ImageView img = (ImageView) findViewById(R.id.profile);
        GlideApp.with(context)
                .load((photo.isEmpty()?R.drawable.blank_avatar:photo))
                .into(img);

        /*RatingBar ratingBar1 = findViewById(R.id.ratingBar1);
        RatingBar ratingBar2 = findViewById(R.id.ratingBar2);
        RatingBar ratingBar3 = findViewById(R.id.ratingBar3);
        RatingBar ratingBar4 = findViewById(R.id.ratingBar4);*/
        final RatingBar[] ratingBars = {findViewById(R.id.ratingBar1), findViewById(R.id.ratingBar2),
                                  findViewById(R.id.ratingBar3), findViewById(R.id.ratingBar4),findViewById(R.id.ratingBar5)};

        for(int i=0; i<N; i++)
            isSetted[i] = false;

        // Handling if all questions has been answered
        for(int i=0; i<N; i++) {
            final int idx = i;
            ratingBars[i].setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    isSetted[idx] = true;
                }
            });
        }


        final ProgressBar progress = findViewById(R.id.indeterminateBar);

        Button send = findViewById(R.id.buttonReview);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double sum = 0;
                boolean check=true;
                for(int i=0; i<N; i++) {
                    // A question has not been answered
                    if(isSetted[i]) // Do stuff
                         sum += ratingBars[i].getRating();
                    else check=false;

                }
                if(check) {
                    findViewById(R.id.scroll).setVisibility(View.GONE);
                    progress.setVisibility(View.VISIBLE);
                    // Double.toString(sum); // TODO
                    JSONObject recensione=new JSONObject();
                    try {
                        recensione.put("userUid",uid);
                        recensione.put("userToReview",uid2);
                        recensione.put("sumReview",sum);
                        recensione.put("sumReview1",ratingBars[0]);
                        recensione.put("sumReview2",ratingBars[1]);
                        recensione.put("sumReview3",ratingBars[2]);
                        recensione.put("sumReview4",ratingBars[3]);
                        recensione.put("sumReview5",ratingBars[4]);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    //new PostReview(context, progress).send(URL,recensione);
                }else{
                    Toast.makeText(context,"Devi rispondere a tutte le domande", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
