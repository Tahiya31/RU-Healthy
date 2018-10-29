package com.example.softwareengineering;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by HIMABINDU on 12/12/2017.
 */

public class SelectPhysician extends AppCompatActivity {


    ListView listView;
    ArrayAdapter<String> adapter;
    DatabaseReference databaseReference, dbref1, dbref3;
    FirebaseAuth auth;
    FirebaseUser user;
    public ArrayList<String> nameList = new ArrayList<>();
    private ArrayList<String> idList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_physician);

        listView = (ListView) findViewById(R.id.database_list_view);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, nameList);
        listView.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://ruhealthy-69349.firebaseio.com/Physician");
        dbref1 = FirebaseDatabase.getInstance().getReferenceFromUrl("https://ruhealthy-69349.firebaseio.com/Patient" + user.getUid());


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds1 : dataSnapshot.getChildren())
                {
                    final String value = ds1.getKey();
                    idList.add(value);

                    databaseReference.child(value).child("first name").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot2)
                        {

                            final String name = dataSnapshot2.getValue().toString();
                            nameList.add(name);
                            Log.d("name", "onDataChange: " + name);

                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                                {
                                    //String physician_name = ((TextView)view).getText().toString();
                                    Toast.makeText(SelectPhysician.this, "Physician selected.", Toast.LENGTH_SHORT).show();
                                    dbref1.child("physician id").setValue(value);

                                }
                            });

                            adapter.notifyDataSetChanged();

                        }



                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

        /*
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s)
            {

                //final String value = dataSnapshot.getKey();

                for(DataSnapshot ds1: dataSnapshot.getChildren())
                {

                    if(ds1.getKey().toString().equalsIgnoreCase("Name"))
                    {
                        String name = ds1.getValue().toString();
                        nameList.add(name);
                        idList.add(value);


                    ds1.getChildren()


                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                String physician_name = ((TextView)view).getText().toString();
                                Toast.makeText(SelectPhysician.this, "Physician selected" + physician_name, Toast.LENGTH_SHORT).show();
                                //dbref1.child("Physician Details").child(value).setValue(physician_name);

                            }
                        });
                    }
                }

                adapter.notifyDataSetChanged();
            });

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        */


