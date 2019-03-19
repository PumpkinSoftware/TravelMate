package com.example.pumpkinsoftware.travelmate;

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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.pumpkinsoftware.travelmate.date_picker.EditTextDatePicker;
import com.example.pumpkinsoftware.travelmate.handle_error.ErrorServer;
import com.example.pumpkinsoftware.travelmate.trip.Trip;
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

public class EditTravelActivity extends AppCompatActivity {
    public final static String EXTRA_TRAVEL = "travelmate_extra_eta_TRIP";
    private final static String URL = "https://debugtm.herokuapp.com/trip/updateTrip/";
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
    private String pathrandom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation_trip);

        context = (Context) this;
        // file per firebase
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        pathrandom = UUID.randomUUID().toString();

        Bundle b = getIntent().getExtras();
        trip = (Trip) b.getSerializable(EXTRA_TRAVEL);

        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar.setTitle("Modifica evento");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
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
        startDate.setText(trip.getStartDate());

        final EditText endDate = findViewById(R.id.ret);
        endDate.setText(trip.getEndDate());

        // Date starts from tomorrow
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, +1);
        final EditTextDatePicker departure = new EditTextDatePicker(this, startDate, calendar);
        final EditTextDatePicker ret = new EditTextDatePicker(this, endDate, calendar, departure);
        departure.setOther(ret);

        RadioButton radioButton;
        if(trip.getVehicle().equals("treno")) radioButton = findViewById(R.id.treno);
        else                                  radioButton = findViewById(R.id.auto);
        radioButton.setChecked(true);

        final EditText budget = findViewById(R.id.budget_max_value);
        budget.setText(trip.getBudget());

        final EditText partecipants = findViewById(R.id.group_max_value);
        partecipants.setText(String.valueOf(trip.getGroupNumber()));

        final TextInputEditText descr = findViewById(R.id.plantext);
        descr.setText(trip.getDescr());

        final TextInputEditText name = findViewById(R.id.nametext);
        name.setText(trip.getName());

        String t_tag = trip.getTag();
        if(t_tag.equals("cultura"))                 radioButton = findViewById(R.id.tag1);
        else if(t_tag.equals("intrattenimento"))    radioButton = findViewById(R.id.tag2);
        else if(t_tag.equals("musica"))             radioButton = findViewById(R.id.tag3);
        else                                        radioButton = findViewById(R.id.tag4);
        radioButton.setChecked(true);

        b_upload = findViewById(R.id.photo_upload);
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
                String from_q = from.getText().toString().toLowerCase();
                String to_q = to.getText().toString().toLowerCase();
                String nome_q = name.getText().toString().toLowerCase();
                String program_q = descr.getText().toString().toLowerCase();
                String departure_q = departure.getSetMonth() + "/" + departure.getSetDay() + "/" + departure.getSetYear();
                String return_q = ret.getSetMonth() + "/" + ret.getSetDay() + "/" + ret.getSetYear();

                double budget_q = Double.parseDouble(budget.getText().toString());
                int group_q = Integer.parseInt(partecipants.getText().toString());

                JSONObject viaggio = new JSONObject();
                if (!departure_q.equals("-1/-1/-1")) {

                    try {
                        viaggio.put("startDate", departure_q);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (!return_q.equals("-1/-1/-1")) {
                    try {
                        viaggio.put("endDate", return_q);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    viaggio.put("_id", trip.getId());
                    viaggio.put("name", nome_q);
                    viaggio.put("description", program_q);
                    viaggio.put("departure", from_q);
                    viaggio.put("destination", to_q);
                    viaggio.put("budget", budget_q);

                    viaggio.put("vehicle", vehicle);
                    viaggio.put("tag", tag);
                    viaggio.put("maxPartecipant", group_q);
                    //FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    //if (user != null) viaggio.put("owner", user.getUid());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                uploadImage(viaggio);
                Intent intent = new Intent();
                trip.setBudget(String.valueOf(budget_q));
                trip.setDescr(program_q);
                trip.setGroup_number(group_q);
                trip.setVehicle(vehicle);
                // TODO set all editable values
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
            final StorageReference ref = storageReference.child("tripImage/" + pathrandom);
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
                                        e.printStackTrace();
                                    }
                                    //Qui richiami mongoDB per creare il trip
                                    jsonParse(viaggio);
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
                                e1.printStackTrace();
                            }
                            Toast.makeText(context, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
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
        }

        else
            jsonParse(viaggio);
    }

    private void jsonParse(JSONObject viaggio) {
        RequestQueue mQueue = Volley.newRequestQueue(this);
        final JsonObjectRequest JORequest = new JsonObjectRequest(Request.Method.POST, URL, viaggio, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    //da controllare l'update
                    String status = response.getString("status");
                    if (status.equals("ok")) {
                        Toast.makeText(context, "Evento modificato correttamente", Toast.LENGTH_SHORT).show();
                    } else {
                        String err = response.getString("type");
                        new ErrorServer(context).handleError(err);
                        deleteTrip();
                    }

                } catch (JSONException e) {
                    Toast.makeText(context, "Errore: riprovare", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // error
                Toast.makeText(context, "Errore ", Toast.LENGTH_SHORT).show();
            }
        });
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
            try {
                //codice per mostrare l'anteprima
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                b_upload.setImageBitmap(bitmap);
                // codice per mostrare il path
                //TextView path = findViewById(R.id.photo_text);
                //path.setText(filePath.getLastPathSegment());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void deleteTrip() {
        StorageReference storageRef = storageReference.child("tripImage/"+pathrandom);
        storageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
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

    /*@Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra(TravelDetailsActivity.EXTRA_TRIP, trip);
        setResult(RESULT_OK, intent);
        finish();
    }*/

}