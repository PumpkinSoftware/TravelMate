package com.example.pumpkinsoftware.travelmate;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.pumpkinsoftware.travelmate.client_server_interaction.PostReview;
import com.example.pumpkinsoftware.travelmate.glide.GlideApp;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;

import org.json.JSONException;
import org.json.JSONObject;

public class ReviewActivity extends AppCompatActivity {
    public static final String EXTRA_PHOTO ="travelmate_extra_ra_photo";
    public static final String EXTRA_UIDUU ="travelmate_extra_ra_uid";
    private Context context;
    private final short N = 5;  // N questions
    private boolean[] isSetted = new boolean[N];
    private final static String URL = Utils.SERVER_PATH + "user/addReview";
    FirebaseUser user;
    private String uid2,photo;
    private RequestQueue mQueue;

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
        uid2 = b.getString(EXTRA_UIDUU);
        //Log.i("useruid2",uid2);
        photo=b.getString(EXTRA_PHOTO);
        //Log.i("photo2",photo);

        user = FirebaseAuth.getInstance().getCurrentUser();


        mQueue = Volley.newRequestQueue(context);

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
                    final JSONObject recensione=new JSONObject();
                    try {
                        recensione.put("userToReview",uid2);
                        recensione.put("sumReview",sum/N);
                        recensione.put("sumReview1",ratingBars[0].getRating());
                        recensione.put("sumReview2",ratingBars[1].getRating());
                        recensione.put("sumReview3",ratingBars[2].getRating());
                        recensione.put("sumReview4",ratingBars[3].getRating());
                        recensione.put("sumReview5",ratingBars[4].getRating());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    user.getIdToken(true)
                            .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                                public void onComplete(@NonNull Task<GetTokenResult> task) {
                                    if (task.isSuccessful()) {
                                        String idToken = task.getResult().getToken();
                                        // Send token to your backend via HTTPS
                                        new PostReview(context, progress,mQueue,idToken).send(URL,recensione);
                                        // ...
                                    } else {
                                        // Handle error -> task.getException();
                                        Toast.makeText(context, "Riprova", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });


                    //Log.i("useruid3",uid2);
                    finish();
                }else{
                    Toast.makeText(context,"Devi rispondere a tutte le domande", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

}
