package com.example.pumpkinsoftware.travelmate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.pumpkinsoftware.travelmate.date_picker.EditTextDatePicker;
import com.example.pumpkinsoftware.travelmate.glide.GlideApp;
import com.example.pumpkinsoftware.travelmate.handle_error.ErrorServer;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;
import java.util.UUID;

@RequiresApi(api = Build.VERSION_CODES.M)
public class CreationTrip extends AppCompatActivity {
    private final static String URL = "https://debugtm.herokuapp.com/trip/newTrip/";
    protected String from_q, to_q, departure_q, return_q, nome_q, program_q, vehicle = "", tag = "";
    protected Double budget_q;
    protected int group_q;
    private RequestQueue mQueue;
    Context contesto;
    //foto
    private ImageView b_upload;
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 71;
    final int PIC_CROP = 2;

    //Firebase
    FirebaseStorage storage;
    StorageReference storageReference;
    private String pathrandom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation_trip);

        mQueue = Volley.newRequestQueue(this);
        contesto = this;

        //TOOLBAR
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

        // file per firebase
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        pathrandom = UUID.randomUUID().toString();
        // campi
        final EditText budget = findViewById(R.id.budget_max_value);
        final EditText group = findViewById(R.id.group_max_value);
        final TextInputEditText from = (TextInputEditText) findViewById(R.id.from_text);
        final TextInputEditText to = (TextInputEditText) findViewById(R.id.to_text);
        final EditText departure_date = (EditText) findViewById(R.id.departure);
        final EditText return_date = (EditText) findViewById(R.id.ret);

        final EditText nome = findViewById(R.id.nametext);
        final EditText program = findViewById(R.id.plantext);

        // Date starts from tomorrow
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, +1);
        final EditTextDatePicker departure = new EditTextDatePicker(contesto, departure_date, calendar);
        final EditTextDatePicker ret = new EditTextDatePicker(contesto, return_date, calendar, departure);
        departure.setOther(ret);

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
                from_q = from.getText().toString().toLowerCase();
                to_q = to.getText().toString().toLowerCase();

                nome_q = nome.getText().toString().toLowerCase();
                program_q = program.getText().toString().toLowerCase();
                departure_q = departure.getSetMonth() + "/" + departure.getSetDay() + "/" + departure.getSetYear();
                return_q = ret.getSetMonth() + "/" + ret.getSetDay() + "/" + ret.getSetYear();


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
                } else if (Integer.parseInt(budget.getText().toString()) < 0 || (Integer.parseInt(budget.getText().toString()) > 500)) {
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

                    budget_q = Double.parseDouble(budget.getText().toString());
                    group_q = Integer.parseInt(group.getText().toString());

                    JSONObject viaggio = new JSONObject();
                    try {
                        viaggio.put("name", nome_q);
                        viaggio.put("description", program_q);
                        viaggio.put("departure", from_q);
                        viaggio.put("destination", to_q);
                        viaggio.put("budget", budget_q);
                        viaggio.put("startDate", departure_q);
                        viaggio.put("endDate", return_q);
                        viaggio.put("vehicle", vehicle);
                        viaggio.put("tag", tag);
                        viaggio.put("maxPartecipant", group_q);
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        if (user != null) viaggio.put("owner", user.getUid());
                        else
                            viaggio.put("owner", "");

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

        /*
       //crop quadrato
        Intent imageDownload = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        imageDownload.setDataAndType(filePath, "image/*");
        imageDownload.putExtra("crop", "true");
        imageDownload.putExtra("aspectX", 1);
        imageDownload.putExtra("aspectY", 1);
        imageDownload.putExtra("outputX", 200);
        imageDownload.putExtra("outputY", 200);
        imageDownload.putExtra("return-data", true);
        startActivityForResult(imageDownload, PICK_IMAGE_REQUEST);*/

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            // codice di crop
           /* Bundle extras = data.getExtras();
            Bitmap image = extras.getParcelable("data");
            b_upload.setImageBitmap(image);*/

            filePath = data.getData();

            //codice per mostrare l'anteprima
                /*Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                b_upload.setImageBitmap(bitmap);*/
            GlideApp.with(contesto).load(filePath).into(b_upload);

            // codice per mostrare il path
            //TextView path = findViewById(R.id.photo_text);
            //path.setText(filePath.getLastPathSegment());

        }
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
                            Toast.makeText(contesto, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
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
        } else {
            //foto non caricata
            try {
                //Log.i("Dato",uri.toString());
                viaggio.put("image", "");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            jsonParse(viaggio);
        }
    }


    private void jsonParse(JSONObject viaggio) {
        final JsonObjectRequest JORequest = new JsonObjectRequest(Request.Method.POST, URL, viaggio, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    //da controllare l'update
                    String status = response.getString("status");
                    if (status.equals("success")) {
                        Toast.makeText(contesto, "Aggiunto correttamente", Toast.LENGTH_SHORT).show();
                    } else {
                        String err = response.getString("type");
                        new ErrorServer(contesto).handleError(err);
                        deleteTrip();
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

    private void msgErrore(String datoMancante) {
        Toast.makeText(contesto, "Inserisci " + datoMancante, Toast.LENGTH_SHORT).show();
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
            case R.id.tag4:
                if (checked)
                    tag = "tecnologia";
                break;
        }
    }

    private void deleteTrip() {
        StorageReference storageRef = storageReference.child("tripImage/" + pathrandom);
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
}

