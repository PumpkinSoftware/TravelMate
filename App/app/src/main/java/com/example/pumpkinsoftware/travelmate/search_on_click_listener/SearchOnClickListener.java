package com.example.pumpkinsoftware.travelmate.search_on_click_listener;

import com.example.pumpkinsoftware.travelmate.SearchResults;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.pumpkinsoftware.travelmate.edit_text_date_picker.EditTextDatePicker;
import com.example.pumpkinsoftware.travelmate.my_on_checked_change_listener.MyOnCheckedChangeListener;
import com.example.pumpkinsoftware.travelmate.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class SearchOnClickListener implements View.OnClickListener {
    private static final  String URL="https://api.myjson.com/bins/97vf0";
    private Context context;
    private FragmentManager frag_manager;
    private TextInputEditText from;
    private TextInputEditText to;
    private EditTextDatePicker departure;
    private EditTextDatePicker ret;
    private MyOnCheckedChangeListener switch_listener;
    private EditText min1;
    private EditText max1;
    private EditText min2;
    private EditText max2;

    private RequestQueue mQueue;
    private String stringaResult="";
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
        min1 = m1;
        max1 = m2;
        min2 = m3;
        max2 = m4;
        mQueue=m;
    }

    @Override
    public void onClick(View v) {

        /* SecondFragement frag = new SecondFragement();

        getActivity().getFragmentManager().beginTransaction().replace(R.id, frag).commit();*/


        /* =======================
                    QUERY
           ======================= */

        /* Get places */
        String from_q = (from.getText()).toString().toLowerCase();
        String to_q = (to.getText()).toString().toLowerCase();

        /* Get dates */
        StringBuilder departure_q =  new StringBuilder().append(departure.getSetMonth()).append("/")
                .append(departure.getSetDay()).append("/").append(departure.getSetYear());

        StringBuilder return_q =  new StringBuilder().append(ret.getSetMonth()).append("/")
                .append(ret.getSetDay()).append("/").append(ret.getSetYear());

        /* Get switch value */
        String pets_value = "false";
        pets_value = switch_listener.getValue();

        /* Get budget */
        String min1_q = (min1.getText()).toString();
        String max1_q = (max1.getText()).toString();

        /* Get group */
        String min2_q = (min2.getText()).toString();
        String max2_q = (max2.getText()).toString();

        // Cycle to obtain n different queries in group range, n = ma2 - mi2
        /*
        for(int i=Integer.parseInt(min2_q); i<=Integer.parseInt(max2_q); i++) {

            String query = "http://localhost:8095/trip/getTripsWithFilter?destination=" + to_q + "&departure=" + from_q +
                    "&minBudget=" + min1_q + "&maxBudget=" + max1_q + "&startDate=" + departure_q + "&endDate=" + return_q +
                    "&maxPartecipant=" + i + "&pets=" + pets_value;

            Toast.makeText(context, "Request: " + query, Toast.LENGTH_SHORT).show();



        }*/
       jsonParse();
      // Log.i("ciao",stringaResult);
       SearchResults nextFrag=new SearchResults();
       //si può fare direttamente nextFrag=serArguments(stringaResult);
       Bundle args = new Bundle();
       args.putString("Key", stringaResult);
       nextFrag.setArguments(args);
       frag_manager.beginTransaction()
                .replace(R.id.search_layout, nextFrag)
                .addToBackStack(null)
                .commit();
       stringaResult="";
    }
    private void jsonParse(){

        //Log.i("ciao","Ciao1");
        JsonObjectRequest request= new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray=response.getJSONArray("employees");
                    // Log.i("ciao","Ciao2");
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject employee= jsonArray.getJSONObject(i);
                        String name =employee.getString("firstname");
                       // Log.i("ciao","Ciao3");
                      //  Toast.makeText(context, "Response: " + name, Toast.LENGTH_SHORT).show();
                        stringaResult+=name+System.getProperty("line.separator");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "Error: connection with server failed", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(context, "Error: connection with server failed", Toast.LENGTH_SHORT).show();
            }
        });
        mQueue.add(request);
    }


}
