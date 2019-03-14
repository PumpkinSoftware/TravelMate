package com.example.pumpkinsoftware.travelmate;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class AccountRegisterActivity extends AppCompatActivity {
    private String mail,pass,profile_pic="",cover_pic="",name,surname,age,sex,relationship;
    private CircleImageView profile;
    private ImageView cover;
    private Button confirm;
    private Uri filePath1,filePath2;
    private final int PICK_IMAGE_REQUEST = 71;
    private int FOTO=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_register);
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

        Intent intent = getIntent();
        mail= intent.getExtras().getString("mail");
        pass=intent.getExtras().getString("pass");

        final TextView mailview=(TextView) findViewById(R.id.email2_r);
        mailview.setText(mail);

        profile=(CircleImageView) findViewById(R.id.profile_r);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FOTO=1;
                chooseImage();
            }

        });
        cover=(ImageView) findViewById(R.id.header_cover_image_r);
        cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FOTO=2;
                chooseImage();
            }
        });

        confirm=(Button) findViewById(R.id.buttonRegister);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRegistration();
            }
        });

    }

    private void sendRegistration() {
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
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            // codice di crop
           /* Bundle extras = data.getExtras();
            Bitmap image = extras.getParcelable("data");
            b_upload.setImageBitmap(image);*/
           if(FOTO==1){
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
        }else {
               filePath2 = data.getData();
               try {
                   //codice per mostrare l'anteprima
                   Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath2);
                   cover.setImageBitmap(bitmap);
                   // codice per mostrare il path
                   //TextView path = findViewById(R.id.photo_text);
                   //path.setText(filePath.getLastPathSegment());
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
                    sex = "uomo";
                break;
            case R.id.donna:
                if (checked)
                    sex = "donna";
                break;
            case R.id.single:
                if (checked)
                    relationship = "single";
                break;
            case R.id.fidanzato:
                if (checked)
                    relationship = "fidanzato";
                break;

        }
    }
}
