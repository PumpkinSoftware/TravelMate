package com.example.pumpkinsoftware.travelmate.users_adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.pumpkinsoftware.travelmate.R;
import com.example.pumpkinsoftware.travelmate.ReviewActivity;
import com.example.pumpkinsoftware.travelmate.glide.GlideApp;
import com.example.pumpkinsoftware.travelmate.user.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapterReview extends RecyclerView.Adapter<UserAdapterReview.ViewHolder> implements Serializable {
    private Context context = null;
    private List<User> users;

    public UserAdapterReview(ArrayList<User> u) {
        users = u;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public CircleImageView image;
        public TextView name;
        public RelativeLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.profile);
            name = itemView.findViewById(R.id.user);
            //layout = itemView.findViewById(R.id.users);

            View.OnClickListener lis = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //apri review
                    User u = getUser();
                    apriReview(u);
                }
            };

            image.setOnClickListener(lis);
            name.setOnClickListener(lis);
            //layout.setOnClickListener(lis);
        }

        // Returns trip in the list
        private User getUser() {
            int pos = getAdapterPosition();
            // check if item still exists
            if (pos != RecyclerView.NO_POSITION) return users.get(pos);
            return null;
        }
    }


    @NonNull
    @Override
    public UserAdapterReview.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.partecipants, viewGroup, false);

        // Return a new holder instance
        UserAdapterReview.ViewHolder viewHolder = new UserAdapterReview.ViewHolder(contactView);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapterReview.ViewHolder viewHolder, int i) {
        User user = users.get(i);
        // Set item views based on your views and data model
        CircleImageView image = viewHolder.image;
        GlideApp.with(context)
                .load((user.getPhotoProfile().isEmpty()) ? (R.drawable.blank_avatar) : (user.getPhotoProfile()))
                .placeholder(R.mipmap.placeholder_image)
                .into(image);
        TextView name = viewHolder.name;
        String userName = user.getName() + " " + user.getSurname();
        name.setText(userName);
    }


    @Override
    public int getItemCount() {
        return users.size();
    }

    private void apriReview(User u) {
            //passa foto e uid
        Intent intent = new Intent(context, ReviewActivity.class);
        intent.putExtra(ReviewActivity.EXTRA_UIDUU,u.getUid());
        Log.i("useruid",u.getUid());
        intent.putExtra(ReviewActivity.EXTRA_PHOTO, u.getPhotoProfile());
        Log.i("photo1",u.getPhotoProfile());
        context.startActivity(intent);

    }
}
