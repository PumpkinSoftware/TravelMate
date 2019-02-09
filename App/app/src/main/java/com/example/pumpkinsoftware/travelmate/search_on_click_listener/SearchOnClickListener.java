package com.example.pumpkinsoftware.travelmate.search_on_click_listener;

import com.android.volley.toolbox.JsonArrayRequest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.pumpkinsoftware.travelmate.CreationTrip;
import com.example.pumpkinsoftware.travelmate.MainActivity;
import com.example.pumpkinsoftware.travelmate.SearchFragment;
import com.example.pumpkinsoftware.travelmate.SearchResult;
import com.example.pumpkinsoftware.travelmate.ViaggiFragment;
import com.example.pumpkinsoftware.travelmate.edit_text_date_picker.EditTextDatePicker;
import com.example.pumpkinsoftware.travelmate.my_on_checked_change_listener.MyOnCheckedChangeListener;
import com.example.pumpkinsoftware.travelmate.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class SearchOnClickListener implements View.OnClickListener {
    private static String URL="http://192.168.1.107:8095/trip/getTripsWithFilter?";
    private static String MIN_BUDGET = "0";
    private static String MAX_BUDGET = "2000";
    private static String MIN_GROUP = "1";
    private static String MAX_GROUP = "20";
    private Context context;
    private FragmentManager frag_manager;
    private TextInputEditText from, to;
    private EditTextDatePicker departure, ret;
    private MyOnCheckedChangeListener switch_listener;
    private EditText budgetMin,budgetMax, gruppoMin,gruppoMax;

    private RequestQueue mQueue;
    private String stringaResult="", query="";

    public SearchOnClickListener(Context c, FragmentManager fm, TextInputEditText f, TextInputEditText t,
                                 EditTextDatePicker d, EditTextDatePicker r, MyOnCheckedChangeListener s,
                                 EditText m1, EditText m2, EditText m3, EditText m4,RequestQueue m) {
        context = c;
        frag_manager = fm;
        from = f;
        to = t;
        departure = d;
        ret = r;
        switch_listener = s;
        budgetMin = m1;
        budgetMax = m2;
        gruppoMin = m3;
        gruppoMax = m4;
        mQueue=m;
    }



    @Override
    public void onClick(View v) {
        String from_q,to_q,min1_q,min2_q,max1_q,max2_q,pets_value="",departure_q,return_q;

        /* Get places */
         from_q = (from.getText()).toString().toLowerCase();
         to_q = (to.getText()).toString().toLowerCase();

        /* Get dates */
        departure_q =  (new StringBuilder().append(departure.getSetMonth()).append("/")
                .append(departure.getSetDay()).append("/").append(departure.getSetYear())).toString();

        return_q =  (new StringBuilder().append(ret.getSetMonth()).append("/")
                .append(ret.getSetDay()).append("/").append(ret.getSetYear())).toString();

        /* Get switch value */

        pets_value = switch_listener.getValue();

        /* Get budget */
        min1_q = (budgetMin.getText()).toString();
        max1_q = (budgetMax.getText()).toString();

        /* Get group */
        min2_q = (gruppoMin.getText()).toString();
        max2_q = (gruppoMax.getText()).toString();

        //costruzione della query
        query=URL;
        if(!to_q.isEmpty())                             filter("destination",to_q.toLowerCase());
        if(!from_q.isEmpty())                           filter("departure",from_q.toLowerCase());
        if(!departure_q.equals("-1/-1/-1"))             filter("startDate",departure_q.toLowerCase());
        if(!return_q.equals("-1/-1/-1"))                filter("endDate",return_q.toLowerCase());
        if(pets_value.equals("true"))                   filter("pets","true");
        if(!min1_q.equals("1")&&!min1_q.equals("0"))    filter("minBudget",min1_q);
        if(!max1_q.equals("2000"))                      filter("maxBudget",max1_q);
        if(!min2_q.equals("1")&&!min2_q.equals("0"))    filter("minPartecipant",min2_q);
        if(!max2_q.equals("20"))                        filter("maxPartecipant",max2_q);

        /*
        // Intent to start search activity
        Intent intent = new Intent(this, com.example.pumpkinsoftware.travelmate.SearchableActivity.class);
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
        */


        //chiamata del server
        jsonParse();

        Intent i = new Intent(this.context,SearchResult.class);
        i.putExtra("result",stringaResult);
        context.startActivity(i);


        stringaResult="";
    }

    //altre funzioni

    private void jsonParse(){
        //Log.i("query",query);
        JsonArrayRequest request= new JsonArrayRequest(Request.Method.GET, query, null, new Response.Listener<JSONArray>() {
            public void onResponse(JSONArray response) {

                try {
                    //JSONArray jsonArray=response.getJSONArray("viaggi")
                    for(int i=0;i<response.length();i++){
                        JSONObject viaggio= response.getJSONObject(i);
                        String name =viaggio.getString("name");
                       // Log.i("viaggio",name);
                        stringaResult+=name+System.getProperty("line.separator");
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
    }

    private String filter(String categoria,String filtro){
        //il primo valore non deve avere il simbolo "&"
        if(query.equals(URL))
            return query+=categoria+"="+filtro;
        else
            return query+="&"+categoria+"="+filtro;
    }

}
