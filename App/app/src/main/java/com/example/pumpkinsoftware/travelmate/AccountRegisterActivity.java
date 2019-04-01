package com.example.pumpkinsoftware.travelmate;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pumpkinsoftware.travelmate.client_server_interaction.PostJoin;
import com.example.pumpkinsoftware.travelmate.client_server_interaction.PostUser;
import com.example.pumpkinsoftware.travelmate.client_server_interaction.ServerCallback;
import com.example.pumpkinsoftware.travelmate.date_picker.BirthdayPicker;
import com.example.pumpkinsoftware.travelmate.glide.GlideApp;
import com.gc.materialdesign.widgets.ProgressDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.auth.UserProfileChangeRequest;
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
    private String mail, pass, name, surname, bio, birthday, sex = "", relationship = "";
    private EditText namet, surnamet, biot;
    private CircleImageView profile;
    private ImageView cover;
    private Button confirm;
    private Uri filePath1, filePath2;
    private final int PICK_IMAGE_REQUEST = 71;
    private int FOTO = 0;
    private BirthdayPicker nascita;
    private static Calendar calendar;
    private String avatar = "";

    public static String status = "";
    private ProgressBar progressBar;

    private Context contesto;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    private boolean confirmFlag=false;

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
                if (!confirmFlag) {
                    onBackPressed();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    finish();
                }
            }
        });
        //valori
        Intent intent = getIntent();
        mail = intent.getExtras().getString("mail");
        pass = intent.getExtras().getString("pass");
        // file per firebase

        mAuth = FirebaseAuth.getInstance();

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            progressBar = findViewById(R.id.indeterminateBar);
        else
            progressBar = findViewById(R.id.indeterminateBarForOldSdk);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        final EditText mailView = findViewById(R.id.email2_r);
        mailView.setText(mail);

        namet = (EditText) findViewById(R.id.name_r);
        surnamet = (EditText) findViewById(R.id.surname_r);
        biot = (EditText) findViewById(R.id.bio_r);

        calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -18);
        final EditText data = (EditText) findViewById(R.id.age2_r);

        nascita = new BirthdayPicker(contesto, data, calendar);

        //photos
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

        TextView terms = findViewById(R.id.terms);
        terms.setMovementMethod(LinkMovementMethod.getInstance());

        //conferma
        confirm = (Button) findViewById(R.id.buttonRegister);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmFlag=true;
                mail = mailView.getText().toString();
                PreparationAccount();
            }
        });
    }

    private void PreparationAccount() {
        name = String.valueOf(namet.getText());
        surname = String.valueOf(surnamet.getText());
        bio = String.valueOf(biot.getText());
        birthday = nascita.getSetMonth() + "/" + nascita.getSetDay() + "/" + nascita.getSetYear();
        boolean passed = true;

        if (name.isEmpty()) {
            msgErrore("nome");
            passed = false;
        } else if (surname.isEmpty()) {
            msgErrore("cognome");
            passed = false;
        } else if (bio.isEmpty()) {
            msgErrore("una breve biografia");
            passed = false;
        } else if (birthday.equals("-1/-1/-1")) {
            msgErrore("la data di nascita");
            passed = false;
        } else if (sex.isEmpty()) {
            msgErrore("il sesso");
            passed = false;
        } else if (relationship.isEmpty()) {
            msgErrore("la relazione sentimentale");
            passed = false;
        }

        if(passed) {
            progressBar.setVisibility(View.VISIBLE);
            confirm.setEnabled(false);

            mAuth.createUserWithEmailAndPassword(mail, pass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                user = mAuth.getCurrentUser();
                                sendRegistration();
                            } else {
                                try {
                                    progressBar.setVisibility(View.GONE);
                                    confirmFlag = false;
                                    confirm.setEnabled(true);
                                    throw task.getException();
                                }
                                // if user enters wrong password.
                            /*catch (FirebaseAuthWeakPasswordException weakPassword)
                            {
                                Log.d(TAG, "onComplete: weak_password");
                            }
                            // if user enters wrong email.
                            catch (FirebaseAuthInvalidCredentialsException malformedEmail)
                            {
                                Log.d(TAG, "onComplete: malformed_email");
                            }*/ catch (FirebaseAuthUserCollisionException existEmail) {
                                    Toast.makeText(contesto, "Email già in uso", Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                    confirmFlag = false;
                                } catch (Exception e) {
                                    Toast.makeText(contesto, "Si è verificato un problema, riprovare", Toast.LENGTH_SHORT).show();
                                    confirmFlag = false;
                                    progressBar.setVisibility(View.GONE);
                                }
                            }
                        }
                    });
        }

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

                //codice per mostrare l'anteprima

                GlideApp.with(contesto).load(filePath1).into(profile);
                // codice per mostrare il path
                //TextView path = findViewById(R.id.photo_text);
                //path.setText(filePath.getLastPathSegment());

            } else {
                filePath2 = data.getData();
                GlideApp.with(contesto).load(filePath2).into(cover);
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
        confirmFlag = false;
        confirm.setEnabled(true);
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

    private void sendRegistration() {
        JSONObject utente = new JSONObject();
        try {
            utente.put("name", processingUpperLowerString(name));
            utente.put("surname", processingUpperLowerString(surname));
            utente.put("description", bio.substring(0, 1).toUpperCase() + bio.substring(1).toLowerCase());
            utente.put("birthday", birthday);
            utente.put("gender", sex);
            if (!relationship.equals("Single")) {
                if (sex.equals("Uomo"))
                    relationship = "Fidanzato";
                else
                    relationship = "Fidanzata";
            }
            utente.put("relationship", relationship);
            utente.put("email", mail);
        } catch (JSONException e) {
            progressBar.setVisibility(View.GONE);
            confirmFlag = false;
            confirm.setEnabled(true);
            e.printStackTrace();
        }

        uploadImage1(utente);
    }


    private void sendEmail() {
        if (user != null) {
            user.sendEmailVerification()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            task.isSuccessful();
                        }
                    });
        }
    }

    private void uploadImage1(final JSONObject utente) {
        if (filePath1 != null) {
            byte[] image_compressed = Utils.compressImage(filePath1.toString(), profile); //cover);
            final StorageReference ref = storageReference.child("userImage/" + mail+"/"+UUID.randomUUID().toString());
            ref.putBytes(image_compressed)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    try {
                                        //Log.i("Dato",uri.toString());
                                        utente.put("avatar", (uri.toString()));
                                        avatar = uri.toString();
                                    } catch (JSONException e) {
                                        progressBar.setVisibility(View.GONE);
                                        e.printStackTrace();
                                        confirmFlag = false;
                                        confirm.setEnabled(true);
                                    }
                                    uploadImage2(utente);
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            try {
                                //Log.i("Dato",uri.toString());
                                utente.put("avatar", "");
                                avatar = "";
                            } catch (JSONException ex) {
                                progressBar.setVisibility(View.GONE);
                                ex.printStackTrace();
                                confirmFlag = false;
                                confirm.setEnabled(true);
                            }
                            uploadImage2(utente);
                        }
                    });
        } else {
            try {
                utente.put("avatar", "");
                avatar = "";
            } catch (JSONException e) {
                progressBar.setVisibility(View.GONE);
                e.printStackTrace();
                confirmFlag = false;
                confirm.setEnabled(true);
            }
            uploadImage2(utente);
        }
    }

    private void uploadImage2(final JSONObject utente) {
        if (filePath2 != null) {
            byte[] image_compressed = Utils.compressImage(filePath2.toString(), cover); //profile);
            final StorageReference ref = storageReference.child("userImage/" + mail + "/"+UUID.randomUUID().toString());
            ref.putBytes(image_compressed)
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
                                        progressBar.setVisibility(View.GONE);
                                        e.printStackTrace();
                                        confirmFlag = false;
                                        confirm.setEnabled(true);
                                    }
                                    sendPostServer(utente);
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //progressDialog.dismiss();
                            try {
                                //Log.i("Dato",uri.toString());
                                utente.put("cover", "");
                            } catch (JSONException ex) {
                                progressBar.setVisibility(View.GONE);
                                ex.printStackTrace();
                                confirmFlag = false;
                                confirm.setEnabled(true);
                            }
                            sendPostServer(utente);
                        }
                    });

        } else {
            try {
                utente.put("cover", "");
            } catch (JSONException e) {
                progressBar.setVisibility(View.GONE);
                e.printStackTrace();
                confirmFlag = false;
                confirm.setEnabled(true);
            }
            sendPostServer(utente);
        }
    }

    private void sendPostServer(final JSONObject utente) {
        user.getIdToken(true)
                .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                    public void onComplete(@NonNull Task<GetTokenResult> task) {
                        if (task.isSuccessful()) {
                            String idToken = task.getResult().getToken();
                            // Send token to your backend via HTTPS
                            new PostUser(contesto).jsonParse(utente, PostUser.flag.NEW, idToken, new ServerCallback() {
                                @Override
                                public void onSuccess(JSONObject response) {
                                    if (getStatus().equals("ERROR")) {
                                        deleteUser(utente);
                                        progressBar.setVisibility(View.GONE);
                                        confirmFlag = false;
                                        confirm.setEnabled(true);
                                    }
                                    if (getStatus().equals("OK")) {
                                        updateUserForChat();
                                        // Send intent to finish prev activity
                                        Intent intent = new Intent(RegistrationActivity.FINISH);
                                        sendBroadcast(intent);
                                        sendEmail();
                                        progressBar.setVisibility(View.GONE);
                                        new AlertDialog.Builder(contesto)
                                                .setTitle("Registrazione completata")
                                                .setMessage("Ti è stata mandata una mail per attivare il tuo account")
                                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                                                    public void onClick(DialogInterface dialog, int whichButton) {
                                                        openLogin();

                                                    }
                                                }).show();
                                    }
                                }
                            });

                        } else {
                            // Handle error -> task.getException();
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(contesto, "Riprova", Toast.LENGTH_SHORT).show();
                            confirmFlag = false;
                            confirm.setEnabled(true);
                            deleteUser(utente);
                        }
                    }
                });


    }

    public void openLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void deleteUser(final JSONObject utente) {
        user.delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // Log.d(TAG, "User account deleted.");
                        }
                    }
                });
        if (filePath1 != null) {
            try {
                deleteImg(storage.getReferenceFromUrl(utente.getString("avatar")));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (filePath2 != null) {
            try {
                deleteImg(storage.getReferenceFromUrl(utente.getString("cover")));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Toast.makeText(contesto, "Registrazione fallita, riprovare", Toast.LENGTH_SHORT).show();
        //finish();
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


    private void updateUserForChat() {
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(processingUpperLowerString(name) + " " + processingUpperLowerString(surname)) //QUI GLI PASSI IL NOME E COGNOME
                .setPhotoUri(Uri.parse(avatar)) //QUI IL LINK DELL'AVATAR
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            //Log.d(TAG, "User profile updated.");
                        }
                    }
                });
    }


    public String getStatus() {
        return status;
    }

    public static void setStatus(String s) {
        status = s;
    }

    @Override
    public void onBackPressed() {
        if (!confirmFlag) {
            super.onBackPressed();
        }
    }

}
