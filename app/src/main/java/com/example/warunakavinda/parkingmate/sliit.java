package com.example.warunakavinda.parkingmate;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class sliit extends AppCompatActivity {

    int data;
    int data1;
    CountDownTimer waitTimer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acsliit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);





        waitTimer = new CountDownTimer(999999999, 3000) {

            public void onTick(long millisUntilFinished) {
                new MyTask().execute();
            }

            public void onFinish() {
                //After 60000 milliseconds (60 sec) finish current
                //if you would like to execute something when time finishes
            }
        }.start();


    }




    private class MyTask extends AsyncTask<Void, Void, Void> {

        int count = 0;
        int[] slots = new int[1];

        Connection conn = null;
        ResultSet rs;


        TextView[] textViews = new TextView[1];

        TextView s1 = (TextView) findViewById(R.id.ss1);
       // TextView s11 = (TextView) findViewById(R.id.txts2);
        //  TextView s111 = (TextView) findViewById(R.id.txts3);


        @Override
        protected Void doInBackground(Void... params) {

            textViews[0] = s1;
            //textViews[1] = s11;
            //  textViews[2] = s111;


            try {
                conn = db.myconn();
                Statement s = conn.createStatement();
                //Statement ss=conn.createStatement();
                rs = s.executeQuery("select slotId , availability from slots where parkId = 2");


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

            for (int i = 0; i < 1; i++) {

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
