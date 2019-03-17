package com.example.pumpkinsoftware.travelmate.trips_adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pumpkinsoftware.travelmate.R;
import com.example.pumpkinsoftware.travelmate.glide.GlideApp;
import com.example.pumpkinsoftware.travelmate.trip.Trip;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TripsAdapterChat extends RecyclerView.Adapter<TripsAdapterChat.ViewHolder> implements Serializable {
    private transient List<Trip> trips;
    private transient Context context = null;


    public TripsAdapterChat(ArrayList<Trip> mTrips) {
        trips = mTrips;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public CircleImageView imageTravel;
        public TextView nameTravel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageTravel = itemView.findViewById(R.id.profile);
            nameTravel = itemView.findViewById(R.id.user);

            View.OnClickListener lis = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Trip t = getTrip();
                    //apri chat
                }
            };

            imageTravel.setOnClickListener(lis);
            nameTravel.setOnClickListener(lis);

        }

        // Returns trip in the list
        private Trip getTrip() {
            int pos = getAdapterPosition();
            // check if item still exists
            if (pos != RecyclerView.NO_POSITION) return trips.get(pos);
            return null;
        }
    }



    @NonNull
    @Override
    public TripsAdapterChat.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.partecipants, viewGroup, false);

        // Return a new holder instance
        TripsAdapterChat.ViewHolder viewHolder = new TripsAdapterChat.ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TripsAdapterChat.ViewHolder viewHolder, int i) {
        Trip trip = trips.get(i);
        // Set item views based on your views and data model
        CircleImageView image = viewHolder.imageTravel;
        GlideApp.with(context)
                .load((trip.getImage().isEmpty()) ? (R.mipmap.default_trip) : (trip.getImage()))
                .placeholder(R.mipmap.placeholder_image)
                .into(image);
        TextView name = viewHolder.nameTravel;
        name.setText(trip.getName());
    }

    @Override
    public int getItemCount() {
        return trips.size();
    }


}
