package com.example.pumpkinsoftware.travelmate.client_server_interaction;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.pumpkinsoftware.travelmate.trip.Trip;
import com.example.pumpkinsoftware.travelmate.user.User;
import com.example.pumpkinsoftware.travelmate.users_adapter.UsersAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GetTripById {
    private Context context;
    private ProgressBar progressBar;
    private Trip trip;
    private RecyclerView rvUsers;
    private UsersAdapter adapter;
    private String ownerUid, ownerName, ownerSurname, ownerImg;
    private ArrayList<User> users;
    private boolean userIsAPartecipant;
    private String currentUserUid;

    public GetTripById(Context c, RecyclerView rv, ProgressBar progress) {
        context = c;
        rvUsers = rv;
        progressBar = progress;
    }

    public void getTripFromServer(String query, final ArrayList<User> users,
                                  final String currentUserUid, final String idToken, final ServerCallback callback) {
        this.users = users;
        this.currentUserUid = currentUserUid;
        final RequestQueue mQueue = Volley.newRequestQueue(context);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, query, null, new Response.Listener<JSONArray>() {
            public void onResponse(JSONArray response) {

                try {
                    JSONObject travel = response.getJSONObject(0);
                    final String id = travel.getString("_id");
                    final String image = travel.getString("image");
                    final String name = travel.getString("name");
                    final String descr = travel.getString("description");
                    final String departure = travel.getString("departure");
                    final String dest = travel.getString("destination");
                    final int budget = travel.getInt("budget");
                    final String dep_date = travel.getString("startDate");
                    final String end_date = travel.getString("endDate");
                    final int group_max = travel.getInt("maxPartecipant");
                    final int n_partecipants = travel.getInt("partecipants");
                    final String tag = travel.getString("tag");
                    final String vehicle = travel.getString("vehicle");
                    ownerUid = travel.getString("owner");

                    getPartecipants(response.getJSONArray(1));
                    hideProgressBar();
                    trip = new Trip(id, image, name, descr, departure, dest, budget,dep_date, end_date,
                            n_partecipants, group_max, tag, vehicle, ownerUid);
                    callback.onSuccess(travel);
                } catch (JSONException e) {
                    e.printStackTrace();
                    hideProgressBar();
                    Toast.makeText(context, "Errore: connessione fallita", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                hideProgressBar();
                Toast.makeText(context, "Errore: connessione assente", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("authorization", idToken);
                return params;
            }
        };
        mQueue.add(request);
    }

    private void hideProgressBar() { if (progressBar != null) progressBar.setVisibility(View.GONE); }

    public Trip getTrip() { return trip; }

    private void getPartecipants(JSONArray response) {
        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject user = response.getJSONObject(i);
                String uid = user.getString("uid");
                String name, surname = "";

                if(uid.equals(currentUserUid)) {
                    userIsAPartecipant = true;
                    name = "Tu";
                }
                else {
                    name = user.getString("name");
                    //surname = user.getString("surname");
                }

                String profile = user.getString("avatar");

                if(uid.equals(ownerUid)) {
                    ownerName = name;
                    ownerSurname = surname;
                    ownerImg = profile;
                    // TODO in travel details add textview for surname
                }
                else
                    users.add(new User(uid, name, surname, profile));
            }

            /*boolean currentUserIsOwner = false;
            if(currentUserUid.equals(ownerUid))
                currentUserIsOwner = true;*/

            adapter = new UsersAdapter(users, currentUserUid.equals(ownerUid));
            // Attach the adapter to the recyclerview to populate items
            rvUsers.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
            hideProgressBar();
            Toast.makeText(context, "Errore: connessione fallita", Toast.LENGTH_SHORT).show();
        }
    }

    public String getOwnerUid() { return ownerUid; }

    public String getOwnerName() { return ownerName; }

    public String getOwnerSurname() { return ownerSurname; }

    public String getOwnerImg() { return ownerImg; }

    public boolean isUserAPartecipant() { return userIsAPartecipant; }

}
