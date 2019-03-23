package com.example.pumpkinsoftware.travelmate.client_server_interaction;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.pumpkinsoftware.travelmate.AccountRegisterActivity;
import com.example.pumpkinsoftware.travelmate.EditUserActivity;
import com.example.pumpkinsoftware.travelmate.handle_error.ErrorServer;


import org.json.JSONException;
import org.json.JSONObject;

public class PostUser {
    private final static String URLNEW = "https://debugtm.herokuapp.com/user/newUser";
    private final static String URLUPDATE = "https://debugtm.herokuapp.com/user/updateUser";
    private Context contesto;

    public enum flag {NEW, UPDATE}

    private RequestQueue mQueue;

    public PostUser(Context c) {
        contesto = c;
    }

    public void jsonParse(JSONObject utente, final flag myflag, final ServerCallback callback) {
       try {
            Log.i("sto per inviare il file",utente.getString("avatar"));
        } catch (JSONException e) {
           Log.i("non ho inviato il file","errore");
        }
        mQueue = Volley.newRequestQueue(contesto);
        final JsonObjectRequest JORequest = new JsonObjectRequest(Request.Method.POST, (myflag.equals(flag.NEW) ? URLNEW : URLUPDATE), utente, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    //System.out.println("SONO QUI");
                    String status = response.getString("status");
                    if (status.equals("success")) {
                        if (myflag.equals(flag.NEW)) {
                            Toast.makeText(contesto, "Conferma via email", Toast.LENGTH_SHORT).show();
                            AccountRegisterActivity.setStatus("OK");
                        } else {
                            Toast.makeText(contesto, "Profilo aggiornato", Toast.LENGTH_SHORT).show();
                            EditUserActivity.setStatus("OK");
                        }

                    } else {
                        String err = response.getString("type");
                        new ErrorServer(contesto).handleError(err);
                        if (myflag.equals(flag.NEW)) {
                            AccountRegisterActivity.setStatus("ERROR");
                        }else{
                            EditUserActivity.setStatus("ERROR");
                        }
                    }

                    callback.onSuccess(response);
                } catch (JSONException e) {
                    Toast.makeText(contesto, "Errore: riprovare", Toast.LENGTH_SHORT).show();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               // Log.d("ERRRORROR",error.toString());
                error.printStackTrace();
                // error
                Toast.makeText(contesto, "Errore ", Toast.LENGTH_SHORT).show();
            }

        });
        // Add the request to the RequestQueue.
        mQueue.add(JORequest);
        mQueue.start();

    }

}
