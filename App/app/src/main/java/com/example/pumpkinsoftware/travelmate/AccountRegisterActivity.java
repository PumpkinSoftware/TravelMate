package com.example.pumpkinsoftware.travelmate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pumpkinsoftware.travelmate.date_picker.BirthdayPicker;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class AccountRegisterActivity extends AppCompatActivity {
    private String mail, pass, name, surname, bio, age, sex, relationship;
    private final static String URL = "https://debugtm.herokuapp.com/user/newUser";
    private EditText namet, surnamet, biot;
    private CircleImageView profile;
    private ImageView cover;
    private Button confirm;
    private Uri filePath1, filePath2;
    private final int PICK_IMAGE_REQUEST = 71;
    private int FOTO = 0;
    private BirthdayPicker nascita;
    private static Calendar calendar;
    Context contesto;
    FirebaseStorage storage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_register);
        contesto = this;

        //toolbar
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


        //valori
        Intent intent = getIntent();
        mail = intent.getExtras().getString("mail");
        pass = intent.getExtras().getString("pass");

        final TextView mailview = (TextView) findViewById(R.id.email2_r);
        mailview.setText(mail);

        namet = (EditText) findViewById(R.id.name_r);
        surnamet = (EditText) findViewById(R.id.surname_r);
        biot = (EditText) findViewById(R.id.bio_r);

        calendar = Calendar.getInstance();
        final EditText data = (EditText) findViewById(R.id.age2_r);
        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nascita = new BirthdayPicker(contesto, data, calendar);
            }
        });

        //foto
        profile = (CircleImageView) findViewById(R.id.profile_r);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FOTO = 1;
                chooseImage();
            }

        });
        cover = (ImageView) findViewById(R.id.header_cover_image_r);
        cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FOTO = 2;
                chooseImage();
            }
        });


        //conferma
        confirm = (Button) findViewById(R.id.buttonRegister);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRegistration();
                openHome();
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
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            if (FOTO == 1) {
                filePath1 = data.getData();
                try {
                    //codice per mostrare l'anteprima
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath1);
                    profile.setImageBitmap(bitmap);
                    // codice per mostrare il path
                    //TextView path = findViewById(R.id.photo_text);
                    //path.setText(filePath.getLastPathSegment());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                filePath2 = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath2);
                    cover.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.uomo:
                if (checked)
                    sex = "Uomo";
                break;
            case R.id.donna:
                if (checked)
                    sex = "Donna";
                break;
            case R.id.single:
                if (checked)
                    relationship = "Single";
                break;
            case R.id.fidanzato:
                if (checked)
                    relationship = "Fidanzato";
                break;

        }
    }

    private void msgErrore(String datoMancante) {
        Toast.makeText(contesto, "Inserisci " + datoMancante, Toast.LENGTH_SHORT).show();
    }

    public static String processingUpperLowerString(String testo) {
        String Result = "";
        String[] arrayResult = null;
        arrayResult = testo.split(" ");
        for (int i = 0; i < arrayResult.length; i++) {
            Result += arrayResult[i].substring(0, 1).toUpperCase() + arrayResult[i].substring(1, arrayResult[i].length()).toLowerCase() + " ";
        }
        return Result.substring(0, Result.length() - 1);
    }

    private static boolean checkAge(int month, int day, int year) {
        calendar=Calendar.getInstance();
        int today_day = calendar.get(Calendar.DAY_OF_MONTH);
        int today_month = calendar.get(Calendar.MONTH);
        int today_year = calendar.get(Calendar.YEAR);
        return (today_year-year)>18 || (today_year-year==18 && (today_month-month>0 || (today_month-month==0 && today_day-day>=0))) ;
    }


    private void sendRegistration() {
        name = String.valueOf(namet.getText());
        surname = String.valueOf(surnamet.getText());
        bio = String.valueOf(biot.getText());
        age = nascita.getSetMonth() + "/" + nascita.getSetDay() + "/" + nascita.getSetYear();
        if (name.isEmpty()) {
            msgErrore("nome");
        } else if (surname.isEmpty()) {
            msgErrore("cognome");
        } else if (bio.isEmpty()) {
            msgErrore("una breve biografia");
        } else if (!checkAge(nascita.getSetMonth(),nascita.getSetDay(),nascita.getSetYear())) {
            Toast.makeText(contesto, "Devi essere maggiorenne", Toast.LENGTH_SHORT).show();
        } else if (sex.isEmpty()) {
            msgErrore("il sesso");
        } else if (relationship.isEmpty()) {
            msgErrore("la relazione sentimentale");
        } else {

            JSONObject utente = new JSONObject();
            try {
                utente.put("name", processingUpperLowerString(name));
                utente.put("surname", processingUpperLowerString(surname));
                utente.put("description", bio.substring(0, 1).toUpperCase() + bio.substring(1).toLowerCase());
                utente.put("birthday", age);
                utente.put("gender", sex);
                if (!relationship.equals("Single")) {
                    if (sex.equals("Uomo")) utente.put("relationship", "Fidanzato");
                    else utente.put("relationship", "Fidanzata");
                }
                utente.put("email", mail);
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) utente.put("uid", user.getUid());

            } catch (JSONException e) {
                e.printStackTrace();
            }
            uploadImage(utente);
            jsonParse(utente);

        }
    }


    private void uploadImage(final JSONObject utente) {

        if (filePath1 != null) {
            final StorageReference ref = storageReference.child("tripImage/" + UUID.randomUUID().toString());
            ref.putFile(filePath1)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    try {
                                        //Log.i("Dato",uri.toString());
                                        utente.put("avatar", (uri.toString()));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //progressDialog.dismiss();
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
            try {
                utente.put("avatar", "");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (filePath2 != null) {
            final StorageReference ref = storageReference.child("tripImage/" + UUID.randomUUID().toString());
            ref.putFile(filePath1)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    try {
                                        //Log.i("Dato",uri.toString());
                                        utente.put("cover", (uri.toString()));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //progressDialog.dismiss();
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
            try {
                utente.put("cover", "");
            } catch (JSONException e) {
                e.printStackTrace();

            }
        }
    }

    private void jsonParse(JSONObject utente) {

    }



    public void openHome(){
        Intent intent=new Intent(this,LoginActivity.class);
        startActivity(intent);
        finish();
    }

}
