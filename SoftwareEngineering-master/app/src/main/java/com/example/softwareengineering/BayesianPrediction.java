package com.example.softwareengineering;

import android.os.Bundle;
import android.renderscript.Sampler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by RAMYA on 12/5/2017.
 */

public class BayesianPrediction extends AppCompatActivity {

    static FirebaseAuth auth;
    static FirebaseUser user;
    static DatabaseReference databaseReference, dr1;
    static ArrayList<String> myList = new ArrayList<>();
    //static ArrayList<String> myListx = new ArrayList<>();
    static final int CONTEXT_IGNORE_SECURITY = 1;
    static double predictedValue = 0;

    TextView tv = (TextView) findViewById(R.id.text1);

   /* final int count = 1; // number of stock values in the file
    static double mean, variance, det, alpha, beta;
    final int n = 8;// number of input from the total size which user wants to use to compute.
    //double[] actualStocks = new double[50];// actual stock values read from the file
    final int[] pastNumber = new int[50];
    final double predictedValue = 0;//stores predicted value
    final double[] phi = new double[3];
    final double[] nphi = new double[3];
    final double[][] X = new double[3][3];
    final double[][] t = new double[3][3];
    final double[][] inv = new double[3][3];
    final double[][] adj = new double[3][3];
    final double[] Y = new double[3];
    final double[] summation = new double[3];
    final int k = 0;*/

    public final void MyHelp(double pred){
        Toast.makeText(this, "Prediction value: " + pred, Toast.LENGTH_LONG).show();
    }






    //static final double finalPredictedValue = Prediction();
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rec);
    }


    static public void Prediction() {

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://ruhealthy-69349.firebaseio.com/Patient/" + user.getUid());
        dr1 = databaseReference.child("Recovery Time");
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Date currentDate = Calendar.getInstance().getTime();


        double actualSum = 0, errorSum = 0; // absolute mean error and average relative error

        //opening a csv file and reading the values from it and storing it in an array.

        //final double[] recoveryArray = {10.2,9.5,10.6,9.6,11.3,10.5,9.3,10.1,10.5,10.9};


        final double[] recoveryArray = new double[10];
        final String[] recoveryArray1 = new String[10];


        final List<Sampler.Value> list = new ArrayList<Sampler.Value>();



        //Query query1 = dr1.orderByKey().limitToLast(10);
        dr1.limitToLast(10).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //final ArrayList<Long> myList = new ArrayList<>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    //Log.d("test", child.getKey() + " - " + child.getValue());
                    //String value = String.valueOf(ds.getValue());
                    //Log.d("text", "onDataChange: " + value);
                    //myList.add(value);
                    //myList.add((Long)ds.getValue());
                    //Log.d("test", "onDataChange: "+ value);
                    //myList.add(value);
                    //Log.d("values", "Prediction: "+ myList.get(0));
                    HashMap<String, Object> map = new HashMap<String, Object>();
                    map.put(ds.getKey(), ds.getValue());

                    if (map != null) {
                        for (String key : map.keySet()) {
                            myList.add(map.get(key).toString());
                        }

                    }

                    //Data data = ds.getValue(Data.class);
                    //myList.add(data.getRate().toString());



                }

                // Log.d("value", "onDataChange: " + myList.get(2));





