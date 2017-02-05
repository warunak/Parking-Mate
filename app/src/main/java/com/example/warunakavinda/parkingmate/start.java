package com.example.warunakavinda.parkingmate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class start extends AppCompatActivity {

    public EditText mEmail;
    public EditText mPassword;
    public Button mLogin;
    String data;
    String data1;
    String dat;
    String email;
    String password;

    public static final String PREFS_NAME = "loginfile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acstart);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //  new MyTask().execute();

        //  final globalclass globl=(globalclass)getApplicationContext();

        mEmail = (EditText) findViewById(R.id.txtlemail);
        mPassword = (EditText) findViewById(R.id.txtlpassword);
        mLogin = (Button) findViewById(R.id.btnlog);




        SharedPreferences settings = getSharedPreferences(start.PREFS_NAME, 0);
        //Get "hasLoggedIn" value. If the value doesn't exist yet false is returned
        boolean hasLoggedIn = settings.getBoolean("hasLoggedIn", false);

        if (hasLoggedIn) {
            //Go directly to main activity.

            Intent i = new Intent(getApplicationContext(), AcmainActivity.class);
            startActivityForResult(i, 100);
            start.this.finish();




        }


            mLogin.setOnClickListener(new View.OnClickListener() {

                @Override

                public void onClick(View view) {

                    email = mEmail.getText().toString().trim();
                    password = mPassword.getText().toString().trim();

                    if (email.isEmpty() | password.isEmpty()) {
                        Toast.makeText(start.this, "All fields are required", Toast.LENGTH_LONG).show();
                    } else {
                        if (email.contains("@") && email.contains(".com")) {
                            if (password.length() < 4) {
                                mPassword.setError("Less than 4 characters");
                                Toast.makeText(start.this, "Please check the password", Toast.LENGTH_LONG).show();
                            } else {


                                new MyTask().execute();

                            }
                        } else {
                            mEmail.setError("Enter a valid email");
                            Toast.makeText(start.this, "Please check the email", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            });


    }

    private class MyTask extends AsyncTask<Void, Void, Void> {

        Connection conn = null;
        ResultSet rs;
        TextView tv=(TextView)findViewById(R.id.txt1);
        TextView tv1 =(TextView)findViewById(R.id.txt2);
        EditText le=(EditText)findViewById(R.id.txtlemail);
        String emi = le.getText().toString().trim();
        int count;
        //String[][] login = new String[][];
        @Override
        protected Void doInBackground(Void... params) {

            try {
                conn = db.myconn();
                Statement s = conn.createStatement();
                rs = s.executeQuery("select fname from registration where email = '"+email+"'");
                while (rs.next()) {
                    dat = rs.getString("fname");
                    //data="hello";
                }

            } catch (ClassNotFoundException e) {
                data = e.getMessage();


            } catch (SQLException e) {
                data = e.getMessage(    );
                //data="hello";
            } finally {
                db.con_close(conn);
            }

            try {
                conn= db.myconn();
                Statement s=conn.createStatement();
                rs=s.executeQuery("select email , password from registration where email = '"+emi+"' ");


                while (rs.next())
                {

                    data = rs.getString("email");
                    data1 = rs.getString("password");

                }


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
            //tv.setText(String.valueOf(data));
            //tv1.setText(String.valueOf(data1));

            EditText le=(EditText)findViewById(R.id.txtlemail);
            EditText ps=(EditText)findViewById(R.id.txtlpassword);
            /*tv.getText();
            tv1.getText();*/
        //   String em;
          //  String ps;




            //em ="kavindaraj@gmail.com";
            //ps="asdf";

           String e = le.getText().toString();
           String p = ps.getText().toString();


            if(e.equals(data) && p.equals(data1))
            {

                Toast.makeText(start.this, "** Greetings "+dat+" **", Toast.LENGTH_LONG).show();

                //User has successfully logged in, save this information
                // We need an Editor object to make preference changes.
                SharedPreferences settings = getSharedPreferences(start.PREFS_NAME, 0); // 0 - for private mode
                SharedPreferences.Editor editor = settings.edit();

                //Set "hasLoggedIn" to true
                    editor.putBoolean("hasLoggedIn", true);

                // Commit the edits!
                    editor.commit();


                    Intent i = new Intent(getApplicationContext(), AcmainActivity.class);
                   // i.putExtra("loginAs", data);
                    startActivityForResult(i, 100);





            }
            else
            {

                Toast.makeText(start.this, "**Please Check Your Credentials**", Toast.LENGTH_LONG).show();
            }






        }

        @Override
        protected void onPreExecute() {
          // Toast.makeText(start.this, "**Please wait...Connecting**", Toast.LENGTH_LONG).show();
            //tv.setText("Please wait... Connecting");




        }
        @Override
        protected void onProgressUpdate(Void... values) {
          //  tv.setText(String.valueOf(data));
           // tv1.setText(String.valueOf(data1));

        }


    }





    public void register(final View view) {

        Intent i = new Intent(this, register.class);
        startActivity(i);
    }

   /* public void reddah(final View view) {

        Intent i = new Intent(this, AcmainActivity.class);
        startActivity(i);
    }*/

}
