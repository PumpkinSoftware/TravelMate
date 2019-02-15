package com.example.pumpkinsoftware.travelmate;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pumpkinsoftware.travelmate.edit_text_date_picker.EditTextDatePicker;
import com.example.pumpkinsoftware.travelmate.my_on_checked_change_listener.MyOnCheckedChangeListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class CreationTrip extends AppCompatActivity {
    private final static String URL="http://192.168.1.107:8095/trip/newTrip/";
    protected String from_q,to_q,departure_q,return_q,nome_q,program_q;
    protected Double budget_q;
    protected Boolean pets_value;
    protected int group_q;
    private RequestQueue mQueue;
    Context contesto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation_trip);

        mQueue = Volley.newRequestQueue(this);
        contesto = this;

        //TOOLBAR
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // getSupportActionBar().setDisplayShowHomeEnabled(true);
        //toolbar.setTitle("New travel ?");
        //toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //FINE

        final EditText budget =  findViewById(R.id.budget_max_value);
        final EditText group =  findViewById(R.id.group_max_value);
        final TextInputEditText from = (TextInputEditText )findViewById(R.id.from_text);
        final TextInputEditText to = (TextInputEditText ) findViewById(R.id.to_text);
        final EditText departure_date = (EditText) findViewById(R.id.departure);
        final EditText return_date = (EditText) findViewById(R.id.ret);
        final Switch pets_switch = (Switch) findViewById(R.id.switch2);
        final EditText nome= findViewById(R.id.nametext);
        final EditText program= findViewById(R.id.plantext);

        final Calendar calendar = Calendar.getInstance();
        final EditTextDatePicker departure = new EditTextDatePicker( contesto, departure_date, calendar);
        final EditTextDatePicker ret = new EditTextDatePicker(contesto, return_date, calendar, departure);
        departure.setOther(ret);

        final MyOnCheckedChangeListener switch_listener = new MyOnCheckedChangeListener();
        pets_switch.setOnCheckedChangeListener(switch_listener);

        Button b_confirm = (Button) findViewById(R.id.confirm_button);
        b_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // valori da passare
                from_q=from.getText().toString().toLowerCase();
                to_q=to.getText().toString().toLowerCase();
                pets_value = Boolean.valueOf(switch_listener.getValue());
                nome_q=nome.getText().toString().toLowerCase();
                program_q=program.getText().toString().toLowerCase();
                departure_q =  departure.getSetMonth()+"/"+departure.getSetDay()+"/"+departure.getSetYear();
                return_q =  ret.getSetMonth()+"/"+ret.getSetDay()+"/"+ret.getSetYear();

                //Log.i("data",departure_q);
                //Log.i("data",return_q);

                if(from_q.isEmpty()) {
                   msgErrore("la partenza");
                }else if(to_q.isEmpty()){
                    msgErrore("la destinazione");
                }else if(departure_q.equals("-1/-1/-1")){
                    msgErrore("la data di partenza");
                }else if(return_q.equals("-1/-1/-1")){
                    msgErrore("la data di arrivo");
                }else if(budget.getText().toString().isEmpty()){
                    msgErrore("il budget");
                }else if( group.getText().toString().isEmpty()){
                    msgErrore("il numero del gruppo");
                }else if(program_q.isEmpty()){
                    msgErrore("una sintesi del programma");
                }else if(nome_q.isEmpty()){
                    msgErrore("il nome del viaggio");
                }

                else{

                    budget_q=Double.parseDouble(budget.getText().toString());
                    group_q=Integer.parseInt(group.getText().toString());
                    /*Log.i("Dato",nome_q);
                    Log.i("Dato",program_q);
                    Log.i("Dato",from_q);
                    Log.i("Dato",to_q);
                    Log.i("Dato",budget_q.toString());
                    Log.i("Dato",departure_q);
                    Log.i("Dato",return_q);
                    Log.i("Dato",pets_value.toString());
                    Log.i("Dato", String.valueOf(group_q));*/
                    JSONObject viaggio = new JSONObject();
                    try {
                        viaggio.put("name", nome_q);
                        viaggio.put("description", program_q);
                        viaggio.put("departure", from_q);
                        viaggio.put("destination", to_q);
                        viaggio.put("budget", budget_q);
                        viaggio.put("startDate", departure_q);
                        viaggio.put("endDate", return_q);
                        viaggio.put("pets", pets_value);
                        viaggio.put("maxPartecipant", group_q);
                        viaggio.put("image", "https://raw.githubusercontent.com/PumpkinSoftware/TravelMate/Back-End/Logo/trip.jpg");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    jsonParse();
                }
            }
        });

    }

    private void jsonParse() {
            final StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // your response
                            Toast.makeText(contesto, "Inserito correttamente", Toast.LENGTH_SHORT).show();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // error
                    Toast.makeText(contesto, "Errore ", Toast.LENGTH_SHORT).show();
                }
            });
            // Add the request to the RequestQueue.
        mQueue.add(stringRequest);
        mQueue.start();
    }

    private void msgErrore(String datoMancante){
        Toast.makeText(contesto, "Inserisci " + datoMancante, Toast.LENGTH_SHORT).show();
    }

}