/*

                //for(int r = 0;r<myList.size();r++)
                //{
                //Log.d("values", "Prediction: "+ myList.get(2));
                //}


                /*
                GenericTypeIndicator<Map<String, Double>> maptype = new GenericTypeIndicator<Map<String, Double>>(){};
                Map<String, Double> td = new HashMap<String, Double>();
                td = dataSnapshot.child("Recovery time").getValue(maptype);
                //List<String> values = new ArrayList<>(td.values());
                //values.toArray(recoveryArray1);
                //Log.d("values", values.toArray(actualStocks1));

                for(Double rates: td.values())
                {
                    Log.d("values", "onDataChange: " + rates);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        for(int i=0;i<10;i++)
        {
            recoveryArray[i] = Double.parseDouble(recoveryArray1[i]);
            Log.d("values", "Stock = " + recoveryArray[i]);
        }
        */


                //Take input from user how much data to be used from the available data.



                int count = 1; // number of stock values in the file
                double mean, variance, det, alpha, beta;
                int n = 8;// number of input from the total size which user wants to use to compute.
                //double[] actualStocks = new double[50];// actual stock values read from the file
                int[] pastNumber = new int[50];
                //double predictedValue = 0;//stores predicted value
                double[] phi = new double[3];
                double[] nphi = new double[3];
                double[][] X = new double[3][3];
                double[][] t = new double[3][3];
                double[][] inv = new double[3][3];
                double[][] adj = new double[3][3];
                double[] Y = new double[3];
                double[] summation = new double[3];
                int k = 0;

                alpha = 0.005;
                beta = 11.1;
                mean = 0;
                variance = 0;
                det = 0;

                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        phi[i] = 0;
                        nphi[i] = 0;
                        X[i][j] = 0;
                        t[i][j] = 0;
                        inv[i][j] = 0;
                        adj[i][j] = 0;
                        Y[i] = 0;
                        summation[i] = 0;
                    }
                }

                int predstart = n + 1;// case from which the prediction starts
                for (int i = 1; i <= n; i++) {
                    pastNumber[i] = i;
                }

                //calculating sum of phi(Xn)
                for (int i = 0; i < 3; i++) {
                    for (int j = 1; j <= n; j++) {
                        nphi[i] = nphi[i] + Math.pow(pastNumber[j], i);
                    }
                }

                //calculating transpose of phi(X)
                for (int i = 0; i < 3; i++) {
                    phi[i] = Math.pow(predstart, i);
                }

                //calculating S inverse
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        X[i][j] = beta * nphi[i] * phi[j]; //second term of S inverse
                        if (j == i)
                            X[i][j] = X[i][j] + alpha; //first term of S inverse , taken only for diagonal elements.
                    }
                }

                //calculating matrix S by finding inverse of matrix X
                //calculating the determinant of X
                det = det + (X[0][0] * (X[1][1] * X[2][2] - X[2][1] * X[1][2]) - X[0][1] * (X[1][0] * X[2][2] - X[2][0] * X[1][2]) + X[0][2] * (X[1][0] * X[2][1] - X[2][0] * X[1][1]));
                det = 1 / det;

                //finding transpose of X original matrix
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        t[i][j] = X[j][i];
                    }
                }

                //finding adjoint of X matrix
                adj[0][0] = (t[1][1] * t[2][2] - (t[2][1] * t[1][2]));
                adj[0][1] = -(t[1][0] * t[2][2] - (t[2][0] * t[1][2]));
                adj[0][2] = (t[1][0] * t[2][1] - (t[2][0] * t[1][1]));
                adj[1][0] = -(t[0][1] * t[2][2] - (t[2][1] * t[0][2]));
                adj[1][1] = (t[0][0] * t[2][2] - (t[2][0] * t[0][2]));
                adj[1][2] = -(t[0][0] * t[2][1] - (t[2][0] * t[0][1]));
                adj[2][0] = (t[0][1] * t[1][2] - (t[1][1] * t[0][2]));
                adj[2][1] = -(t[0][0] * t[1][2] - (t[1][0] * t[0][2]));
                adj[2][2] = (t[0][0] * t[1][1] - (t[1][0] * t[0][1]));

                //finding inverse step
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        inv[i][j] = adj[i][j] * det;
                    }
                }

                //calculating the variance
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        Y[i] = Y[i] + (phi[j] * inv[j][i]);
                    }
                }
                for (int i = 0; i < 3; i++) {
                    variance = variance + (Y[i] * phi[i]);
                }
                variance += (1 / beta);

                //calculating the mean.summation[] matrix used to calculate the sum.
                for (int i = 0; i < 3; i++) {
                    for (int j = 1; j <= n; j++) {
                        summation[i] += Math.pow(j, i) * Double.parseDouble(myList.get(j));
                    }
                }

                for (int i = 0; i < 3; i++) {
                    mean += Y[i] * summation[i];
                }
                mean = mean * beta;

                //calculating the predicted value
                predictedValue = mean + (2.99 * Math.sqrt(variance));
                double errorPred = 0;// subtracting from last actual stock value to calculate error
                errorPred = predictedValue - Double.parseDouble(myList.get(n-1));

                //calculating error matrix

                double[] error = new double[50];
                if (errorPred >= 0)
                    error[k] = errorPred;

                else
                    error[k] = -(errorPred);

                //actualSum += actualStocks[n + 1];
                //errorSum += error[k];


                Log.d("Predicted Value:", "Prediction: " + predictedValue);


                //return predictedValue;

                //MyHelp(predictedValue);



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }

        });

    }

    public static double getPred() {
        return predictedValue;
    }



}



