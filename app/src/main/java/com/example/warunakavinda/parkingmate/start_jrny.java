package com.example.warunakavinda.parkingmate;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class start_jrny extends AppCompatActivity {

    String lat,lng,ava,sd;
    public Button StrartJrny;
    CountDownTimer waitTimer;



    TextView latx;
    TextView lngx;
    TextView avax;
    TextView sidx;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acstart_jrny);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);





        StrartJrny = (Button) findViewById(R.id.btnsj);
        latx=(TextView)findViewById(R.id.txtlat);
        lngx=(TextView)findViewById(R.id.txtlng);
        avax=(TextView)findViewById(R.id.txtava);
        sidx=(TextView)findViewById(R.id.txtsid);



        sidx.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (!s.equals("")) { //do your work here }


                }

            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {



            }

            public void afterTextChanged(Editable s) {
                Intent intent = new Intent(getApplicationContext(), reroute.class);
                intent.putExtra("lat", latx.getText());
                intent.putExtra("lng", lngx.getText());
                intent.putExtra("ava", avax.getText());
                startActivity(intent);

            }
        });



       StrartJrny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//
//                waitTimer = new CountDownTimer(999999999, 3000) {
//
//                    public void onTick(long millisUntilFinished) {
//                        new MyTask().execute();
//                    }
//
//                    public void onFinish() {
//                        //After 60000 milliseconds (60 sec) finish current
//                        //if you would like to execute something when time finishes
//                    }
//                }.start();


               new MyTask().execute();


            }
        });



    }

    public void bb(View view)
    {
        Intent i =new Intent(this,availableplaces.class);
        startActivity(i);
    }

    public class MyTask extends AsyncTask<Void, Void, Void>
    {

        Connection conn = null;
        ResultSet rs;


        @Override
        protected Void doInBackground(Void... params)
        {
            try
            {
                conn = db.myconn();
                Statement s  = conn.createStatement();

                rs = s.executeQuery("SELECT latitude,longitude FROM parks where parkname='SLIIT Computing'");


                while (rs.next()) {
                    lat = rs.getString("latitude");
                    lng = rs.getString("longitude");
                }

            }
            catch (ClassNotFoundException e)
            {
            }
            catch (SQLException e)
            {
            }
            finally
            {
                db.con_close(conn);
            }



            try
            {
                conn = db.myconn();
                Statement s  = conn.createStatement();
                rs = s.executeQuery("SELECT count(availability) as avacount FROM slots where availability = 1 and parkId = 1");

                while (rs.next())
                {
                    ava = rs.getString("avacount");
                }

               // publishProgress();

            }
            catch (ClassNotFoundException e)
            {
            }
            catch (SQLException e)
            {
            }
            finally
            {
                db.con_close(conn);
            }





            try
            {
                conn = db.myconn();
                Statement s  = conn.createStatement();
                rs = s.executeQuery("SELECT count(slotId) as scount FROM slots where parkId = 1");

                while (rs.next())
                {
                    sd = rs.getString("scount");
                }




            }
            catch (ClassNotFoundException e)
            {
            }
            catch (SQLException e)
            {
            }
            finally
            {
                db.con_close(conn);
            }



            return null;

        }


        @Override
        protected void onPostExecute(Void result)
        {


            latx.setText(lat.toString());
            lngx.setText(lng.toString());
            avax.setText(ava.toString());
            sidx.setText(sd.toString());


        }

        @Override
        protected void onPreExecute()
        {
            // Toast.makeText(testp.this, "**Please wait...Creating**", Toast.LENGTH_LONG).show();
            //tv.setText("Please wait... Connecting");
        }

        @Override
        protected void onProgressUpdate(Void... values)
        {
            // s1.setText(String.valueOf(data1));




//            waitTimer = new CountDownTimer(999999999, 3000) {
//
//                    public void onTick(long millisUntilFinished) {
//                        new MyTask().execute();
//                    }
//
//                    public void onFinish() {
//                        //After 60000 milliseconds (60 sec) finish current
//                        //if you would like to execute something when time finishes
//                    }
//                }.start();






        }
    }

}
