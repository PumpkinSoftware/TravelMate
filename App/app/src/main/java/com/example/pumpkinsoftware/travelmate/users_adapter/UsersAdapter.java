package com.example.pumpkinsoftware.travelmate.users_adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pumpkinsoftware.travelmate.UserDetailsActivity;
import com.example.pumpkinsoftware.travelmate.glide.GlideApp;
import com.example.pumpkinsoftware.travelmate.swipe_touch_listener.OnSwipeTouchListener;
import com.example.pumpkinsoftware.travelmate.user.User;
import com.example.pumpkinsoftware.travelmate.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {
    private Context context = null;
    private List<User> users;
    private boolean currentUserIsOwner;

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public CircleImageView profile;
        public TextView username;
        public TextView userSurname;
        public ImageView delete;
        public RelativeLayout layout;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View v) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(v);

            profile = v.findViewById(R.id.profile);
            username = v.findViewById(R.id.userName);
            userSurname = v.findViewById(R.id.userSurname);
            delete = v.findViewById(R.id.delete);
            layout = v.findViewById(R.id.users);

            View.OnClickListener lis = new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    User u = getUser();

                    if(u != null)
                        openUser(u);
                }
            };

            userSurname.setVisibility(View.VISIBLE);
            profile.setOnClickListener(lis);
            username.setOnClickListener(lis);
            userSurname.setOnClickListener(lis);

            // Only the travel's owner can delete a partecipant
            if(currentUserIsOwner) {
                delete.setVisibility(View.VISIBLE);
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO delete user from group
                    }
                });

                layout.setOnTouchListener(new OnSwipeTouchListener(context) {
                    public void onSwipeTop() {
                        //Toast.makeText(MyActivity.this, "top", Toast.LENGTH_SHORT).show();
                    }

                    public void onSwipeRight() {
                        Toast.makeText(context, "right", Toast.LENGTH_SHORT).show();
                        //TODO delete user from group
                    }

                    public void onSwipeLeft() {
                        //Toast.makeText(MyActivity.this, "left", Toast.LENGTH_SHORT).show();
                    }

                    public void onSwipeBottom() {
                        //Toast.makeText(MyActivity.this, "bottom", Toast.LENGTH_SHORT).show();
                    }
                });
            }

        }

        // Returns user in the list
        private User getUser(){
            int pos = getAdapterPosition();

            // check if item still exists
            if(pos != RecyclerView.NO_POSITION) return users.get(pos);
            return null;
        }

        private void openUser(User u) {
            Intent intent = new Intent(context, UserDetailsActivity.class);
            intent.putExtra(UserDetailsActivity.EXTRA_UID, u.getUid());

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                // create the transition animation - the images in the layouts
                // of both activities are defined with android:transitionName="robot"
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity)context,
                        Pair.create((View)profile, "image"));
                //Pair.create((View)trip_name, "travel_name"));
                // start the new activity
                context.startActivity(intent, options.toBundle());
            }

            else {
                context.startActivity(intent);
            }
        }
    }

    public UsersAdapter(ArrayList<User> u, boolean currentUserIsOwner) {
        users = u;
        this.currentUserIsOwner = currentUserIsOwner;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @NonNull
    @Override
    public UsersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.partecipants, parent, false);

        // Return a new holder instance
        UsersAdapter.ViewHolder viewHolder = new UsersAdapter.ViewHolder(contactView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(UsersAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        User user = users.get(position);

        // Set item views based on your views and data model
        CircleImageView image = viewHolder.profile;
        GlideApp.with(context)
                .load((user.getPhotoProfile().isEmpty())?(R.drawable.blank_avatar):(user.getPhotoProfile()))
                .placeholder(R.mipmap.placeholder_image)
                .into(image);

        TextView txt = viewHolder.username;
        txt.setText(user.getName());

        txt = viewHolder.userSurname;
        txt.setText(user.getSurname());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }


}
