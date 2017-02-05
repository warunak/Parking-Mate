package com.example.warunakavinda.parkingmate;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class func extends AppCompatActivity {

    double lat;
    double lng;
    int ava;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    //Thread to connect to the database every 5ms without freezing the interface of the android device
    public class MyTask extends AsyncTask<Void, Void, Void>
    {

        Connection conn = null;
        ResultSet rs;
        ResultSet rs2;

        @Override
        protected Void doInBackground(Void... params) {

          try {
                conn = db.myconn();
                Statement s  = conn.createStatement();
                Statement s1 = conn.createStatement();
                //Statement ss=conn.createStatement();
                rs = s.executeQuery("SELECT parkId FROM slots WHERE availability=1 group by parkId;");

                while (rs.next())
                {
                    ava= Integer.parseInt(rs.getString("count(availability)"));
                    rs2 = s1.executeQuery("SELECT latitude,longitude FROM parkingmate.parks where parkId ='"+ava+"';");

                    while (rs2.next())
                    {
                        lat = Double.parseDouble(rs2.getString("latitude"));
                        lng = Double.parseDouble(rs2.getString("longitude"));

                    }
                }

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

            Intent i = new Intent(getApplicationContext(), rerout_to_eng.class);
            i.putExtra("lat",lat);
            i.putExtra("lng",lng);
            startActivityForResult(i, 101);
        }

        @Override
        protected void onPreExecute() {
            // Toast.makeText(testp.this, "**Please wait...Creating**", Toast.LENGTH_LONG).show();
            //tv.setText("Please wait... Connecting");
        }

        @Override
        protected void onProgressUpdate(Void... values) {
           // s1.setText(String.valueOf(data1));


        }


    }
}
