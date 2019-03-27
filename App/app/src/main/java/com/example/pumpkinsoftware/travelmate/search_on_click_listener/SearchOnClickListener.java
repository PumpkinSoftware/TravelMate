package com.example.pumpkinsoftware.travelmate.search_on_click_listener;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.android.volley.RequestQueue;
import com.example.pumpkinsoftware.travelmate.SearchResult;
import com.example.pumpkinsoftware.travelmate.Utils;
import com.example.pumpkinsoftware.travelmate.date_picker.EditTextDatePicker;


public class SearchOnClickListener implements View.OnClickListener {
    private final static String URL = Utils.SERVER_PATH + "trip/getTripsWithFilter?";
    private final static String MIN_BUDGET = "0";
    private final static String MAX_BUDGET = "2000";
    private final static String MIN_GROUP = "1";
    private final static String MAX_GROUP = "20";
    private Context context;
    private FragmentManager frag_manager;
    private TextInputEditText from, to;
    private EditTextDatePicker departure, ret;
    private EditText budgetMin, budgetMax, gruppoMin, gruppoMax;
    private RadioGroup vehicle_radio, tag_radio;

    private RequestQueue mQueue;
    private String stringaResult = "", query = "";

    public SearchOnClickListener(Context c, FragmentManager fm, TextInputEditText f, TextInputEditText t,
                                 EditTextDatePicker d, EditTextDatePicker r,
                                 RadioGroup vr, RadioGroup tr, EditText m1, EditText m2, EditText m3, EditText m4) {//, RequestQueue m) {
        context = c;
        frag_manager = fm;
        from = f;
        to = t;
        departure = d;
        ret = r;
        vehicle_radio = vr;
        tag_radio = tr;
        budgetMin = m1;
        budgetMax = m2;
        gruppoMin = m3;
        gruppoMax = m4;
        //mQueue = m;

    }


    @Override
    public void onClick(View v) {
        String from_q, to_q, min1_q, min2_q, max1_q, max2_q, departure_q, return_q, vehicle, tag ;

        //Get radio value

        RadioButton tmp1 = ((RadioButton) ((Activity) context).findViewById(vehicle_radio.getCheckedRadioButtonId()));
        if(tmp1!=null){
            vehicle=tmp1.getText().toString();
        }else {
            vehicle="";
        }

        //Non posso mettere le linee come nella search altrimenti questo non funziona perchè non trova il bottone
        RadioButton tmp2 = ((RadioButton) ((Activity) context).findViewById(tag_radio.getCheckedRadioButtonId()));
        if(tmp2!=null){
            tag=tmp2.getText().toString();
        }else{
            tag="";
        }

        Log.i("Dato4", vehicle);
        Log.i("Dato4", tag);


        // Get places
        from_q = (from.getText()).toString().toLowerCase();
        to_q = (to.getText()).toString().toLowerCase();

        // Get dates
        departure_q = departure.getSetMonth() + "/" + departure.getSetDay() + "/" + departure.getSetYear();
        return_q = ret.getSetMonth() + "/" + ret.getSetDay() + "/" + ret.getSetYear();

        // Get budget
        min1_q = (budgetMin.getText()).toString();
        max1_q = (budgetMax.getText()).toString();

        // Get group
        min2_q = (gruppoMin.getText()).toString();
        max2_q = (gruppoMax.getText()).toString();

        //costruzione della query
        query = URL;
        if (!to_q.isEmpty()) filter("destination", to_q.toLowerCase());
        if (!from_q.isEmpty()) filter("departure", from_q.toLowerCase());
        if (!departure_q.equals("-1/-1/-1")) filter("startDate", departure_q.toLowerCase());
        if (!return_q.equals("-1/-1/-1")) filter("endDate", return_q.toLowerCase());
        if (!vehicle.isEmpty()) filter("vehicle", vehicle.toLowerCase());
        if (!tag.isEmpty()) filter("tag", tag.toLowerCase());
        if (!min1_q.equals("1") && !min1_q.equals(MIN_BUDGET)) filter("minBudget", min1_q);
        if (!max1_q.equals(MAX_BUDGET)) filter("maxBudget", max1_q);
        if (!min2_q.equals(MIN_GROUP) && !min2_q.equals("0")) filter("minPartecipant", min2_q);
        if (!max2_q.equals(MAX_GROUP)) filter("maxPartecipant", max2_q);

            /*
            // Intent to start search activity
            Intent intent = new Intent(this, com.example.pumpkinsoftware.travelmate.SearchableActivity.class);
            intent.putExtra(EXTRA_MESSAGE, message);
            startActivity(intent);
            */

        context.startActivity(new Intent(this.context, SearchResult.class).putExtra(SearchResult.EXTRA_QUERY, query));
    }

    //altre funzioni

    private void filter(String categoria, String filtro) {
        //il primo valore non deve avere il simbolo "&"
        if (query.equals(URL))
            query += categoria + "=" + filtro;
        else
            query += "&" + categoria + "=" + filtro;
    }


}
