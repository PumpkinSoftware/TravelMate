package com.example.pumpkinsoftware.travelmate.client_server_interaction;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.pumpkinsoftware.travelmate.handle_error.ErrorServer;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PostJoin {
    private Context context;

    public PostJoin(Context c) {
        context = c;
    }

    public enum request {JOIN, ABANDON, CHANGE};

    public void send(String url, String tripId, String userId, final request request, final String idToken, final ServerCallback callback) {
        final RequestQueue mQueue = Volley.newRequestQueue(context);
        JSONObject jsonBody = new JSONObject();

        try {
            jsonBody.put("tripId", tripId);
            if(request.equals(PostJoin.request.CHANGE))
                jsonBody.put("userUid", userId);
        } catch (JSONException e) {
            Toast.makeText(context, "Errore: riprovare", Toast.LENGTH_SHORT).show();
        }

        final JsonObjectRequest JORequest = new JsonObjectRequest(Request.Method.POST, url, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String status = response.getString("status");

                    if(status.equals("success")) {
                        if(request.equals(PostJoin.request.JOIN))
                            Toast.makeText(context, "Aggiunto al viaggio con successo", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(context, "Rimosso dal viaggio con successo", Toast.LENGTH_SHORT).show();
                    }

                    else {
                        String err = response.getString("type");
                        new ErrorServer(context).handleError(err);
                    }

                    callback.onSuccess(response);

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
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("access_token", idToken);
                return params;
            }
        };
        mQueue.add(JORequest);
        mQueue.start();
    }

    private boolean isDeleted;

    public void delete(String query, final String idToken, final ServerCallback callback) {
        final RequestQueue mQueue = Volley.newRequestQueue(context);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, query, null, new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject response) {

                try {
                    String status = response.getString("status");

                    if(status.equals("success")) {
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
        }) {
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

    public boolean isDeleted() { return isDeleted; }

}
