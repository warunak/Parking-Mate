package com.example.warunakavinda.parkingmate;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.nearby.messages.Messages;

public class AcmainActivity extends AppCompatActivity {

    TextView txteml;
    String ss;

   // public static final String PREFS_NAME = "register";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acmain);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);





          /*  Intent intent = getIntent();
            String logginAs = intent.getStringExtra("loginAs");
            txteml = (TextView) findViewById(R.id.logp);

            txteml.setText(logginAs);
*/


    }

    public void clickplaces(View view) {


        Intent i = new Intent(this, availableplaces.class);
        startActivity(i);

    }

    public void inq(View view) {

        Intent i = new Intent(this, inquries.class);
        startActivity(i);
    }

    public void about(View view) {

        Intent i = new Intent(this, about.class);
        startActivity(i);
    }

//    public void logout(View view) {
//
//        /*Intent i = new Intent(this, start.class);
//        startActivity(i);*/
//
//
//        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                switch (which) {
//                    case DialogInterface.BUTTON_POSITIVE:
//                        //Yes button clicked
//
//
//
//                        Intent i = new Intent(getApplicationContext(), start.class);
//                        startActivity(i);
//
//
//                        //db.con_close(null);
//                        break;
//
//                    case DialogInterface.BUTTON_NEGATIVE:
//                        //No button clicked
//                        break;
//                }
//            }
//        };
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setMessage("Are you sure you want to logout").setPositiveButton("Accept", dialogClickListener)
//                .setNegativeButton("Cancel", dialogClickListener).show();
//
//
//    }

}



