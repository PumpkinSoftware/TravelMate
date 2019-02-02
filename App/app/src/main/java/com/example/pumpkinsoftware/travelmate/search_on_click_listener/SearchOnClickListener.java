package com.example.pumpkinsoftware.travelmate.search_on_click_listener;

import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pumpkinsoftware.travelmate.SearchResults;
import com.example.pumpkinsoftware.travelmate.edit_text_date_picker.EditTextDatePicker;
import com.example.pumpkinsoftware.travelmate.my_on_checked_change_listener.MyOnCheckedChangeListener;
import com.example.pumpkinsoftware.travelmate.R;

import org.json.JSONObject;


public class SearchOnClickListener implements View.OnClickListener {
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

    public SearchOnClickListener(Context c, FragmentManager fm, TextInputEditText f, TextInputEditText t,
                                 EditTextDatePicker d, EditTextDatePicker r, MyOnCheckedChangeListener s,
                                 EditText m1, EditText m2, EditText m3, EditText m4) {
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
    }

    @Override
    public void onClick(View v) {

        /* SecondFragement frag = new SecondFragement();

        getActivity().getFragmentManager().beginTransaction().replace(R.id, frag).commit();*/
        SearchResults nextFrag= new SearchResults();
        frag_manager.beginTransaction()
                .replace(R.id.search_layout, nextFrag)
                .addToBackStack(null)
                .commit();

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
        for(int i=Integer.parseInt(min2_q); i<=Integer.parseInt(max2_q); i++) {

            String query = "http://localhost:8095/trip/getTripsWithFilter?destination=" + to_q + "&departure=" + from_q +
                    "&minBudget=" + min1_q + "&maxBudget=" + max1_q + "&startDate=" + departure_q + "&endDate=" + return_q +
                    "&maxPartecipant=" + i + "&pets=" + pets_value;

            Toast.makeText(context, "Request: " + query, Toast.LENGTH_SHORT).show();


            // Instantiate the RequestQueue.
            RequestQueue queue = Volley.newRequestQueue(context);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.GET, query, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            // TODO: Handle response
                            Toast.makeText(context, "Response: " + response.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO: Handle error
                            Toast.makeText(context, "Error: connection with server failed", Toast.LENGTH_SHORT).show();
                        }
                    });

            // Add the request to the RequestQueue.
            queue.add(jsonObjectRequest);

        }


    }


}
