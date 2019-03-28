package com.example.pumpkinsoftware.travelmate.client_server_interaction;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.pumpkinsoftware.travelmate.trip.Trip;
import com.example.pumpkinsoftware.travelmate.trips_adapter.TripsAdapter;
import com.example.pumpkinsoftware.travelmate.trips_adapter.TripsAdapterChat;
import com.example.pumpkinsoftware.travelmate.user.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GetTripInteraction {
    private Context context;
    private RecyclerView rvTrips;
    private TripsAdapter adapter;
    private TripsAdapterChat adapterChat;
    private ProgressBar progressBar;
    private ArrayList<Trip> mTrips;
    private String idToken;

    public GetTripInteraction(Context c, RecyclerView rv, ProgressBar progress, String id) {
        context = c;
        rvTrips = rv;
        progressBar = progress;
        idToken=id;
    }

    // NEW VERSION
    public void getTripsFromServer(String query, RequestQueue mQueue, final TextView text, final ImageView img) {
        if(mTrips == null)    mTrips = new ArrayList<Trip>();
        else                  mTrips.clear();

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, query, null, new Response.Listener<JSONArray>() {
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject travel = response.getJSONObject(i);
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
                        final int partecipants = travel.getInt("partecipants");
                        final String tag=travel.getString("tag");
                        final String vehicle= travel.getString("vehicle");
                        final String owner = travel.getString("owner");

                        mTrips.add(new Trip(id, image, name, descr, departure, dest, budget,dep_date, end_date,
                                partecipants, group_max, tag, vehicle, owner));
                    }

                    if(mTrips.isEmpty()) {
                        text.setVisibility(View.VISIBLE);
                        img.setVisibility(View.VISIBLE);
                    }
                    // Useful to restore default conditions
                    else {
                        text.setVisibility(View.GONE);
                        img.setVisibility(View.GONE);
                    }

                    adapter = new TripsAdapter(mTrips);
                    // Attach the adapter to the recyclerview to populate items
                    rvTrips.setAdapter(adapter);
                    hideProgressBar();
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
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("access_token", idToken);
                return params;
            }
        };

        mQueue.add(request);
    }

    //private int lastSize;

    // EXCLUSIVELY USAGE WHEN USER RETURNS FROM TRAVEL DETAILS
    public void getTripsFromServer(String query, RequestQueue mQueue, final TextView text, final ImageView img, final int pos,
                                   final int offset) {
        if(mTrips == null) {
            mTrips = new ArrayList<Trip>();
            //lastSize = 0;
        }
        else {
            //lastSize = mTrips.size();
            mTrips.clear();
        }

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, query, null, new Response.Listener<JSONArray>() {
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject travel = response.getJSONObject(i);
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
                        final int partecipants = travel.getInt("partecipants");
                        final String tag=travel.getString("tag");
                        final String vehicle= travel.getString("vehicle");
                        final String owner = travel.getString("owner");

                        mTrips.add(new Trip(id, image, name, descr, departure, dest, budget,dep_date, end_date,
                                partecipants, group_max, tag, vehicle, owner));
                    }

                    if(mTrips.isEmpty()) {
                        text.setVisibility(View.VISIBLE);
                        img.setVisibility(View.VISIBLE);
                    }
                    // Useful to restore default conditions
                    else {
                        text.setVisibility(View.GONE);
                        img.setVisibility(View.GONE);
                    }

                    adapter = new TripsAdapter(mTrips);
                    // Attach the adapter to the recyclerview to populate items
                    rvTrips.setAdapter(adapter);

                    // Check if last position is still in Array
                    int newSize = mTrips.size();
                    int newPos = pos;

                    if(pos >= newSize) // => newSize < lastSize
                        newPos = newSize-1;

                    //rvTrips.scrollToPosition(newPos);
                    ((LinearLayoutManager) rvTrips.getLayoutManager()).scrollToPositionWithOffset(newPos, offset);
                    hideProgressBar();
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
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("access_token", idToken);
                return params;
            }
        };
        mQueue.add(request);
    }

    public void getChatTripsFromServer(String query, RequestQueue mQueue, final ArrayList<Trip> trips, final TextView text, final ImageView img) {
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, query, null, new Response.Listener<JSONArray>() {
            public void onResponse(JSONArray response) {
                try {
                    //JSONArray jsonArray=response.getJSONArray("viaggi")
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject travel = response.getJSONObject(i);
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
                        final int partecipants = travel.getInt("partecipants");
                        final String tag=travel.getString("tag");
                        final String vehicle= travel.getString("vehicle");
                        final String owner = travel.getString("owner");

                        trips.add(new Trip(id, image, name, descr, departure, dest, budget,dep_date, end_date,
                                partecipants, group_max, tag, vehicle, owner));
                    }

                    if(trips.isEmpty()) {
                        text.setVisibility(View.VISIBLE);
                        img.setVisibility(View.VISIBLE);
                    }

                    adapterChat = new TripsAdapterChat(trips);
                    //adapter = new TripsAdapter(mTrips);
                    rvTrips.setAdapter(adapterChat);
                    hideProgressBar();
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
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("access_token", idToken);
                return params;
            }
        };
        mQueue.add(request);
    }

    private void hideProgressBar() { if (progressBar != null) progressBar.setVisibility(View.GONE); }
}
