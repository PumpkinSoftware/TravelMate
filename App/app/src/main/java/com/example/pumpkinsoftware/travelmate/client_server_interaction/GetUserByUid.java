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
import com.android.volley.toolbox.JsonObjectRequest;

import com.example.pumpkinsoftware.travelmate.user.User;
import com.example.pumpkinsoftware.travelmate.users_adapter.UserAdapterReview;
import com.google.gson.JsonArray;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class GetUserByUid {
    private Context context;
    private ProgressBar progressBar;
    private User mUser;
    private ArrayList<User> users;
    UserAdapterReview adapterReview;
    private RecyclerView rvUser;

    public GetUserByUid(Context c) {
        context = c;
    }

    public GetUserByUid(Context c, ProgressBar progress) {
        context = c;
        progressBar = progress;
    }

    public GetUserByUid(Context c, RecyclerView r) {
        context = c;
        rvUser = r;
    }

    public void getUserFromServer(String query, RequestQueue mQueue, final ServerCallback callback) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, query, null, new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject response) {

                try {
                    JSONObject user = response;
                    String uid = user.getString("uid");
                    String name = user.getString("name");
                    String surname = user.getString("surname");
                    String birthday = user.getString("birthday");
                    String gender = user.getString("gender");
                    String relationship = user.getString("relationship");
                    String email = user.getString("email");
                    String descr = user.getString("description");
                    String photo = user.getString("avatar");
                    String cover = user.getString("cover");
                    double sumReviews = user.getDouble("sumReview");
                    int numReviews = user.getInt("numReview");

                    // Per ora non serve, vediamo se in futuro può essere utile
                    /*LinkedList<String> trips = new LinkedList<String>();
                    JSONArray t =  user.getJSONArray("trips");
                    for(int i = 0; i < t.length(); i++)
                        trips.add(t.getString(i));*/

                    hideProgressBar();
                    mUser = new User(uid, name, surname, birthday, gender, relationship, email,
                            descr, photo, cover, sumReviews, numReviews);
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

    public void getUserReviewFromServer(String query, RequestQueue mQueue) {
        users = new ArrayList<User>();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, query, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject user = response.getJSONObject(i);
                        String uid = user.getString("uid");
                        String name = user.getString("name");
                        String surname = user.getString("surname");
                        String photo = user.getString("avatar");
                        users.add(new User(uid, name, surname, photo));
                    }
                    adapterReview = new UserAdapterReview(users);
                    // Attach the adapter to the recyclerview to populate items
                    rvUser.setAdapter(adapterReview);
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

    private void hideProgressBar() {
        if (progressBar != null) progressBar.setVisibility(View.GONE);
    }

    public User getUser() {
        return mUser;
    }
}
