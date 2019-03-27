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
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.pumpkinsoftware.travelmate.date_picker.EditTextDatePicker;
import com.example.pumpkinsoftware.travelmate.glide.GlideApp;
import com.example.pumpkinsoftware.travelmate.handle_error.ErrorServer;
import com.example.pumpkinsoftware.travelmate.min_max_filter.MinMaxFilter;
import com.example.pumpkinsoftware.travelmate.trip.Trip;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EditTravelActivity extends AppCompatActivity {
    public final static String EXTRA_TRAVEL = "travelmate_extra_eta_TRIP";
    private final static String URL = Utils.SERVER_PATH + "trip/updateTrip/";
    private Context context;
    private Trip trip;
    private String vehicle;
    private String tag;
    //Photo
    private ImageView b_upload;
    private final int PICK_IMAGE_REQUEST = 71;
    private Uri filePath;
    //Firebase
    FirebaseStorage storage;
    StorageReference storageReference;
    FirebaseUser user;
    // Filters on input
    private final static int MIN_BUDGET = 0;
    private final static int MAX_BUDGET = 500;
    private final static int MIN_PARTECIPANTS = 1;
    private final static int MAX_PARTECIPANTS = 15;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation_trip);

        context = (Context) this;

        // file per firebase
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();

        Bundle b = getIntent().getExtras();
        trip = (Trip) b.getSerializable(EXTRA_TRAVEL);

        progressBar = findViewById(R.id.indeterminateBar);

        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                finish();
            }
        });

        final TextView title = findViewById(R.id.title);
        title.setText("Modifica evento");

        final TextInputEditText from = findViewById(R.id.from_text);
        from.setText(trip.getDeparture());

        final TextInputEditText to = findViewById(R.id.to_text);
        to.setText(trip.getDest());

        final EditText startDate = findViewById(R.id.departure);
        startDate.setText(inverseDate(trip.getStartDate()));

        final EditText endDate = findViewById(R.id.ret);
        endDate.setText(inverseDate(trip.getEndDate()));

        // Date starts from tomorrow
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, +1);
        final EditTextDatePicker departure = new EditTextDatePicker(this, startDate, calendar);
        final EditTextDatePicker ret = new EditTextDatePicker(this, endDate, calendar, departure);
        departure.setOther(ret);

        RadioButton radioButton;
        String vehicle_t = trip.getVehicle();
        if (vehicle_t.equals("treno")) radioButton = findViewById(R.id.treno);
        else radioButton = findViewById(R.id.auto);
        radioButton.setChecked(true);
        vehicle = vehicle_t;

        final EditText budget = findViewById(R.id.budget_max_value);
        budget.setText(trip.getBudget());
        budget.setFilters(new InputFilter[]{new MinMaxFilter(MIN_BUDGET, MAX_BUDGET)});

        final EditText partecipants = findViewById(R.id.group_max_value);
        partecipants.setText(String.valueOf(trip.getGroupNumber()));
        partecipants.setFilters(new InputFilter[]{new MinMaxFilter(MIN_PARTECIPANTS, MAX_PARTECIPANTS)});

        final TextInputEditText descr = findViewById(R.id.plantext);
        descr.setText(trip.getDescr());

        final TextInputEditText name = findViewById(R.id.nametext);
        name.setText(trip.getName());

        String t_tag = trip.getTag();
        if (t_tag.equals("cultura")) radioButton = findViewById(R.id.tag1);
        else if (t_tag.equals("intrattenimento")) radioButton = findViewById(R.id.tag2);
        else if (t_tag.equals("musica")) radioButton = findViewById(R.id.tag3);
        else radioButton = findViewById(R.id.tag4);
        radioButton.setChecked(true);
        tag = t_tag;

        b_upload = findViewById(R.id.photo_upload);
        String img = trip.getImage();
        GlideApp.with(context)
                .load(img.isEmpty() ? (R.mipmap.default_trip) : img)
                .into(b_upload);
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

                progressBar.setVisibility(View.VISIBLE);

                // valori da passare
                String from_q = from.getText().toString().toLowerCase();
                String to_q = to.getText().toString().toLowerCase();
                String nome_q = name.getText().toString().toLowerCase();
                String program_q = descr.getText().toString().toLowerCase();
                String departure_q = departure.getSetMonth() + "/" + departure.getSetDay() + "/" + departure.getSetYear();
                String return_q = ret.getSetMonth() + "/" + ret.getSetDay() + "/" + ret.getSetYear();

                // Useful to check server side if current user is actually owner (Ridundant)
                /*FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String userUid = null;
                if (user != null)
                    userUid = user.getUid();*/

                String budget_t = budget.getText().toString();
                double budget_q = 0;
                int group_q = 0;
                try {
                    budget_q = Double.parseDouble(budget_t);
                    group_q = Integer.parseInt(partecipants.getText().toString());
                } catch (Exception e) {
                    progressBar.setVisibility(View.GONE);
                    e.printStackTrace();
                }

                JSONObject viaggio = new JSONObject();
                // Check if values have been modified
                if (!departure_q.equals("-1/-1/-1")) {

                    try {
                        viaggio.put("startDate", departure_q);
                        trip.setStartDate(departure_q);
                    } catch (JSONException e) {
                        progressBar.setVisibility(View.GONE);
                        e.printStackTrace();
                    }
                }

                if (!return_q.equals("-1/-1/-1")) {
                    try {
                        viaggio.put("endDate", return_q);
                        trip.setEndDate(return_q);
                    } catch (JSONException e) {
                        progressBar.setVisibility(View.GONE);
                        e.printStackTrace();
                    }
                }

                try {
                    //viaggio.put("userUid", userUid);
                    viaggio.put("_id", trip.getId());

                    if (!nome_q.isEmpty() && !nome_q.equals(trip.getName())) {
                        viaggio.put("name", nome_q);
                        trip.setName(nome_q);
                    }

                    if (!program_q.isEmpty() && !program_q.equals(trip.getDescr())) {
                        viaggio.put("description", program_q);
                        trip.setDescr(program_q);
                    }

                    if (!from_q.isEmpty() && !from_q.equals(trip.getDeparture())) {
                        viaggio.put("departure", from_q);
                        trip.setDeparture(from_q);
                    }

                    if (!to_q.isEmpty() && !to_q.equals(trip.getDest())) {
                        viaggio.put("destination", to_q);
                        trip.setDest(to_q);
                    }

                    if (!budget_t.isEmpty() && !budget_t.equals(trip.getBudget())) {
                        viaggio.put("budget", budget_q);
                        trip.setBudget(String.valueOf((int) budget_q));
                    }

                    if (!vehicle.equals(trip.getVehicle())) {
                        viaggio.put("vehicle", vehicle);
                        trip.setVehicle(vehicle);
                    }

                    if (!tag.equals(trip.getTag())) {
                        viaggio.put("tag", tag);
                        trip.setTag(tag);
                    }

                    if (group_q > 1 && group_q != trip.getPartecipantsNumber()) {
                        viaggio.put("maxPartecipant", group_q);
                        trip.setGroup_number(group_q);
                    }

                    //FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    //if (user != null) viaggio.put("owner", user.getUid());

                } catch (JSONException e) {
                    progressBar.setVisibility(View.GONE);
                    e.printStackTrace();
                }
                uploadImage(viaggio);
                progressBar.setVisibility(View.GONE);
                Intent intent = new Intent();
                intent.putExtra(TravelDetailsActivity.EXTRA_TRIP, trip);
                setResult(RESULT_OK, intent);
                finish();

            }
        });
    }


    private void uploadImage(final JSONObject viaggio) {

        if (filePath != null) {
            //final ProgressDialog progressDialog = new ProgressDialog(this.contesto);
            // progressDialog.setTitle("Creazione viaggio in corso...");
            // progressDialog.show();
            trip.setImage(filePath.toString());
            final StorageReference ref = storageReference.child("tripImage/" + UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    try {
                                        //Log.i("Dato",uri.toString());
                                        viaggio.put("image", (uri.toString()));
                                    } catch (JSONException e) {
                                        progressBar.setVisibility(View.GONE);
                                        e.printStackTrace();
                                    }
                                    //Qui richiami mongoDB per creare il trip
                                    user.getIdToken(true)
                                            .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                                                public void onComplete(@NonNull Task<GetTokenResult> task) {
                                                    if (task.isSuccessful()) {
                                                        String idToken = task.getResult().getToken();
                                                        // Send token to your backend via HTTPS
                                                        jsonParse(viaggio, idToken);
                                                        // ...
                                                    } else {
                                                        // Handle error -> task.getException();
                                                        progressBar.setVisibility(View.GONE);
                                                        Toast.makeText(context, "Riprova", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                    //Ricorda di lasciare un commento qui per ricordarci di gestire l'errore lato MongoDB
                                    // progressDialog.dismiss();
                                    //Toast.makeText(contesto, "Viaggio creato correttamente.", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //progressDialog.dismiss();
                            try {
                                viaggio.put("image", (""));
                            } catch (JSONException e1) {
                                progressBar.setVisibility(View.GONE);
                                e1.printStackTrace();
                            }
                            user.getIdToken(true)
                                    .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                                        public void onComplete(@NonNull Task<GetTokenResult> task) {
                                            if (task.isSuccessful()) {
                                                String idToken = task.getResult().getToken();
                                                // Send token to your backend via HTTPS
                                                jsonParse(viaggio, idToken);
                                                // ...
                                            } else {
                                                // Handle error -> task.getException();
                                                progressBar.setVisibility(View.GONE);
                                                Toast.makeText(context, "Riprova", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                    .getTotalByteCount());
                            // progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });

        } else
            user.getIdToken(true)
                    .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                        public void onComplete(@NonNull Task<GetTokenResult> task) {
                            if (task.isSuccessful()) {
                                String idToken = task.getResult().getToken();
                                // Send token to your backend via HTTPS
                                jsonParse(viaggio, idToken);
                                // ...
                            } else {
                                // Handle error -> task.getException();
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(context, "Riprova", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
    }


    private void jsonParse(final JSONObject viaggio, final String idToken) {
        RequestQueue mQueue = Volley.newRequestQueue(this);
        final JsonObjectRequest JORequest = new JsonObjectRequest(Request.Method.POST, URL, viaggio, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    //da controllare l'update
                    String status = response.getString("status");
                    if (status.equals("success")) {
                        Toast.makeText(context, "Evento modificato correttamente", Toast.LENGTH_SHORT).show();
                        if (!trip.getImage().equals("")) {
                            deleteImg(storage.getReferenceFromUrl(trip.getImage()));
                        }
                    } else {
                        String err = response.getString("type");
                        new ErrorServer(context).handleError(err);
                        deleteImg(storage.getReferenceFromUrl(viaggio.getString("image")));
                    }

                } catch (JSONException e) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(context, "Errore: riprovare", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // error
                progressBar.setVisibility(View.GONE);
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

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.auto:
                if (checked)
                    vehicle = "auto";
                break;
            case R.id.treno:
                if (checked)
                    vehicle = "treno";
                break;
            case R.id.tag1:
                if (checked)
                    tag = "cultura";
                break;
            case R.id.tag2:
                if (checked)
                    tag = "intrattenimento";
                break;
            case R.id.tag3:
                if (checked)
                    tag = "musica";
                break;
            default:
                if (checked)
                    tag = "tecnologia";
                break;
        }
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData();

            //codice per mostrare l'anteprima

            GlideApp.with(context).load(filePath).into(b_upload);
            // codice per mostrare il path
            //TextView path = findViewById(R.id.photo_text);
            //path.setText(filePath.getLastPathSegment());

        }
    }


    private void deleteImg(StorageReference image) {
        image.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // File deleted successfully
                // Log.d(TAG, "onSuccess: deleted file");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Uh-oh, an error occurred!
                // Log.d(TAG, "onFailure: did not delete file");
            }
        });
    }

    private String inverseDate(String s) {
        String data[] = s.split("-");
        return data[2].substring(0, 2) + "/" + data[1] + "/" + data[0];
    }

}
/*
* from_q = from.getText().toString().toLowerCase();
                to_q = to.getText().toString().toLowerCase();

                nome_q = nome.getText().toString().toLowerCase();
                program_q = program.getText().toString().toLowerCase();
                departure_q = departure.getSetMonth() + "/" + departure.getSetDay() + "/" + departure.getSetYear();
                return_q = ret.getSetMonth() + "/" + ret.getSetDay() + "/" + ret.getSetYear();
                group=partecipats.getText();

                if (from_q.isEmpty()) {
                    msgErrore("la partenza");
                } else if (to_q.isEmpty()) {
                    msgErrore("la destinazione");
                } else if (departure_q.equals("-1/-1/-1")) {
                    msgErrore("la data di partenza");
                } else if (return_q.equals("-1/-1/-1")) {
                    msgErrore("la data di arrivo");
                } else if (vehicle.isEmpty()) {
                    msgErrore("il veicolo");
                } else if (budget.getText().toString().isEmpty()) {
                    msgErrore("il budget");
                } else if (Integer.parseInt(budget) < 0 || (Integer.parseInt(budget) > 500)) {
                    Toast.makeText(contesto, "Valore budget non valido", Toast.LENGTH_SHORT).show();
                } else if (group.getText().toString().isEmpty()) {
                    msgErrore("il numero del gruppo");
                } else if (Integer.parseInt(group.getText().toString()) < 2 || (Integer.parseInt(group.getText().toString()) > 15)) {
                    Toast.makeText(contesto, "Valore gruppo non valido", Toast.LENGTH_SHORT).show();
                } else if (program_q.isEmpty()) {
                    msgErrore("una sintesi del programma");
                } else if (nome_q.isEmpty()) {
                    msgErrore("il nome del viaggio");
                } else if (tag.isEmpty()) {
                    msgErrore("il tipo di evento");
                } else {
*
* private void msgErrore(String datoMancante) {
        Toast.makeText(contesto, "Inserisci " + datoMancante, Toast.LENGTH_SHORT).show();
    }
*
* */