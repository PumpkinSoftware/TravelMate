package com.example.pumpkinsoftware.travelmate.client_server_interaction;

import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pumpkinsoftware.travelmate.handle_error.ErrorServer;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PostReview {
    private Context context;
    private ProgressBar progressBar;
    private RequestQueue mQueue;
    private String idToken;

    public PostReview(Context c, ProgressBar progress, RequestQueue rq, String id) {
        context = c;
        progressBar = progress;
        mQueue = rq;
        idToken = id;
    }

    public void send(String url, JSONObject review) {
        final JsonObjectRequest JORequest = new JsonObjectRequest(Request.Method.POST, url, review, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    //da controllare l'update
                    String status = response.getString("status");
                    if (status.equals("success")) {
                        Toast.makeText(context, "Recensione aggiunta", Toast.LENGTH_SHORT).show();
                    } else {
                        String err = response.getString("type");
                        new ErrorServer(context).handleError(err);
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
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("access_token", idToken);
                return params;
            }
        };
        // Add the request to the RequestQueue.
        mQueue.add(JORequest);
        mQueue.start();
    }

    private void hideProgressBar() {
        if (progressBar != null) progressBar.setVisibility(View.GONE);
    }
}
