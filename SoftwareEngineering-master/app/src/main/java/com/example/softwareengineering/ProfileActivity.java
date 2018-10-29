package com.example.softwareengineering;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by HIMABINDU on 11/29/2017.
 */

public class ProfileActivity extends AppCompatActivity
{
    EditText Age,Gender,Height,Weight,Target,inputName, heartRate ;
    Button Submit;
    String AgeHolder, GenderHolder, HeightHolder, WeightHolder,TargetHolder, NameHolder,EmailHolder, heartRateHolder;
    FirebaseAuth auth;
    FirebaseUser user;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        user= auth.getCurrentUser();

        inputName = (EditText)findViewById(R.id.editName);
        Age = (EditText)findViewById(R.id.editAge);
        Gender = (EditText)findViewById(R.id.editGender);
        Height = (EditText)findViewById(R.id.editHeight);
        Weight = (EditText)findViewById(R.id.editWeight);
        Target = (EditText)findViewById(R.id.editTarget);
        heartRate = (EditText)findViewById(R.id.editHR);
        Submit = (Button)findViewById(R.id.buttonSubmit);

        //sqLiteHelper = new SQLiteHelper(this);

        // Adding click listener to register button.
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                NameHolder = inputName.getText().toString().trim();
                AgeHolder = Age.getText().toString().trim();
                GenderHolder = Gender.getText().toString().trim();
                HeightHolder = Height.getText().toString().trim();
                WeightHolder = Weight.getText().toString().trim();
                TargetHolder = Target.getText().toString().trim();
                heartRateHolder = heartRate.getText().toString().trim();
                EmailHolder = user.getEmail();

                //check any of the edit texts are empty. if so notify the user to enter valid details
                if (TextUtils.isEmpty(NameHolder) || TextUtils.isEmpty(AgeHolder) || TextUtils.isEmpty(EmailHolder) ||
                        TextUtils.isEmpty(GenderHolder) || TextUtils.isEmpty(HeightHolder) || TextUtils.isEmpty(WeightHolder)
                         || TextUtils.isEmpty(TargetHolder) || TextUtils.isEmpty(heartRateHolder))
                {
                    Toast.makeText(getApplicationContext(), "Enter valid details!", Toast.LENGTH_LONG).show();
                    return;
                }

                else
                {
                    String id = databaseReference.push().getKey();
                    UserInformation userInformation= new UserInformation(id, NameHolder, EmailHolder, AgeHolder, GenderHolder, HeightHolder, WeightHolder, TargetHolder, heartRateHolder);
                    databaseReference.child(id).setValue(userInformation);

                    Toast.makeText(ProfileActivity.this, "Profile Created", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ProfileActivity.this, DashboardActivity.class));
                    finish();
                }

            }
        });

    }

}
