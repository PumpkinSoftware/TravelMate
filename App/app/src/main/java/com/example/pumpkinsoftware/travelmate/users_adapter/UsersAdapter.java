package com.example.pumpkinsoftware.travelmate.users_adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
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

import com.example.pumpkinsoftware.travelmate.TravelDetailsActivity;
import com.example.pumpkinsoftware.travelmate.UserDetailsActivity;
import com.example.pumpkinsoftware.travelmate.client_server_interaction.PostJoin;
import com.example.pumpkinsoftware.travelmate.client_server_interaction.ServerCallback;
import com.example.pumpkinsoftware.travelmate.glide.GlideApp;
import com.example.pumpkinsoftware.travelmate.swipe_touch_listener.OnSwipeTouchListener;
import com.example.pumpkinsoftware.travelmate.user.User;
import com.example.pumpkinsoftware.travelmate.R;
import com.example.pumpkinsoftware.travelmate.utils.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {
    private Context context = null;
    private List<User> users;
    private boolean currentUserIsOwner;
    private String tripId, tripStartDate;
    private UsersAdapter.ViewHolder viewHolder;
    private boolean deletingFromSwipe;

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public CircleImageView profile;
        public TextView username;
        public TextView userSurname;
        public ImageView delete;

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

            final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if(user == null)    return;

            // Only the travel's owner can delete a partecipant
            if(currentUserIsOwner && !isTripExpired()) {
                delete.setVisibility(View.VISIBLE);
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Remove user from group
                        removeUser(user);
                    }
                });
            }

        }

        private int pos;

        // Returns user in the list
        private User getUser(){
            pos = getAdapterPosition();

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

        private void removeUser(final FirebaseUser user) {
            User u = getUser();
            //System.out.println("pos = "+pos);

            if(deletingFromSwipe) {
                if(u == null) {
                    u = mRecentlyDeletedItem;
                    pos = mRecentlyDeletedItemPosition;
                }
                users.remove(pos);
                notifyItemRemoved(pos);
            }

            final String uid = u.getUid();

            if(u != null) {
                new AlertDialog.Builder(context)
                        .setTitle("Elimina partecipante")
                        .setMessage("Sei sicuro di voler rimuovere " + u.getName() + " " + u.getSurname() + "?")
                        .setPositiveButton("Sì", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                user.getIdToken(true)
                                        .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                                            public void onComplete(@NonNull Task<GetTokenResult> task) {
                                                if (task.isSuccessful()) {
                                                    String idToken = task.getResult().getToken();
                                                    // Send token to your backend via HTTPS
                                                    new PostJoin(context).send(Utils.SERVER_PATH +
                                                                    "user/removeTripByOwner", tripId, uid,
                                                            PostJoin.request.REMOVE, idToken, new ServerCallback() {
                                                                @Override
                                                                public void onSuccess(JSONObject result) {
                                                                    if(!deletingFromSwipe) {
                                                                        users.remove(pos);
                                                                        notifyItemRemoved(pos);
                                                                    }
                                                                }
                                                            });
                                                } else {
                                                    // Handle error -> task.getException();
                                                    Toast.makeText(context, "Riprova", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            }

                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(deletingFromSwipe) {
                                    users.add(mRecentlyDeletedItemPosition,
                                            mRecentlyDeletedItem);
                                    notifyItemInserted(mRecentlyDeletedItemPosition);
                                    deletingFromSwipe = false;
                                }
                            }
                        }).show();
            }
        }

        private boolean isTripExpired() {
            try {
                Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(TravelDetailsActivity.getData(tripStartDate));
                Calendar now = Calendar.getInstance();
                Calendar startDate = Calendar.getInstance();
                startDate.setTime(date1);

                // Check if travel is expired
                if (now.equals(startDate) || now.after(startDate))
                    return true;
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
            return false;
        }

    }

    public UsersAdapter(ArrayList<User> u, boolean currentUserIsOwner, String tripId, String tripStartDate) {
        users = u;
        this.currentUserIsOwner = currentUserIsOwner;
        this.tripStartDate = tripStartDate;
        this.tripId = tripId;
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
        viewHolder = new UsersAdapter.ViewHolder(contactView);
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

    private User mRecentlyDeletedItem;
    private int mRecentlyDeletedItemPosition = -1;

    public void deleteItem(int position) {
        deletingFromSwipe = true;
        mRecentlyDeletedItem = users.get(position);
        mRecentlyDeletedItemPosition = position;
        //System.out.println("recently del pos = "+position);

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null)
            viewHolder.removeUser(user);
    }

    public void setViewHolder(UsersAdapter.ViewHolder v) { viewHolder = v; }

    public void updateData(List<User> newUsers, boolean currentUserIsOwner) {
        users.clear();
        users.addAll(newUsers);
        this.currentUserIsOwner = currentUserIsOwner;
    }

}
