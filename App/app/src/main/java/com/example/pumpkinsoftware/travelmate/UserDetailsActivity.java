package com.example.pumpkinsoftware.travelmate;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.pumpkinsoftware.travelmate.client_server_interaction.GetTripInteraction;
import com.example.pumpkinsoftware.travelmate.client_server_interaction.GetUserByUid;
import com.example.pumpkinsoftware.travelmate.client_server_interaction.ServerCallback;
import com.example.pumpkinsoftware.travelmate.glide.GlideApp;
import com.example.pumpkinsoftware.travelmate.user.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;

import org.json.JSONObject;

public class UserDetailsActivity extends AppCompatActivity {
    public final static String EXTRA_UID = "travelmate_extra_uda_USER_UID";
    private final static String URL = Utils.SERVER_PATH + "user/getUserByUid";
    private Context context;
    private boolean so_prev_lol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partecipant_details);
        context = (Context) this;

        Bundle b = getIntent().getExtras();
        final String uid = b.getString(EXTRA_UID);
        final ProgressBar progressBar = findViewById(R.id.indeterminateBar);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        user.getIdToken(true)
                .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                    public void onComplete(@NonNull Task<GetTokenResult> task) {
                        if (task.isSuccessful()) {
                            String idToken = task.getResult().getToken();
                            // Send token to your backend via HTTPS
                            // TODO POST INSTEAD OF GET TO GET PARTECIPANT
                            final GetUserByUid server =  new GetUserByUid(context, progressBar, idToken);
                            server.getUserFromServer(URL, uid, GetUserByUid.request.PARTECIPANT, new ServerCallback() {
                                        @Override
                                        public void onSuccess(JSONObject response) {
                                            loadUser(server);
                                        }
                                    }
                            );
                        } else {
                            // Handle error -> task.getException();
                            Toast.makeText(context, "Riprova", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)    finishAfterTransition();
                else    finish();
            }
        });
    }

    private boolean loadUser(GetUserByUid server) {
        User mUser = server.getUser();

        if(mUser == null) return false;

        ImageView img = (ImageView) findViewById(R.id.profile);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            img.setTransitionName("image");

        loadImg(mUser.getPhotoProfile(), img);

        img = findViewById(R.id.header_cover_image);
        GlideApp.with(context)
                .load(mUser.getCover().isEmpty()?R.drawable.blank_cover:mUser.getCover())
                .into(img);

        TextView txt = findViewById(R.id.name);
        String ns = mUser.getName()+ " "+ mUser.getSurname(); //così non rompe
        txt.setText(ns);

        txt = findViewById(R.id.bio);
        txt.setText(mUser.getDescr());
        // Justified text alignment
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            txt.setText(mUser.getDescr());
            txt.setJustificationMode(Layout.JUSTIFICATION_MODE_INTER_WORD);
        } else {
            final WebView v = (WebView) findViewById(R.id.bio_for_older_versions);
            String text = "<html><body><p align=\"justify\">";
            text += mUser.getDescr();
            text += "</p></body></html>";
            /*
            v.loadData(text, "text/html", "utf-8");
            v.setVisibility(View.VISIBLE);
            txt.setVisibility(View.GONE);*/
            ((WebView) v.findViewById(R.id.bio_for_older_versions)).loadData(text, "text/html; charset=UTF-8", "utf-8");
            // Now I've to change the below param of the below elements
            /*final RelativeLayout layout = view.findViewById(R.id.layout2);
            RelativeLayout.LayoutParams params= new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.BELOW, R.id.bio_for_older_versions);
            layout.setLayoutParams(params);*/
        }

        txt = findViewById(R.id.age2);
        txt.setText(String.valueOf(mUser.getAge()));

        txt = findViewById(R.id.gender2);
        txt.setText(mUser.getGender());

        txt = findViewById(R.id.rel2);
        txt.setText(mUser.getRelationship());

        txt = findViewById(R.id.email2);
        txt.setText(mUser.getEmail());

        txt = findViewById(R.id.n_review);
        String n="( "+(String.valueOf(mUser.getNumReviews()))+" )"; //così non rompe
        txt.setText(n);

        txt = findViewById(R.id.rating);
        txt.setText(String.valueOf((mUser.getRank())));

        RatingBar ratingBar = findViewById(R.id.ratingBar);
        ratingBar.setRating((float) mUser.getRank());

        return true;
    }

    private void loadImg(String img, ImageView imgv) {
        so_prev_lol = false;
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            postponeEnterTransition();
        else {
            so_prev_lol = true;
            supportPostponeEnterTransition();
        }

        GlideApp.with(this)
                .load((img.isEmpty())?(R.drawable.blank_avatar):(img))
                .placeholder(R.mipmap.placeholder_image)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        if(so_prev_lol)
                            supportStartPostponedEnterTransition();
                        else
                            startPostponedEnterTransition();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        if(so_prev_lol)
                            supportStartPostponedEnterTransition();
                        else
                            startPostponedEnterTransition();
                        return false;
                    }
                })
                .into(imgv);
    }

}
