package com.example.pumpkinsoftware.travelmate;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pumpkinsoftware.travelmate.client_server_interaction.PostUser;
import com.example.pumpkinsoftware.travelmate.client_server_interaction.ServerCallback;
import com.example.pumpkinsoftware.travelmate.glide.GlideApp;
import com.example.pumpkinsoftware.travelmate.user.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditUserActivity extends AppCompatActivity {
    public final static String EXTRA_USER = "travelmate_extra_eua_USER";
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

    private static String status = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_register);

        context = (Context) this;
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        Bundle b = getIntent().getExtras();
        user = (User) b.getSerializable(EXTRA_USER);

        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });
        //toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        toolbar.setBackgroundResource(R.color.colorPrimary);

        final TextView title = findViewById(R.id.title);
        title.setText("Modifica profilo");

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

        // Values editables
        biot = (EditText) findViewById(R.id.bio_r);
        biot.setText(user.getDescr());
        final TextView mailview = (TextView) findViewById(R.id.email2_r);
        mail = user.getEmail();
        mailview.setText(mail);

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

        Button confirm = (Button) findViewById(R.id.buttonRegister);
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
            utente.put("uid", "");
            utente.put("description", bio);

            if (!relationship.equals("Single")) {
                if (gender.equals("Uomo")) relationship = "Fidanzato";
                else relationship = "Fidanzata";
            }
            utente.put("relationship", relationship);
            //utente.put("email", mail);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        uploadImage(utente);
        new PostUser(context).jsonParse(utente, PostUser.flag.UPDATE, new ServerCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                if (getStatus().equals("ERROR")) deleteImages();
                else {
                    Intent intent = new Intent();
                    user.setDescr(bio);
                    user.setRelationship(relationship);
                    // TODO set all editable values
                    intent.putExtra(ProfileFragment.EXTRA_USER, user);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });

    }

    private void uploadImage(final JSONObject utente) {
        Log.i("file",filePath1.toString());
        if (filePath1 != null) {
            final StorageReference ref = storageReference.child("userImage/" + mail + "/avatar");
            ref.putFile(filePath1)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    try {
                                        //Log.i("Dato",uri.toString());
                                        Log.i("file",uri.toString());
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
            final StorageReference ref = storageReference.child("userImage/" + mail + "/cover");
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
            //if (!profile.toString().equals(filePath1.toString()) && !profile.toString().isEmpty()) {
                //deleteImage(storage.getReferenceFromUrl(Uri.parse(profile.toString())));
           // }
          //  if (!cover.toString().equals(filePath2.toString()) && !cover.toString().isEmpty()) {
               // deleteImage(storage.getReferenceFromUrl(cover.toString()));
          //  }

        }
    }

    public void deleteImages() {
        /*if(filePath1 != null) {
            StorageReference storageRef = storageReference.child("tripUser/" + mail+"/avatar");
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
        if(filePath2!=null) {
            StorageReference storageRef = storageReference.child("tripUser/" + mail+"/cover");
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
        }*/
        Log.i("filepath1",filePath1.toString());
        if (filePath1 != null) {
            Log.i("storage","userImage/" + mail + "/avatar");
            deleteImage();
        }
        if (filePath2 != null) {
            //deleteImage(storageReference.child("userImage/" + mail + "/cover"));
        }
        Toast.makeText(context, "Modifica del profilo fallita, riprovare", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void deleteImage() {
        StorageReference storageRef =storageReference.child("userImage/" + mail + "/avatar");
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


    public String getStatus() {
        return status;
    }

    public static void setStatus(String s) {
        status = s;
    }
}
