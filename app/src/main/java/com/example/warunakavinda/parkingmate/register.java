package com.example.warunakavinda.parkingmate;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import android.os.AsyncTask;


public class register extends AppCompatActivity {


    protected EditText mFName;
    protected EditText mLName;
    protected EditText mTelno;
    protected EditText mEmail;
    protected EditText mPassword;
    protected EditText mRePassword;
    protected Button mregisbtn;

    String data;

    String fname;
    String lname;
    String email;
    String telno;
    String password;
    String repassword;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acregister);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);








        mFName = (EditText)findViewById(R.id.txtfname);
        mLName = (EditText)findViewById(R.id.txtlname);
        mTelno = (EditText)findViewById(R.id.txttel);
        mEmail = (EditText)findViewById(R.id.txtemail);
        mPassword = (EditText)findViewById(R.id.txtpass);
        mRePassword = (EditText)findViewById(R.id.txtrepass);
        mregisbtn = (Button) findViewById(R.id.btnreg);

       // new MyTask().execute();



        mregisbtn.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {

                fname = mFName.getText().toString().trim();
                lname = mLName.getText().toString().trim();
                email = mEmail.getText().toString().trim();
                telno = mTelno.getText().toString().trim();
                password = mPassword.getText().toString().trim();
                repassword = mRePassword.getText().toString().trim();


                if (fname.isEmpty() | lname.isEmpty() | email.isEmpty() | telno.isEmpty() | password.isEmpty() | repassword.isEmpty()) {
                    Toast.makeText(register.this, "**All fields are required**", Toast.LENGTH_LONG).show();
                }

                else {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                //Yes button clicked

                               if (password.length() < 4)
                               {
                                    mPassword.setError("at least 4 characters");

                                }
                               else {
                                    if (password.equals(repassword))
                                    {
                                        if (email.contains("@") && email.contains(".com"))
                                        {
                                            if (telno.length() >= 10 && telno.length() <= 12 && telno.startsWith("+") | telno.startsWith("0"))
                                            {

                                                new MyTask().execute();

                                            }
                                            else
                                            {
                                                mTelno.setError("Enter a valid Tel-No");
                                            }
                                        }
                                        else
                                        {
                                            mEmail.setError("Enter a valid email");
                                        }

                                    }
                                    else
                                    {
                                        mRePassword.setError("Passwords don't match");
                                    }
                                }

                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                Toast.makeText(register.this,"Cancelled",Toast.LENGTH_LONG).show();
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage("By clicking accept, you are agreeing to all of our terms and conditions").setPositiveButton("Accept", dialogClickListener)
                        .setNegativeButton("Cancel", dialogClickListener).show();

            }


            }



            });


    }



    //Thread to connect to the database every 5ms without freezing the interface of the android device
    private class MyTask extends AsyncTask<Void, Void, Void> {

        Connection conn = null;
        ResultSet rs;
        TextView tv=(TextView)findViewById(R.id.textView7);




        @Override
        protected Void doInBackground(Void... params) {


            try {
                conn = db.myconn();
                Statement s = conn.createStatement();
                rs = s.executeQuery("select* from registration where email = '"+email+"'");
                while (rs.next()) {
                    data = rs.getString("email");
                    //data="hello";
                }


            } catch (ClassNotFoundException e) {
                data = e.getMessage();


            } catch (SQLException e) {
                data = e.getMessage();
                //data="hello";
            } finally {
                db.con_close(conn);
            }



           // else {

            try {
                conn = db.myconn();
                Statement s = conn.createStatement();

                if (email.equals(data))
                {


                }
                    else
                {
                    s.executeUpdate("INSERT INTO registration (fname, lname, telno, email, password) VALUES('" + fname + "','" + lname + "','" + telno + "','" + email + "','" + password + "')");

                }

            } catch (ClassNotFoundException e) {
                data = e.getMessage();


            } catch (SQLException e) {
                data = e.getMessage();


                //data="hello";
            } finally {
                db.con_close(conn);
            }
       // }

            return null;

        }
        @Override
        protected void onPostExecute(Void result) {

            if(email.equals(data))
            {

                mEmail.setError("This account already exists");
                Button log = (Button) findViewById(R.id.logr);

                log.setVisibility(View.VISIBLE);
            }
            else
            {

                Toast.makeText(register.this, "Registration Successful...", Toast.LENGTH_LONG).show();

                mFName.setText(null);
                mLName.setText(null);
                mTelno.setText(null);
                mEmail.setText(null);
                mPassword.setText(null);
                mRePassword.setText(null);

                Intent i = new Intent(getApplicationContext(), AcmainActivity.class);
                startActivityForResult(i, 100);
            }


        }

        @Override
        protected void onPreExecute() {
          //  Toast.makeText(register.this, "**Please wait...Creating**", Toast.LENGTH_LONG).show();
            //tv.setText("Please wait... Connecting");


        }
        @Override
        protected void onProgressUpdate(Void... values) {
            tv.setText(String.valueOf(data));




        }





    }


    public void loginrr(View view){

        Intent i = new Intent(this, start.class);
        startActivity(i);



    }
}




