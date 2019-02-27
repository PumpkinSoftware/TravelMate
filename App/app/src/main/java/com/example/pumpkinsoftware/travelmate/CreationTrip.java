package com.example.pumpkinsoftware.travelmate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pumpkinsoftware.travelmate.edit_text_date_picker.EditTextDatePicker;
import com.example.pumpkinsoftware.travelmate.my_on_checked_change_listener.MyOnCheckedChangeListener;
import com.gc.materialdesign.widgets.ProgressDialog;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;
import java.util.UUID;

public class CreationTrip extends AppCompatActivity {
    private final static String URL="https://debugtm.herokuapp.com/trip/newTrip/";
    protected String from_q,to_q,departure_q,return_q,nome_q,program_q,vehicle="",tag="";
    protected Double budget_q;
    protected int group_q;
    private RequestQueue mQueue;
    Context contesto;

    //foto
    private Button b_upload;
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 71;

    //Firebase
    FirebaseStorage storage;
    StorageReference storageReference;

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
                finish();
            }
        });
        //FINE

        // file per firebase
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        // campi
        final EditText budget =  findViewById(R.id.budget_max_value);
        final EditText group =  findViewById(R.id.group_max_value);
        final TextInputEditText from = (TextInputEditText )findViewById(R.id.from_text);
        final TextInputEditText to = (TextInputEditText ) findViewById(R.id.to_text);
        final EditText departure_date = (EditText) findViewById(R.id.departure);
        final EditText return_date = (EditText) findViewById(R.id.ret);

        final EditText nome= findViewById(R.id.nametext);
        final EditText program= findViewById(R.id.plantext);

        final Calendar calendar = Calendar.getInstance();
        final EditTextDatePicker departure = new EditTextDatePicker( contesto, departure_date, calendar);
        final EditTextDatePicker ret = new EditTextDatePicker(contesto, return_date, calendar, departure);
        departure.setOther(ret);


        b_upload = (Button) findViewById(R.id.photo_upload);
        b_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });


        Button b_confirm = (Button) findViewById(R.id.confirm_button);
        b_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // valori da passare
                from_q=from.getText().toString().toLowerCase();
                to_q=to.getText().toString().toLowerCase();

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
                }else if(vehicle.isEmpty()){
                    msgErrore("il veicolo");
                }else if(budget.getText().toString().isEmpty()){
                    msgErrore("il budget");
                }else if( group.getText().toString().isEmpty()){
                    msgErrore("il numero del gruppo");
                }else if(program_q.isEmpty()){
                    msgErrore("una sintesi del programma");
                }else if(nome_q.isEmpty()){
                    msgErrore("il nome del viaggio");
                }else if(tag.isEmpty()){
                    msgErrore("il tipo di evento");
                }
                else{

                    budget_q=Double.parseDouble(budget.getText().toString());
                    group_q=Integer.parseInt(group.getText().toString());

                    JSONObject viaggio = new JSONObject();
                    try {
                        viaggio.put("name", nome_q);
                        viaggio.put("description", program_q);
                        viaggio.put("departure", from_q);
                        viaggio.put("destination", to_q);
                        viaggio.put("budget", budget_q);
                        viaggio.put("startDate", departure_q);
                        viaggio.put("endDate", return_q);
                        viaggio.put("vehicle",vehicle);
                        viaggio.put("tag",tag);
                        viaggio.put("maxPartecipant", group_q);
                        viaggio.put("owner","default");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    uploadImage(viaggio);
                    finish();
                }
            }
        });

    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            //codice per mostrare l'anteprima
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //imageView.setImageBitmap(bitmap);
                TextView path=findViewById(R.id.photo_text);
                path.setText(filePath.getLastPathSegment());
            }
            catch (IOException e){
                e.printStackTrace(); }
        }
    }

    private void uploadImage(final JSONObject viaggio) {

        if(filePath != null)
        {

            //final ProgressDialog progressDialog = new ProgressDialog(this.contesto);
           // progressDialog.setTitle("Creazione viaggio in corso...");
           // progressDialog.show();

            final StorageReference ref = storageReference.child("tripImage/"+ UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    try {
                                        viaggio.put("image", uri.toString());
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    //Qui richiami mongoDB per creare il trip
                                    jsonParse(viaggio);
                                    //Ricorda di lasciare un commento qui per ricordarci di gestire l'errore lato MongoDB

                                    // progressDialog.dismiss();
                                    Toast.makeText(contesto, "Viaggio creato correttamente.", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //progressDialog.dismiss();
                            Toast.makeText(contesto, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                           // progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
    }

    private void jsonParse(JSONObject viaggio) {
            final JsonObjectRequest JORequest = new JsonObjectRequest(Request.Method.POST,URL,viaggio, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
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
        mQueue.add(JORequest);
        mQueue.start();
    }

    private void msgErrore(String datoMancante){
        Toast.makeText(contesto, "Inserisci " + datoMancante, Toast.LENGTH_SHORT).show();
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.auto:
                if (checked)
                    vehicle="auto";
                    break;
            case R.id.treno:
                if (checked)
                    vehicle="treno";
                    break;
            case R.id.tag1:
                if (checked)
                    tag="cultura";
                break;
            case R.id.tag2:
                if (checked)
                    tag="intrattenimento";
                break;
            case R.id.tag3:
                if (checked)
                    tag="musica";
                break;
            case R.id.tag4:
                if (checked)
                    tag="tecnologia";
                break;
        }
    }
}

