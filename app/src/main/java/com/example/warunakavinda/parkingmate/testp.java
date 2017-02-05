package com.example.warunakavinda.parkingmate;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Timer;
import java.util.TimerTask;

public class testp extends AppCompatActivity {


    int data;
    int data1;
    Timer tim;
    CountDownTimer waitTimer;
    MyTask mt;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actestp);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent i = new Intent(getApplicationContext(), start_jrny.class);
                waitTimer.cancel();
                startActivityForResult(i, 100);
            }
        });

//        new Timer().schedule(new TimerTask() {
//            public void run() {
//                new MyTask().execute();
//            }
//        }, 5, 5);


        waitTimer = new CountDownTimer(999999999, 3000) {

            public void onTick(long millisUntilFinished) {
                new MyTask().execute();
            }

            public void onFinish() {
                //After 60000 milliseconds (60 sec) finish current
                //if you would like to execute something when time finishes
            }
        }.start();



/*

        Timer timer = new Timer();
        timer.schedule(new MyTask(), 0, 5000);
*/



    }


    /*public void refe(View view) {

        new MyTask().execute();
    }*/




    //Thread to connect to the database every 5ms without freezing the interface of the android device
    private class MyTask extends AsyncTask<Void, Void, Void> {

        int count = 0;
        int[] slots = new int[2];

        Connection conn = null;
        ResultSet rs;


        TextView[] textViews = new TextView[2];

        TextView s1 = (TextView) findViewById(R.id.txts1);
        TextView s11 = (TextView) findViewById(R.id.txts2);
      //  TextView s111 = (TextView) findViewById(R.id.txts3);


        @Override
        protected Void doInBackground(Void... params) {

            textViews[0] = s1;
            textViews[1] = s11;
          //  textViews[2] = s111;


            try {
                conn = db.myconn();
                Statement s = conn.createStatement();
                //Statement ss=conn.createStatement();
                rs = s.executeQuery("select slotId , availability from slots where parkId = 1");


                while (rs.next()) {
                    data = Integer.parseInt(rs.getString("slotId"));
                    data1 = Integer.parseInt(rs.getString("availability"));

                    slots[count] = data1;
                    count++;
                }
                publishProgress();

            } catch (ClassNotFoundException e) {
                //data1=e.getMessage();


            } catch (SQLException e) {
                //data1=e.getMessage();
                //data="hello";
            } finally {
                db.con_close(conn);
            }




    return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            /*textViews[0] = s1; */



        }

        @Override
        protected void onPreExecute() {
            // Toast.makeText(testp.this, "**Please wait...Creating**", Toast.LENGTH_LONG).show();
            //tv.setText("Please wait... Connecting");
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            s1.setText(String.valueOf(data1));

            for (int i = 0; i < 2; i++) {

                if (slots[i] == 0) {
                    textViews[i].setText("N");
                    textViews[i].setTextColor(Color.parseColor("#ec2525"));
                } else {
                    textViews[i].setText("A");
                    textViews[i].setTextColor(Color.parseColor("#20da3c"));
                }
            }


        }


    }


}
