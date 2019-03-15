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

public class PostUser {
    private final static String URLNEW="https://debugtm.herokuapp.com/user/newUser";
    private final static String URLUPDATE="https://debugtm.herokuapp.com/user/updateUser";
    private Context contesto;
    public enum flag{NEW,UPDATE};
    private RequestQueue mQueue;

    public PostUser(Context c) {
        contesto=c;
    }

    public void jsonParse(JSONObject utente, final flag myflag){
        mQueue=Volley.newRequestQueue(contesto);
        final JsonObjectRequest JORequest = new JsonObjectRequest(Request.Method.POST, (myflag.equals(flag.NEW)?URLNEW:URLUPDATE), utente, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    //da controllare l'update
                    String status = response.getString("status");
                    if(status.equals("ok")) {
                        if(myflag.equals(flag.NEW)) {
                            Toast.makeText(contesto, "Conferma via email", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(contesto, "Profilo aggiornato", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        String err = response.getString("type");
                        handleError(err);
                    }

                } catch (JSONException e) {
                    Toast.makeText(contesto, "Errore: riprovare", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // error
                Toast.makeText(contesto, "Errore ", Toast.LENGTH_SHORT).show();
            }
        });
        // Add the request to the RequestQueue.
        mQueue.add(JORequest);
        mQueue.start();
    }


    //finire gestione errori
    private void handleError(String err) {
        if(err.equals("-1"))
            Toast.makeText(contesto, "Errore: riprovare", Toast.LENGTH_SHORT).show();
        else {
                Toast.makeText(contesto, "Errore: utente non trovato", Toast.LENGTH_SHORT).show();
        }
    }
}
