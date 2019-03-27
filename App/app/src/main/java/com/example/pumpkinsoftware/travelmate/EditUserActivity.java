package com.example.pumpkinsoftware.travelmate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.pumpkinsoftware.travelmate.client_server_interaction.PostUser;
import com.example.pumpkinsoftware.travelmate.client_server_interaction.ServerCallback;
import com.example.pumpkinsoftware.travelmate.glide.GlideApp;
import com.example.pumpkinsoftware.travelmate.handle_error.ErrorServer;
import com.example.pumpkinsoftware.travelmate.user.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
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
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditUserActivity extends AppCompatActivity {
    public final static String EXTRA_USER = "travelmate_extra_eua_USER";
    private final static String URL = "https://debugtm.herokuapp.com/user/updateUser";
    private Context context;
    private User user;
    private String mail;
    private String gender;
    private String relationship;
    private EditText biot;
    private CircleImageView profile;
    private ImageView cover;
    private Uri filePath1, filePath2;
    private final int PICK_IMAGE_REQUEST = 71;
    private int FOTO = 0;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private ProgressBar progressBar;
    private static String status = "";
    //  private int upload = 0;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_register);

        context = (Context) this;
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        mAuth = FirebaseAuth.getInstance();


        Bundle b = getIntent().getExtras();
        user = (User) b.getSerializable(EXTRA_USER);

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
        //toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        toolbar.setBackgroundResource(R.color.colorPrimary);

        final TextView title = findViewById(R.id.title);
        title.setText("Modifica profilo");

        progressBar = findViewById(R.id.indeterminateBar); // GIA' AGGIUNTA NELL'XML
        // PROCEDURA
        // 1. CARICO IMMAGINE
        // 2. progressBar.setVisibility(View.VISIBLE);
        // 3. FINE CARICAMENTO
        // 4. progressBar.setVisibility(View.GONE);
        // NON USARE LA PROGRESS DIALOG CHE BLOCCA L'INTERATTIVITA'

        // Values not editables
        final EditText name = (EditText) findViewById(R.id.name_r);
        name.setText(user.getName());
        name.setEnabled(false);

        final EditText surname = (EditText) findViewById(R.id.surname_r);
        surname.setText(user.getSurname());
        surname.setEnabled(false);

        final EditText data = (EditText) findViewById(R.id.age2_r);
        data.setText(inverseDate(user.getBirthday()));
        data.setEnabled(false);

        RadioButton radioButtonMan = findViewById(R.id.uomo);
        radioButtonMan.setEnabled(false);

        RadioButton radioButton = findViewById(R.id.donna);
        radioButton.setEnabled(false);

        gender = user.getGender();
        if (gender.equals("Uomo")) radioButtonMan.setChecked(true);
        else radioButton.setChecked(true);

        TextView terms = findViewById(R.id.terms);
        terms.setVisibility(View.GONE);

        // Values editables
        biot = (EditText) findViewById(R.id.bio_r);
        biot.setText(user.getDescr());

        final TextView mailview = (TextView) findViewById(R.id.email2_r);
        mail = user.getEmail();
        mailview.setText(mail);
        mailview.setEnabled(false); // TEMPORANEO

        String rel = user.getRelationship();
        if (rel.equals("Single")) radioButton = findViewById(R.id.single);
        else radioButton = findViewById(R.id.fidanzato);
        radioButton.setChecked(true);
        relationship = user.getRelationship();

        // Photos
        profile = (CircleImageView) findViewById(R.id.profile_r);
        String img = user.getPhotoProfile();

        if (!img.isEmpty())
            GlideApp.with(context)
                    .load(img)
                    .into(profile);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FOTO = 1;
                chooseImage();
            }

        });

        cover = (ImageView) findViewById(R.id.header_cover_image_r);
        img = user.getCover();

        if (!img.isEmpty())
            GlideApp.with(context)
                    .load(img)
                    .into(cover);

        cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FOTO = 2;
                chooseImage();
            }
        });

       /* ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.topMargin = 28;
        params.endToEnd = ;
        params.startToStart = ;
        params.horizontalBias = (float) 0.5;
        params.bottomToBottom = ;*/

        Button confirm = (Button) findViewById(R.id.buttonRegister);
        //confirm.setLayoutParams(params);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });

    }

    private String inverseDate(String s) {
        String data[] = s.split("-");
        return data[2].substring(0, 2) + "/" + data[1] + "/" + data[0];
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
                GlideApp.with(context).load(filePath1).into(profile);
                // codice per mostrare il path
                //TextView path = findViewById(R.id.photo_text);
                //path.setText(filePath.getLastPathSegment());

            } else {
                filePath2 = data.getData();

                GlideApp.with(context).load(filePath2).into(cover);

            }
        }
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.single:
                if (checked)
                    relationship = "Single";
                break;
            default:
                if (checked)
                    relationship = "Fidanzato";
                break;
        }
    }

    private String bio;

    public void update() {
        bio = String.valueOf(biot.getText());
        bio = bio.substring(0, 1).toUpperCase() + bio.substring(1).toLowerCase();
        JSONObject utente = new JSONObject();
        try {
            utente.put("uid", user.getUid());

            // Check if values have been modified
            if(!bio.equals(user.getDescr())) {
                utente.put("description", bio);
                user.setDescr(bio);
            }

            if (!relationship.equals("Single")) {
                if (gender.equals("Uomo")) relationship = "Fidanzato";
                else relationship = "Fidanzata";
            }

            if(!relationship.equals(user.getRelationship())) {
                utente.put("relationship", relationship);
                user.setRelationship(relationship);
            }

            //utente.put("email", mail);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        uploadImage1(utente);
        Intent intent = new Intent();

        if(filePath2 != null)
            user.setCover(filePath2.toString());

        intent.putExtra(ProfileFragment.EXTRA_USER, user);
        setResult(RESULT_OK, intent);
        finish();

       /* new PostUser(context).jsonParse(utente, PostUser.flag.UPDATE, new ServerCallback() {
            @Override
            public void onSuccess(JSONObject response) {
               if (getStatus().equals("ERROR")){
                   deleteImg(storageReference.child("userImage/"+mail+"/avatar"));
                   deleteImg(storageReference.child("userImage/"+mail+"/cover"));
               }
                else {
                    if(!profile.toString().isEmpty()){
                        deleteImg(storageReference.child("userImage/"+mail+"/"+profile.toString()));
                    }
                    if(!cover.toString().isEmpty()){
                        deleteImg(storageReference.child("userImage/"+mail+"/"+cover.toString()));
                    }
                    Intent intent = new Intent();
                    user.setDescr(bio);
                    user.setRelationship(relationship);
                    // TODO set all editable values
                    intent.putExtra(ProfileFragment.EXTRA_USER, user);
                    setResult(RESULT_OK, intent);
                    finish();
               }
            }
        });*/


    }

    private void uploadImage1(final JSONObject utente) {
        if (filePath1 != null) {
            user.setPhotoProfile(filePath1.toString());
            final StorageReference ref = storageReference.child("userImage/" + mail + "/" + UUID.randomUUID().toString());
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
                                    //  upload++;
                                    uploadImage2(utente);
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //progressDialog.dismiss();
                            try {
                                utente.put("avatar", "");
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }
                            uploadImage2(utente);
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
            uploadImage2(utente);
    }

    private void uploadImage2(final JSONObject utente) {
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (filePath2 != null) {
            final StorageReference ref2 = storageReference.child("userImage/" + mail + "/" + UUID.randomUUID().toString());
            ref2.putFile(filePath2)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ref2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    try {
                                        //Log.i("Dato",uri.toString());
                                        utente.put("cover", (uri.toString()));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    //  upload++;
                                    firebaseUser.getIdToken(true)
                                            .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                                                public void onComplete(@NonNull Task<GetTokenResult> task) {
                                                    if (task.isSuccessful()) {
                                                        String idToken = task.getResult().getToken();
                                                        // Send token to your backend via HTTPS
                                                        jsonParse(utente, idToken);
                                                        // ...
                                                    } else {
                                                        // Handle error -> task.getException();
                                                        Toast.makeText(context, "Riprova", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //progressDialog.dismiss();
                            try {
                                utente.put("cover", "");
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }

                            firebaseUser.getIdToken(true)
                                    .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                                        public void onComplete(@NonNull Task<GetTokenResult> task) {
                                            if (task.isSuccessful()) {
                                                String idToken = task.getResult().getToken();
                                                // Send token to your backend via HTTPS
                                                jsonParse(utente, idToken);
                                                // ...
                                            } else {
                                                // Handle error -> task.getException();
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
            firebaseUser.getIdToken(true)
                    .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                        public void onComplete(@NonNull Task<GetTokenResult> task) {
                            if (task.isSuccessful()) {
                                String idToken = task.getResult().getToken();
                                // Send token to your backend via HTTPS
                                jsonParse(utente, idToken);
                                // ...
                            } else {
                                // Handle error -> task.getException();
                                Toast.makeText(context, "Riprova", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
    }

    /*
        private void uploadImage(final JSONObject utente) {
            if (filePath1 != null) {
                final StorageReference ref = storageReference.child("userImage/" + mail + "/" + pathRandom1);
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
                                      //  upload++;
                                        jsonParse(utente);
                                    }
                                });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                //progressDialog.dismiss();
                                try {
                                    utente.put("avatar", "");
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
            if (filePath2 != null) {
                final StorageReference ref2 = storageReference.child("userImage/" + mail + "/" + pathRandom2);
                ref2.putFile(filePath2)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                ref2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        try {
                                            //Log.i("Dato",uri.toString());
                                            utente.put("cover", (uri.toString()));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                      //  upload++;
                                        jsonParse(utente);
                                    }
                                });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                //progressDialog.dismiss();
                                try {
                                    utente.put("cover", "");
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
            if (filePath1 == null && filePath2 == null) {
                jsonParse(utente);
            }


        }
    */
    private void jsonParse(final JSONObject utente, final String idToken) {
        RequestQueue mQueue = Volley.newRequestQueue(this);
        final JsonObjectRequest JORequest = new JsonObjectRequest(Request.Method.POST, URL, utente, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String status = response.getString("status");
                    if (status.equals("success")) {
                        Toast.makeText(context, "Modifica completata", Toast.LENGTH_SHORT).show();
                        //nel caso che venga chiamata due volte non trova le foto da firebase ma l'app funziona lo stesso
                        //  if (upload < 2) {
                        if (filePath1 != null && !user.getPhotoProfile().equals("")) {
                            deleteImg(storage.getReferenceFromUrl(user.getPhotoProfile()));
                        }
                        if (filePath2 != null && !user.getCover().equals("")) {
                            deleteImg(storage.getReferenceFromUrl(user.getCover()));
                        }
                        updateUserForChat(utente);
                        //     }

                    } else {
                        String err = response.getString("type");
                        new ErrorServer(context).handleError(err);
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


    public String getStatus() {
        return status;
    }

    public static void setStatus(String s) {
        status = s;
    }

    private void updateUserForChat(JSONObject utente) {
        String foto = null;
        try {
            foto = utente.getString("avatar");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (foto != null) {
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(user.getName() + " " + user.getSurname()) //QUI GLI PASSI IL NOME E COGNOME
                    .setPhotoUri(Uri.parse(foto)

                            //"userImage/"+mail+"/"+
                    ) //QUI IL LINK DELL'AVATAR
                    .build();

            mAuth.getCurrentUser().updateProfile(profileUpdates)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                //Log.d(TAG, "User profile updated.");
                            }
                        }
                    });

        }
    }

}
