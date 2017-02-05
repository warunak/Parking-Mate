package com.example.warunakavinda.parkingmate;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;






public class inquries extends AppCompatActivity
{

   String data;
    //String email;
    String msg;

    protected Button mInq;
    protected TextView mInqt;
    TextView txteml;




    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acinquries);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        mInq = (Button)findViewById(R.id.btninq);

        mInqt =(TextView)findViewById(R.id.txtinq);





        mInq.setOnClickListener(new View.OnClickListener()
        {

            @Override

            public void onClick(View view) {

                msg = mInqt.getText().toString().trim();

               /* msg = mInqt.getText().toString().trim();

                new MyTask().execute();*/
                if(msg.isEmpty())
                {
                    mInqt.setError("Please enter the inquiry here");
                }

                else
                {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                //Yes button clicked

                               // msg = mInqt.getText().toString().trim();

                                new MyTask().execute();


                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked



                                break;


                            case DialogInterface.BUTTON_NEUTRAL:
                                //No button clicked

                                Intent i = new Intent(getApplicationContext(),AcmainActivity.class);
                                startActivity(i);



                                break;


                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage("Do you want to send this Inquiry or Idea").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).setNeutralButton("Home", dialogClickListener).show();


            }



            }


        });








    }





    private class MyTask extends AsyncTask<Void, Void, Void> {

        Connection conn = null;
        ResultSet rs;
        TextView tv=(TextView)findViewById(R.id.textView7);




        @Override
        protected Void doInBackground(Void... params) {





         try {
                conn= db.myconn();
                Statement s=conn.createStatement();

                s.executeUpdate("INSERT INTO inquries ( msg ) VALUES( '"+msg+"' )");



            } catch (ClassNotFoundException e) {
                data=e.getMessage();


            } catch (SQLException e) {
                data=e.getMessage();

                //data="hello";
            }

            finally {
                db.con_close(conn);
            }


            return null;

        }
        @Override
        protected void onPostExecute(Void result) {
            // tv.setText(String.valueOf(data));

            Toast.makeText(inquries.this, "**Thank You for your feedback**", Toast.LENGTH_LONG).show();


            Intent i = new Intent(getApplicationContext(), AcmainActivity.class);
            startActivityForResult(i, 100);



        }

        @Override
        protected void onPreExecute() {
          //  Toast.makeText(register.this, "**Please wait...Creating**", Toast.LENGTH_LONG).show();
            //tv.setText("Please wait... Connecting");
        }
        @Override
        protected void onProgressUpdate(Void... values) {
          //  tv.setText(String.valueOf(data));


        }





    }

}
