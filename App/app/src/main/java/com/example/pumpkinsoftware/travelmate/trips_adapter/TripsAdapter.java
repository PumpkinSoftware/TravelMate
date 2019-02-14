package com.example.pumpkinsoftware.travelmate.trips_adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;

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
        public Button more_button;
        public ImageView sharing_image;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View v) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(v);

            trip_image = (ImageView) v.findViewById(R.id.travel_image);
            trip_name = (TextView) v.findViewById(R.id.travel_name);
            group_number = (TextView) v.findViewById(R.id.group_number);
            group_image = (ImageView) v.findViewById(R.id.group_image);
            budget_number = (TextView) v.findViewById(R.id.budget_number);
            budget_image = (ImageView) v.findViewById(R.id.budget_image);
            more_button = (Button) v.findViewById(R.id.button);
            sharing_image = (ImageView) v.findViewById(R.id.sharing_image);
        }
    }

    public TripsAdapter(List<Trip> t) {
        trips = t;
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
        // To prevent crash, it wait until context is setted
        while(context == null){}
        GlideApp.with(context)
                .load(R.mipmap.new_york)
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
        Button button = viewHolder.more_button;
        ImageView s_image = viewHolder.sharing_image;
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return trips.size();
    }

}
