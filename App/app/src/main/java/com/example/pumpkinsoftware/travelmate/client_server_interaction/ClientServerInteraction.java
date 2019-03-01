package com.example.pumpkinsoftware.travelmate.client_server_interaction;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.pumpkinsoftware.travelmate.trip.Trip;
import com.example.pumpkinsoftware.travelmate.trips_adapter.TripsAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ClientServerInteraction {
   // private ArrayList<Trip> trips=null;
    private Context context;
    private RecyclerView rvTrips;
    TripsAdapter adapter;
    private ProgressBar progressBar;

    public ClientServerInteraction(Context c, RecyclerView rv) {
        context = c;
        rvTrips = rv;
    }

    public ClientServerInteraction(Context c, RecyclerView rv, ProgressBar progress) {
        context = c;
        rvTrips = rv;
        progressBar = progress;
    }

    public void getTripsFromServer(String query, RequestQueue mQueue, final ArrayList<Trip> trips) {
       /* if(this.trips == null)    this.trips = new ArrayList<Trip>();
        else                 this.trips.clear();*/

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, query, null, new Response.Listener<JSONArray>() {
            public void onResponse(JSONArray response) {

                try {
                    //JSONArray jsonArray=response.getJSONArray("viaggi")
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject travel = response.getJSONObject(i);
                        String id = travel.getString("_id");
                        String image = travel.getString("image");
                        String name = travel.getString("name");
                        String descr = travel.getString("description");
                        String departure = travel.getString("departure");
                        String dest = travel.getString("destination");
                        int budget = travel.getInt("budget");
                        String dep_date = travel.getString("startDate");
                        String end_date = travel.getString("endDate");
                        int group_max = travel.getInt("maxPartecipant");
                        JSONArray part = travel.getJSONArray("partecipant");
                        int partecipants = part.length();
                        //ClientServerInteraction.this.

                        trips.add(new Trip(id, image, name, descr, departure, dest, budget,dep_date, end_date,
                                partecipants+"/"+group_max));
                    }

                    adapter = new TripsAdapter(trips);
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
        });
        mQueue.add(request);
    }

    private void hideProgressBar() { if (progressBar != null) progressBar.setVisibility(View.GONE); }

}
