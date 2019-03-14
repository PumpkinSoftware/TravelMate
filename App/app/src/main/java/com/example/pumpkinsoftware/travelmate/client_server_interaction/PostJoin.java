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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class PostJoin {
    private Context context;

    public PostJoin(Context c) {
        context = c;
    }

    public enum request {JOIN, ABANDON, DELETE};

    public void send(String url, String tripId, String userId, final request request) {
        final RequestQueue mQueue = Volley.newRequestQueue(context);
        JSONObject jsonBody = new JSONObject();

        try {
            jsonBody.put("tripId", tripId);
            jsonBody.put("userUid", userId);
        } catch (JSONException e) {
            Toast.makeText(context, "Errore: riprovare", Toast.LENGTH_SHORT).show();
        }
        // final String requestBody = jsonBody.toString();
        final JsonObjectRequest JORequest = new JsonObjectRequest(Request.Method.POST, url, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if(request.equals(PostJoin.request.JOIN))
                    Toast.makeText(context, "Aggiunto al viaggio con successo", Toast.LENGTH_SHORT).show();
                else if(request.equals(PostJoin.request.ABANDON))
                    Toast.makeText(context, "Rimosso dal viaggio con successo", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(context, "Viaggio eliminato con successo", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // error
                Toast.makeText(context, "Errore ", Toast.LENGTH_SHORT).show();
            }
        });
        mQueue.add(JORequest);
        mQueue.start();

        /*StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //Log.i("VOLLEY", response);
                    if(request.equals(PostJoin.request.JOIN))
                        Toast.makeText(context, "Aggiunto al viaggio con successo", Toast.LENGTH_SHORT).show();
                    else if(request.equals(PostJoin.request.ABANDON))
                        Toast.makeText(context, "Rimosso dal viaggio con successo", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(context, "Viaggio eliminato con successo", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context, "Errore: connessione fallita", Toast.LENGTH_SHORT).show();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Log.e("VOLLEY", error.toString());
                Toast.makeText(context, "Errore: connessione assente", Toast.LENGTH_SHORT).show();
            }
        });
        mQueue.add(stringRequest);*/
    }

}
