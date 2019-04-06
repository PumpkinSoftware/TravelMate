package com.example.pumpkinsoftware.travelmate;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pumpkinsoftware.travelmate.glide.GlideApp;
import com.google.firebase.auth.FirebaseAuth;

public class SettingsFragment extends Fragment {
    private Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_opzioni, container, false);

        context = getContext();

        final TextView bug = inflate.findViewById(R.id.bug);
        bug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Try if an email app in installed
                try {
                    Intent intent = new Intent (Intent.ACTION_VIEW , Uri.parse("mailto:" + getResources().getString(R.string.ourMail)));
                    intent.putExtra(Intent.EXTRA_SUBJECT, "TRAVELMATE: Segnalazione bug");
                    ///intent.putExtra(Intent.EXTRA_TEXT, "your_text");
                    startActivity(intent);
                } catch(Exception e) {
                    Toast.makeText(context, "Nessuna app per email trovata", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

                // Try only with gmail
                /*Intent intent = new Intent (Intent.ACTION_SEND);
                intent.setType("message/rfc822");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{getResources().getString(R.string.ourMail)});
                intent.putExtra(Intent.EXTRA_SUBJECT, "TRAVELMATE: Segnalazione bug");
                intent.setPackage("com.google.android.gm");

                if (intent.resolveActivity(context.getPackageManager()) != null)
                    startActivity(intent);
                else
                    Toast.makeText(context, "Gmail App is not installed", Toast.LENGTH_SHORT).show();*/
            }
        });

        final TextView changePass = inflate.findViewById(R.id.changePass);
        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (context, ChangePassActivity.class);
                context.startActivity(intent);
            }
        });

        final TextView deleteAccount = inflate.findViewById(R.id.deleteAccount);
        deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO delete account
                new AlertDialog.Builder(context)
                        .setTitle("Elimina account")
                        .setMessage("Funzionalità in arrivo")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                            }
                        }).show();
            }
        });

        final TextView infoApp = inflate.findViewById(R.id.infoApp);
        infoApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (context, InfoAppActivity.class);
                context.startActivity(intent);
            }
        });

        /*final WebView view = (WebView) inflate.findViewById(R.id.twitterFeed);
        view.getSettings().setAppCacheEnabled(true);
        view.getSettings().setJavaScriptEnabled(true);
        String text = "<html><body>";
        text += "<a class=\"twitter-timeline\" href=\"https://twitter.com/_TravelMate_?ref_src=twsrc%5Etfw\">Tweets by _TravelMate_</a> <script async src=\"https://platform.twitter.com/widgets.js\" charset=\"utf-8\"></script>\n";
        text += "</body></html>";
        view.loadDataWithBaseURL("https://twitter.com", text, "text/html", "utf-8", "");*/

        ImageView social = inflate.findViewById(R.id.fb);
        social.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://www.facebook.com/PumpkinSoftware/"));
                context.startActivity(i);
            }
        });

        social = inflate.findViewById(R.id.twitter);
        social.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://twitter.com/_TravelMate_"));
                context.startActivity(i);
            }
        });

        final ImageView instagram = inflate.findViewById(R.id.instagram);

        GlideApp.with(context)
                .load(R.mipmap.instagram_logo_2016)
                .into(instagram);

        instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://www.instagram.com/pumpkinsoftware/"));
                context.startActivity(i);
            }
        });

        TextView terms = inflate.findViewById(R.id.terms);
        terms.setMovementMethod(LinkMovementMethod.getInstance());

        final Button b_logout = (Button) inflate.findViewById(R.id.button_logout);
        b_logout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Logout effettuato", Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
                openStart();
                ((Activity)getContext()).finish();
            }
        });

        return inflate;
    }

    public void openStart(){
        Intent intent=new Intent(this.getContext(), StartActivity.class);
        startActivity(intent);
    }
}

