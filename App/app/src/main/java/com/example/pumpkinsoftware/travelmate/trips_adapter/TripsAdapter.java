package com.example.pumpkinsoftware.travelmate.trips_adapter;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;

import com.example.pumpkinsoftware.travelmate.TravelDetailsActivity;
import com.example.pumpkinsoftware.travelmate.glide.GlideApp;
import com.example.pumpkinsoftware.travelmate.trip.Trip;
import com.example.pumpkinsoftware.travelmate.R;

import java.io.Serializable;
import java.util.List;

public class TripsAdapter extends RecyclerView.Adapter<TripsAdapter.ViewHolder> implements Serializable {
    private transient List<Trip> trips;
    private transient Context context = null;
    private boolean fav;

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder implements Serializable {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public ImageView trip_image,fav_image;
        public TextView trip_name,group_number,budget_number,trip_tag,destinazione,data;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View v) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(v);


            // Handling click on a card
            View.OnClickListener lis = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Trip t = getTrip();

                    if(t != null)
                        openCard(t);
                }
            };

            trip_image = (ImageView) v.findViewById(R.id.travel_image);
            trip_image.setOnClickListener(lis);

            trip_name = (TextView) v.findViewById(R.id.travel_name);
            trip_name.setOnClickListener(lis);

            trip_tag = (TextView) v.findViewById(R.id.travel_tag);

            group_number = (TextView) v.findViewById(R.id.group_number);
            /*
            group_image = (ImageView) v.findViewById(R.id.group_image);
            GlideApp.with(context)
                    .load(R.drawable.baseline_group_black_24dp)
                    .into(group_image);
            */
            budget_number = (TextView) v.findViewById(R.id.budget_number);
            /*
            budget_image = (ImageView) v.findViewById(R.id.budget_image);
            GlideApp.with(context)
                    .load(R.drawable.baseline_euro_symbol_black_24dp)
                    .into(budget_image);*/
            //more_button = (Button) v.findViewById(R.id.button);

            /*
            fav_image = (ImageView) v.findViewById(R.id.fav_image);
            // Handling animation on click
            fav_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(fav) {
                        fav_image.setImageResource(R.drawable.fav_icon);
                        fav = false;
                        // TODO remove travel from user favs
                    }
                    else {
                        fav_image.setImageResource(R.drawable.red_heart);
                        fav = true;
                        // TODO add travel to user favs
                    }
                    AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.zoom_in);
                    set.setTarget(v); // set the view you want to animate
                    set.start();
                }
            });

            /*fav_image.setOnChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.zoom_in);
                    set.setTarget(buttonView); // set the view you want to animate
                    set.start();
                }
            });*/

            destinazione = (TextView) v.findViewById(R.id.destinazione);
            data = (TextView) v.findViewById(R.id.data);
            /*
            sharing_image = (ImageView) v.findViewById(R.id.sharing_image);
            GlideApp.with(context)
                    .load(R.drawable.outline_share_black_24dp)
                    .into(sharing_image);
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
            });*/

        }

        private int pos;

        // Returns trip in the list
        private Trip getTrip(){
            pos = getAdapterPosition();

            // check if item still exists
            if(pos != RecyclerView.NO_POSITION) return trips.get(pos);
            return                              null;
        }

        private void openCard(Trip t) {
            Intent intent = new Intent(context, TravelDetailsActivity.class);
            intent.putExtra(TravelDetailsActivity.EXTRA_ID, t.getId());
            intent.putExtra(TravelDetailsActivity.EXTRA_IMG, t.getImage());
            intent.putExtra(TravelDetailsActivity.EXTRA_NAME, t.getName());
            intent.putExtra(TravelDetailsActivity.EXTRA_DESCR, t.getDescr());
            intent.putExtra(TravelDetailsActivity.EXTRA_DEPARTURE, t.getDeparture());
            intent.putExtra(TravelDetailsActivity.EXTRA_DEST, t.getDest());
            intent.putExtra(TravelDetailsActivity.EXTRA_BUDGET, t.getBudget());
            intent.putExtra(TravelDetailsActivity.EXTRA_START, t.getStartDate());
            intent.putExtra(TravelDetailsActivity.EXTRA_END, t.getEndDate());
            intent.putExtra(TravelDetailsActivity.EXTRA_PARTECIPANTS_NUMBER, t.getPartecipants());
            intent.putExtra(TravelDetailsActivity.EXTRA_GROUP_NUMBER, t.getGroupNumber());
            intent.putExtra(TravelDetailsActivity.EXTRA_TAG, t.getTag());
            intent.putExtra(TravelDetailsActivity.EXTRA_VEHICLE, t.getVehicle());
            intent.putExtra(TravelDetailsActivity.EXTRA_OWNER_UID, t.getOwner());
            intent.putExtra(TravelDetailsActivity.EXTRA_ADAPTER, getTripsAdapter());
            intent.putExtra(TravelDetailsActivity.EXTRA_ADAPTER_POS, pos);

            /*String extraUserIsAPartecipant;
            if(currentUser.getTrips().contains(travelId))  extraUserIsAPartecipant = "true";
            else                                           extraUserIsAPartecipant = "false";

            intent.putExtra(TravelDetailsActivity.EXTRA_USER, extraUserIsAPartecipant);*/

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

    // Useful to update recycler view from travel details
    public TripsAdapter getTripsAdapter() { return this; }
    public List<Trip> getTrips() { return trips; }

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
        TripsAdapter.ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(TripsAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Trip trip = trips.get(position);

        // Set item views based on your views and data model
        ImageView t_image = viewHolder.trip_image;
        GlideApp.with(context)
                .load((trip.getImage().isEmpty())?(R.mipmap.default_trip):(trip.getImage()))
                .placeholder(R.mipmap.placeholder_image)
                .into(t_image);
        TextView t_name = viewHolder.trip_name;
        t_name.setText(trip.getName());
        t_name.setHorizontallyScrolling(true);
        TextView b_number = viewHolder.budget_number;
        b_number.setText(trip.getBudget());
        TextView g_number = viewHolder.group_number;
        g_number.setText(trip.getGroup());
        TextView t_tag=viewHolder.trip_tag;
        String tag=trip.getTag();
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
        TextView b_destinazione = viewHolder.destinazione;
        b_destinazione.setText(trip.getDest());
        TextView b_data=viewHolder.data;
        b_data.setText(getData(trip.getStartDate()));
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return trips.size();
    }

    public String getData(String data) {
      String[] d=data.split("-");
      return d[2].substring(0,2) +"/"+d[1];
    }
}
