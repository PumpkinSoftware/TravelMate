package com.example.pumpkinsoftware.travelmate;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.pumpkinsoftware.travelmate.client_server_interaction.GetPartecipantIteration;
import com.example.pumpkinsoftware.travelmate.client_server_interaction.GetUserByUid;
import com.example.pumpkinsoftware.travelmate.client_server_interaction.ServerCallback;
import com.example.pumpkinsoftware.travelmate.glide.GlideApp;
import com.example.pumpkinsoftware.travelmate.user.User;

import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class TravelDetailsActivity extends AppCompatActivity {
    public final static String EXTRA_ID = "travelmate_extra_tda_TRIP_ID";
    public final static String EXTRA_IMG = "travelmate_extra_tda_TRIP_IMG";
    public final static String EXTRA_NAME = "travelmate_extra_tda_TRIP_NAME";
    public final static String EXTRA_DESCR = "travelmate_extra_tda_TRIP_DESCR";
    public final static String EXTRA_DEPARTURE = "travelmate_extra_tda_TRIP_DEP";
    public final static String EXTRA_DEST = "travelmate_extra_tda_TRIP_DEST";
    public final static String EXTRA_BUDGET = "travelmate_extra_tda_TRIP_BUDGET";
    public final static String EXTRA_START = "travelmate_extra_tda_TRIP_START";
    public final static String EXTRA_END = "travelmate_extra_tda_TRIP_END";
    public final static String EXTRA_GROUP = "travelmate_extra_tda_TRIP_GROUP";
    public final static String EXTRA_TAG = "travelmate_extra_tda_TRIP_TAG";
    public final static String EXTRA_VEHICLE ="travelmate_extra_tda_TRIP_VEHICLE";
    public final static String EXTRA_OWNER_UID ="travelmate_extra_tda_TRIP_OWNER_UID";

    private Context context;
    private boolean so_prev_lol; // Useful for transitions

    private final static String QUERY= "https://debugtm.herokuapp.com/user/getUsersByTrip?tripId=";
    private final static String URL = "https://debugtm.herokuapp.com/user/getUserByUid?userUid=";
    private RequestQueue mRequestQueue;
    private ArrayList<User> partecipants;
    private CardView card;
    private CircleImageView o_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_details);
        context = (Context) this;

        Bundle b = getIntent().getExtras();
        final String id =  b.getString(EXTRA_ID);
        final String img =  b.getString(EXTRA_IMG);
        final String name =  b.getString(EXTRA_NAME);
        final String descr =  b.getString(EXTRA_DESCR);
        final String dep =  b.getString(EXTRA_DEPARTURE);
        final String dest =  b.getString(EXTRA_DEST);
        final String budget =  b.getString(EXTRA_BUDGET);
        final String start =  b.getString(EXTRA_START);
        final String end =  b.getString(EXTRA_END);
        final String group =  b.getString(EXTRA_GROUP);
        final String tag = b.getString(EXTRA_TAG);
        final String vehicle = b.getString(EXTRA_VEHICLE);
        final String owner_uid = b.getString(EXTRA_OWNER_UID);

        final ImageView imgv = (ImageView) findViewById(R.id.header_cover_image);
        loadImg(img, imgv);
        final TextView n = (TextView) findViewById(R.id.name);
        n.setText(name);
        final TextView dsc = (TextView) findViewById(R.id.descr);
        dsc.setText(descr);

        // Justified text alignment
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            dsc.setText(descr);
            dsc.setJustificationMode(Layout.JUSTIFICATION_MODE_INTER_WORD);
        }

        else {
            final WebView view = (WebView) findViewById(R.id.descr_for_older_versions);
            String text = "<html><body><p align=\"justify\">";
            text+= descr;
            text+= "</p></body></html>";
            view.loadData(text, "text/html", "utf-8");
            view.setVisibility(View.VISIBLE);
            dsc.setVisibility(View.GONE);
            // Now I've to change the below param of the below elements
            final RelativeLayout layout = findViewById(R.id.layout2);
            RelativeLayout.LayoutParams params= new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.BELOW, R.id.descr_for_older_versions);
            layout.setLayoutParams(params);
        }

        final TextView t_tag=(TextView) findViewById(R.id.tag);
        if(tag.equals("cultura")){
            t_tag.setBackgroundColor(Color.parseColor("#008000")); //verde
        }else if (tag.equals("musica")){
            t_tag.setBackgroundColor(Color.parseColor("#FF8C00")); //arancione(dark)
        }else if(tag.equals("intrattenimento")){
            t_tag.setBackgroundColor(Color.parseColor("#FF0000")); //rosso
        }else{
            t_tag.setBackgroundColor(Color.parseColor("#1E90FF")); //blu
        }
        t_tag.setText(tag);

        final TextView dp = (TextView) findViewById(R.id.from);
        dp.setText(dep);
        final TextView dt = (TextView) findViewById(R.id.to);
        dt.setText(dest);
        final TextView bud = (TextView) findViewById(R.id.budget);
        bud.setText(budget);
        final TextView s = (TextView) findViewById(R.id.date1);
        s.setText(getData(start));
        final TextView e = (TextView) findViewById(R.id.date2);
        e.setText(getData(end));
        final TextView g = (TextView) findViewById(R.id.n_users);
        g.setText(group);

        final TextView vm= (TextView) findViewById(R.id.vehicle_text);
        final ImageView vi= (ImageView) findViewById(R.id.vehicle_image);
        vm.setText(vehicle);
        if(vehicle.equals("treno")){
            vi.setImageResource(R.drawable.ic_train_black_12dp);
        }else{
            vi.setImageResource(R.drawable.ic_directions_car_black_12dp);
        }


        final ImageView back_image = (ImageView) findViewById(R.id.back);
        // Handling back to parent activity
        back_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)    finishAfterTransition();
                else    finish();
            }
        });
        /* favoriti
        final CheckBox fav_image = (CheckBox) findViewById(R.id.fav_image);
        // Handling animation on click
        fav_image.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.zoom_in);
                set.setTarget(buttonView); // set the view you want to animate
                set.start();
            }
        });*/

        final ImageView sharing_image = (ImageView) findViewById(R.id.sharing_image);
        // Handling sharing on click with animation
        sharing_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.zoom_in);
                set.setTarget(v); // set the view you want to animate
                set.start();
                shareText(name);
            }
        });

        card = (CardView) findViewById(R.id.cardView);
        final Button join = (Button) findViewById(R.id.join_button);
        // Handling join on click with animation
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.zoom_in);
                set.setTarget(v); // set the view you want to animate
                set.start();*/
                join(join);
            }
        });

        // Handling partecipants
        /*RequestQueue mRequestQueue = Volley.newRequestQueue(context);
        final GetUserByUid server =  new GetUserByUid(context);
        server.getUserFromServer(URL+owner_uid, mRequestQueue, new ServerCallback() {
                    @Override
                    public void onSuccess(JSONObject response) {
                        final User owner = server.getUser();
                        o_image = findViewById(R.id.profile1);

                        GlideApp.with(context)
                                .load((owner.getPhotoProfile().isEmpty())?(R.drawable.girl):(owner.getPhotoProfile()))
                                .placeholder(R.mipmap.placeholder_image)
                                .into(o_image);
                        TextView o_name = findViewById(R.id.user1);
                        o_name.setText(owner.getName());

                        View.OnClickListener lis = new View.OnClickListener(){
                            @Override
                            public void onClick(View v) {
                                openUser(owner);
                            }
                        };

                        o_image.setOnClickListener(lis);
                        o_name.setOnClickListener(lis);
                    }
                }
        );*/

        final ProgressBar progress = findViewById(R.id.indeterminateBar);
        final RecyclerView rvUsers = (RecyclerView) findViewById(R.id.recyclerview);
        // Set layout manager to position the items
        rvUsers.setLayoutManager(new LinearLayoutManager(context));
        rvUsers.setNestedScrollingEnabled(false);
        getPartecipants(rvUsers, progress, id, owner_uid);


        //new GetPartecipantIteration(context, rvUsers, progress).getPartecipantFromServer(QUERY+id, owner_uid, mRequestQueue, partecipants);

        //swipe da finire
        final SwipeRefreshLayout swipe = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe.setRefreshing(true);
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //temporaneo
                        mRequestQueue= Volley.newRequestQueue(context);
                        getPartecipants(rvUsers, progress, id, owner_uid);
                        swipe.setRefreshing(false);

                    }
                },1500);
            }
        });
    }

    private void getPartecipants(RecyclerView rvUsers, ProgressBar progress, String id, final String owner_uid) {
        partecipants = new ArrayList<User>();

        mRequestQueue = Volley.newRequestQueue(context);
        final GetPartecipantIteration server =  new GetPartecipantIteration(context, rvUsers, progress);
        server.getPartecipantFromServer(QUERY+id, owner_uid, mRequestQueue,
                partecipants, new ServerCallback() {
                    @Override
                    public void onSuccess(JSONObject response) {
                        final String img = server.getOwnerImg();
                        o_image = findViewById(R.id.profile1);

                        GlideApp.with(context)
                                .load((img.isEmpty())?(R.drawable.girl):(img))
                                .placeholder(R.mipmap.placeholder_image)
                                .into(o_image);
                        TextView o_name = findViewById(R.id.user1);
                        o_name.setText(server.getOwnerName());

                        View.OnClickListener lis = new View.OnClickListener(){
                            @Override
                            public void onClick(View v) {
                                openUser(owner_uid);
                            }
                        };

                        o_image.setOnClickListener(lis);
                        o_name.setOnClickListener(lis);
                    }
                }
        );
    }

    // Handling sharing
    private void shareText(String s) {
        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.setType("text/plain");
        //String shareBodyText = "Your sharing message goes here";
        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject/Title");
        intent.putExtra(android.content.Intent.EXTRA_TEXT, "Che ne dici di dare un'occhiata a "+ s + "?");
        startActivity(Intent.createChooser(intent, "Condividi"));
    }

    // Handling join button with animation
    private int colorFrom = R.color.colorPrimary;
    private int colorTo = Color.RED;

    private void join(final Button b) {
        if (b.getText() == "Abbandona") {
            new AlertDialog.Builder(this)
                    .setTitle("Abbandona evento")
                    .setMessage("Vuoi lasciare il gruppo?")
                    //.setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton("Sì", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {
                            /*AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.zoom_in);
                            set.setTarget(b); // set the view you want to animate
                            set.start();*/
                            animate(b);
                            ObjectAnimator backgroundColorAnimator = ObjectAnimator.ofObject(card,
                                    "backgroundColor",
                                    new ArgbEvaluator(),
                                    colorTo,
                                    colorFrom);
                            backgroundColorAnimator.setDuration(300);
                            backgroundColorAnimator.start();
                            b.setText("Unisciti");
                            // TODO remove user from travel
                        }})
                    .setNegativeButton(android.R.string.no, null).show();
        }

        else {
            /*AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.zoom_in);
            set.setTarget(b); // set the view you want to animate
            set.start();*/
            animate(b);
            ObjectAnimator backgroundColorAnimator = ObjectAnimator.ofObject(card,
                    "backgroundColor",
                    new ArgbEvaluator(),
                    colorFrom,
                    colorTo);
            backgroundColorAnimator.setDuration(300);
            backgroundColorAnimator.start();
            b.setText("Abbandona");
            // TODO add user to travel
        }
    }

    // Animation zoom in on a view
    private void animate(View v) {
        AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.zoom_in);
        set.setTarget(v); // set the view you want to animate
        set.start();
    }

    // Open user on click
    private void openUser(User u) {
        Intent intent = new Intent(context, UserDetailsActivity.class);
        intent.putExtra(UserDetailsActivity.EXTRA_UID, u.getUid());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // create the transition animation - the images in the layouts
            // of both activities are defined with android:transitionName="robot"
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity)context,
                    Pair.create((View)o_image, "image"));
            //Pair.create((View)trip_name, "travel_name"));
            // start the new activity
            context.startActivity(intent, options.toBundle());
        }

        else {
            context.startActivity(intent);
        }
    }

    // Open user on click
    private void openUser(String uid) {
        Intent intent = new Intent(context, UserDetailsActivity.class);
        intent.putExtra(UserDetailsActivity.EXTRA_UID, uid);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // create the transition animation - the images in the layouts
            // of both activities are defined with android:transitionName="robot"
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity)context,
                    Pair.create((View)o_image, "image"));
            //Pair.create((View)trip_name, "travel_name"));
            // start the new activity
            context.startActivity(intent, options.toBundle());
        }

        else {
            context.startActivity(intent);
        }
    }

    // Load travel image with transition efficiently
    private void loadImg(String img, ImageView imgv) {
        so_prev_lol = false;
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            postponeEnterTransition();
        else {
            so_prev_lol = true;
            supportPostponeEnterTransition();
        }

        GlideApp.with(this)
                .load((img.isEmpty())?(R.mipmap.default_trip):(img))
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sharing_menu, menu);
        return true; //super.onCreateOptionsMenu(menu);
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
                startActivity(Intent.createChooser(sharingIntent, "Sharing Option"));
                return true;

            default:
                return false;
        }
    }
    public String getData(String data) {
        String[] d=data.split("-");
        return d[2].substring(0,2) +"/"+d[1]+"/"+d[0];
    }
}
