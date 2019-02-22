package com.example.pumpkinsoftware.travelmate.client_server_interaction;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.pumpkinsoftware.travelmate.trip.Trip;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ClientServerInteraction {
    private ArrayList<Trip> trips = null;
    private Context context;

    public ClientServerInteraction(Context c) {
        context = c;
    }

    public ArrayList<Trip> getTripsFromServer(String query, RequestQueue mQueue) {
        if(trips == null)    trips = new ArrayList<Trip>();
        else                 trips.clear();

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, query, null, new Response.Listener<JSONArray>() {
            public void onResponse(JSONArray response) {

                try {
                    //JSONArray jsonArray=response.getJSONArray("viaggi")
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject travel = response.getJSONObject(i);
                        String name = travel.getString("name");
                        int budget = travel.getInt("budget");
                        int group_max = travel.getInt("maxPartecipant");
                        JSONArray part = travel.getJSONArray("partecipant");
                        int partecipants = part.length();
                        trips.add(new Trip("Trip id","Trip img", name, partecipants+"/"+group_max, budget));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "Error: data reception failed", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(context, "Error: connection with server failed ", Toast.LENGTH_SHORT).show();
            }
        });

        mQueue.add(request);
        return trips;
    }

}
