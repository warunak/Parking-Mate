package com.example.warunakavinda.parkingmate;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class availableplaces extends AppCompatActivity {

    TextView txteml;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acplaces);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

public void sliit(View view){

    Intent i = new Intent(this, sliit.class);
    startActivity(i);
}

    public void mc(View view){

        Intent i = new Intent(this, other.class);
        startActivity(i);
    }


    public void lb(View view){

        Intent i = new Intent(this, other.class);
        startActivity(i);
    }

    public void sov(View view){

        Intent i = new Intent(this, other.class);
        startActivity(i);
    }

    public void home(View view){

        Intent i = new Intent(this, AcmainActivity.class);
        startActivity(i);
    }

    public void mp(View view){

        Intent i = new Intent(this, testp.class);
        startActivity(i);
    }

}
