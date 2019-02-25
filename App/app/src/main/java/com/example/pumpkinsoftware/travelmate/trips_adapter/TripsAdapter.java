package com.example.pumpkinsoftware.travelmate.trips_adapter;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.transition.TransitionManager;
import android.util.DisplayMetrics;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.CardView;
import android.widget.Toast;

import com.example.pumpkinsoftware.travelmate.TravelDetailsActivity;
import com.example.pumpkinsoftware.travelmate.glide.GlideApp;
import com.example.pumpkinsoftware.travelmate.trip.Trip;
import com.example.pumpkinsoftware.travelmate.R;

import java.util.List;

public class TripsAdapter extends RecyclerView.Adapter<TripsAdapter.ViewHolder> {
    private List<Trip> trips;
    private Context context = null;

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public ImageView trip_image;
        public TextView trip_name;
        public TextView group_number;
        public ImageView group_image;
        public TextView budget_number;
        public ImageView budget_image;
        //public Button more_button;
        public CheckBox fav_image;
        public ImageView sharing_image;
        private int minHeight;
        private CardView cardView;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View v) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(v);

            trip_image = (ImageView) v.findViewById(R.id.travel_image);
            // Handling click on a card
            trip_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Trip t = getTrip();

                    if(t != null)
                        openCard(t);
                }
            });

            trip_name = (TextView) v.findViewById(R.id.travel_name);
            group_number = (TextView) v.findViewById(R.id.group_number);
            group_image = (ImageView) v.findViewById(R.id.group_image);
            budget_number = (TextView) v.findViewById(R.id.budget_number);
            budget_image = (ImageView) v.findViewById(R.id.budget_image);
            //more_button = (Button) v.findViewById(R.id.button);

            fav_image = (CheckBox) v.findViewById(R.id.fav_image);
            // Handling animation on click
            fav_image.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.zoom_in);
                    set.setTarget(buttonView); // set the view you want to animate
                    set.start();
                }
            });

            sharing_image = (ImageView) v.findViewById(R.id.sharing_image);
            // Handling sharing on click with animation
            sharing_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.zoom_in);
                    set.setTarget(v); // set the view you want to animate
                    set.start();
                    Trip t = getTrip();
                    shareText(t.getName());
                }
            });

        }

        // Returns trip in the list
        private Trip getTrip(){
            int pos = getAdapterPosition();

            // check if item still exists
            if(pos != RecyclerView.NO_POSITION) return trips.get(pos);
            return                              null;
        }

        private void openCard(Trip t) {
            Intent intent = new Intent(context, TravelDetailsActivity.class);
            intent.putExtra(TravelDetailsActivity.EXTRA_ID, t.getId());
            intent.putExtra(TravelDetailsActivity.EXTRA_NAME, t.getName());
            intent.putExtra(TravelDetailsActivity.EXTRA_BUDGET, t.getBudget());
            intent.putExtra(TravelDetailsActivity.EXTRA_GROUP, t.getGroup());

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                // create the transition animation - the images in the layouts
                // of both activities are defined with android:transitionName="robot"
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity)context,
                        Pair.create((View)trip_image, "travel_image"));
                        //Pair.create((View)trip_name, "travel_name"));
                // start the new activity
                context.startActivity(intent, options.toBundle());
            }

            else {
                context.startActivity(intent);
            }
        }

    }



    public TripsAdapter(List<Trip> t) {
        trips = t;
    }

    private void shareText(String s) {
        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.setType("text/plain");
        //String shareBodyText = "Your sharing message goes here";
        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject/Title");
        intent.putExtra(android.content.Intent.EXTRA_TEXT, "Che ne dici di dare un'occhiata a "+ s + "?");
        context.startActivity(Intent.createChooser(intent, "Condividi"));
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public TripsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.home_row, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(TripsAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Trip trip = trips.get(position);

        // Set item views based on your views and data model
        ImageView t_image = viewHolder.trip_image;
        ;
        // To prevent crash, it wait until context is setted
        while(context == null){}
        GlideApp.with(context)
                .load(trip.getImage())
                .placeholder(R.mipmap.placeholder_image)
                .into(t_image);
        TextView t_name = viewHolder.trip_name;
        t_name.setText(trip.getName());
        TextView b_number = viewHolder.budget_number;
        b_number.setText(trip.getBudget());
        ImageView b_image = viewHolder.budget_image;
        TextView g_number = viewHolder.group_number;
        g_number.setText(trip.getGroup());
        ImageView g_image = viewHolder.group_image;
        //Button button = viewHolder.more_button;
        ImageView s_image = viewHolder.sharing_image;
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return trips.size();
    }

}
