package com.example.pumpkinsoftware.travelmate.client_server_interaction;

import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.pumpkinsoftware.travelmate.trip.Trip;

import org.json.JSONException;
import org.json.JSONObject;

public class GetTripById {
    private Context context;
    private ProgressBar progressBar;
    private Trip trip;

    public GetTripById(Context c) {
        context = c;
    }

    public GetTripById(Context c, ProgressBar progress) {
        context = c;
        progressBar = progress;
    }

    public void getTripFromServer(String query, final ServerCallback callback) {
        final RequestQueue mQueue = Volley.newRequestQueue(context);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, query, null, new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject response) {

                try {
                    JSONObject travel = response;
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

                    hideProgressBar();
                    trip = new Trip(id, image, name, descr, departure, dest, budget,dep_date, end_date,
                            partecipants, group_max, tag, vehicle, owner);
                    callback.onSuccess(response);
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

    public Trip getTrip() { return trip; }
}
