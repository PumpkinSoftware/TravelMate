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

import com.example.pumpkinsoftware.travelmate.client_server_interaction.PostReview;
import com.example.pumpkinsoftware.travelmate.glide.GlideApp;

public class ReviewActivity extends AppCompatActivity {
    private Context context;
    private final short N = 4;  // N questions
    private boolean[] isSetted = new boolean[N];

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

        ImageView img = (ImageView) findViewById(R.id.profile);
        GlideApp.with(context)
                .load(R.drawable.girl)
                .into(img);

        /*RatingBar ratingBar1 = findViewById(R.id.ratingBar1);
        RatingBar ratingBar2 = findViewById(R.id.ratingBar2);
        RatingBar ratingBar3 = findViewById(R.id.ratingBar3);
        RatingBar ratingBar4 = findViewById(R.id.ratingBar4);*/
        final RatingBar[] ratingBars = {findViewById(R.id.ratingBar1), findViewById(R.id.ratingBar2),
                                  findViewById(R.id.ratingBar3), findViewById(R.id.ratingBar4)};

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
                for(int i=0; i<N; i++) {
                    // A question has not been answered
                    if(!isSetted[i]) // Do stuff
                         sum += ratingBars[i].getRating();
                }

                findViewById(R.id.scroll).setVisibility(View.GONE);
                progress.setVisibility(View.VISIBLE);
                String url = "https://debugtm.herokuapp.com/user/getUserByUid?uid=" + Double.toString(sum); // TODO
                new PostReview(context, progress).send(url);
            }
        });
    }

}
