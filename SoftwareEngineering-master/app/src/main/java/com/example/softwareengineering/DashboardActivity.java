package com.example.softwareengineering;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by HIMABINDU on 11/29/2017.
 */

public class DashboardActivity extends AppCompatActivity {
    //String EmailHolder;
    //TextView Email;
    //Button LogOUT,Profile, HRM, Step ;
    ImageButton  HRM;
    ImageButton Profile;
    Button LogOUT;
    ImageButton  Step;
    FirebaseAuth auth;
    ImageButton todo;
    ImageButton rr;
    ImageButton physs;
    DatabaseReference databaseReference;
    FirebaseUser user;
    private boolean  _doubleBackToExitPressedOnce    = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        user = auth.getCurrentUser();

        //Email = (TextView)findViewById(R.id.textView1);
        LogOUT = (Button)findViewById(R.id.button1);
        Profile = (ImageButton)findViewById(R.id.button2);
        HRM = (ImageButton)findViewById(R.id.button3);
        Step = (ImageButton)findViewById(R.id.button4);
        todo = (ImageButton)findViewById(R.id.button5);
        rr=(ImageButton)findViewById(R.id.button6);
        physs=(ImageButton)findViewById(R.id.button7) ;
        // Adding click listener to Log Out button.
        LogOUT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                auth.signOut();//sign out the user
                /*
                user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(DashboardActivity.this, "User account deleted", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                */
                finish(); //close the activity
                Toast.makeText(DashboardActivity.this, "User Logged out", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(DashboardActivity.this, MainActivity.class)); //start the main activity after sign out

            }
        });

        //Adding click listener to Profile button.
        Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Opening new user profile activity using intent on button click.
                Intent intent = new Intent(DashboardActivity.this, ProfileActivity.class);
                //intent.putExtra(UserEmail, EmailHolder);
                startActivity(intent);
            }
        });

        //Adding click listener to HRM button.
        HRM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Opening new user profile activity using intent on button click.
                Intent intent = new Intent(DashboardActivity.this, HeartRateMonitor.class);
                //intent.putExtra(UserEmail, EmailHolder);
                startActivity(intent);
            }
        });

        rr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Opening new user profile activity using intent on button click.
                Intent intent = new Intent(DashboardActivity.this, RecoveryRate.class);
                //intent.putExtra(UserEmail, EmailHolder);
                startActivity(intent);
            }
        });


        //Adding click listener to Step counter button.
        Step.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Opening new user profile activity using intent on button click.
                Intent intent = new Intent(DashboardActivity.this, StepCounter.class);
                //intent.putExtra(UserEmail, EmailHolder);
                startActivity(intent);
            }
        });

        //Adding click listener to Step counter button.
        todo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Opening new user profile activity using intent on button click.
                Intent intent = new Intent(DashboardActivity.this, ToDoMain.class);
                //intent.putExtra(UserEmail, EmailHolder);
                startActivity(intent);
            }
        });

        //Adding click listener to Step counter button.
        physs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Opening new user profile activity using intent on button click.
                Intent intent = new Intent(DashboardActivity.this, SelectPhysician.class);
                //intent.putExtra(UserEmail, EmailHolder);
                startActivity(intent);
            }
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {

        if (_doubleBackToExitPressedOnce) {
            finishAffinity();
        }
        this._doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press again to quit the RU HEALTHY", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                _doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }


}
