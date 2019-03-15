package com.example.pumpkinsoftware.travelmate.client_server_interaction;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class PostJoin {
    private Context context;

    public PostJoin(Context c) {
        context = c;
    }

    public enum request {JOIN, ABANDON, DELETE, CHANGE};

    public void send(String url, String tripId, String userId, final request request) {
        final RequestQueue mQueue = Volley.newRequestQueue(context);
        JSONObject jsonBody = new JSONObject();

        try {
            jsonBody.put("tripId", tripId);
            jsonBody.put("userUid", userId);
        } catch (JSONException e) {
            Toast.makeText(context, "Errore: riprovare", Toast.LENGTH_SHORT).show();
        }

        final JsonObjectRequest JORequest = new JsonObjectRequest(Request.Method.POST, url, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String status = response.getString("status");

                    if(status.equals("ok")) {
                        if(request.equals(PostJoin.request.JOIN))
                            Toast.makeText(context, "Aggiunto al viaggio con successo", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(context, "Rimosso dal viaggio con successo", Toast.LENGTH_SHORT).show();
                    }

                    else {
                        String err = response.getString("type");
                        handleError(err, request);
                    }

                } catch (JSONException e) {
                    Toast.makeText(context, "Errore: riprovare", Toast.LENGTH_SHORT).show();
                }

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
    }

    private void handleError(String err, request request) {
        if(err.equals("-1"))
            Toast.makeText(context, "Errore: riprovare", Toast.LENGTH_SHORT).show();

        if(request.equals(PostJoin.request.JOIN)) {
            if(err.equals("-7"))
                Toast.makeText(context, "Errore: nessun posto a disposizione", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(context, "Errore: utente già aggiunto al viaggio", Toast.LENGTH_SHORT).show();
        }

        else {
            if(err.equals("-2"))
                Toast.makeText(context, "Errore: utente non trovato", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(context, "Errore: viaggio non trovato", Toast.LENGTH_SHORT).show();
        }
    }
    private boolean isDeleted;

    public void delete(String query, final ServerCallback callback) {
        final RequestQueue mQueue = Volley.newRequestQueue(context);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, query, null, new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject response) {

                try {
                    String status = response.getString("status");

                    if(status.equals("ok")) {
                        Toast.makeText(context, "Viaggio eliminato con successo", Toast.LENGTH_SHORT).show();
                        isDeleted = true;
                    }
                    else
                        Toast.makeText(context, "Errore: riprovare", Toast.LENGTH_SHORT).show();

                    callback.onSuccess(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "Errore: connessione fallita", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(context, "Errore: connessione assente", Toast.LENGTH_SHORT).show();
            }
        });
        mQueue.add(request);
    }

    public boolean isDeleted() { return isDeleted; }

}
