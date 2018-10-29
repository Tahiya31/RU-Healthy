package com.example.softwareengineering;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StepCounter extends AppCompatActivity implements SensorEventListener,StepListener {

    private TextView TvMiles;
    private TextView TvCalories;
    private CircularProgressBar circularProgressBar1;

    FirebaseAuth auth;
    FirebaseUser user;
    DatabaseReference databaseReference, dr1;
    Map<String, String> countWithDate = new HashMap<>();
    String previousDay;


    private  StepDetector simpleStepDetector;
    private SensorManager sensorManager;
    private Sensor accel;

    private static final String TEXT_Miles_STEPS = "   Mile";
    private static final String TEXT_Miles_Calorie ="     Kcal";

    private int numSteps;
    private double miles;
    private double calories;


    //ramya
    DateFormat dateFormat;
    Date currentDate;
    String dateString;
    String numberOfSteps;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.step_count);

        //Get Sensor Values using the SensorManager
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        simpleStepDetector = new StepDetector();
        simpleStepDetector.registerListener(this);



        //TvSteps=(TextView)findViewById(R.id.tv_steps);
        TvMiles=(TextView) findViewById(R.id.tv_miles);
        TvCalories=(TextView) findViewById(R.id.tv_calories);

        auth = FirebaseAuth.getInstance();
        user= auth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://ruhealthy-69349.firebaseio.com/Patient/"+ user.getUid());
        //Log.d("id", "id: "+ id2);
        //DatabaseReference dbref = databaseReference.getKey();
        dr1 = databaseReference.child("Step Count");


//For The ProgressBar
        circularProgressBar1 = (CircularProgressBar) findViewById(R.id.circularProgress);
        circularProgressBar1.setProgressColor(getResources().getColor(R.color.colorPrimaryDark));
        circularProgressBar1.setProgressWidth(30);


//THIS METHOD IS TO MAKE THE COUNTER RUN AUTO AND TO WORK IN THE BACKGROUND// BY AYMEN
        numSteps=0;
        sensorManager.registerListener(StepCounter.this, accel, SensorManager.SENSOR_DELAY_FASTEST);


        Date currentTime = Calendar.getInstance().getTime();
        double x,y;



        GraphView graph=(GraphView) findViewById(R.id.graph);
        graph.getViewport().setScrollable(true); // enables horizontal scrolling
        graph.getViewport().setScrollableY(true); // enables vertical scrolling

        graph.getViewport().setScalableY(true); // enables vertical zooming and scrolling
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(1, 10),
                new DataPoint(2, 123),
                new DataPoint(3, 652),
                new DataPoint(4, 431),
                new DataPoint(5, 312),
                new DataPoint(6, 412),
                new DataPoint(7, 590),
                new DataPoint(8, 1000),
                new DataPoint(9, 500),
                new DataPoint(10, 10),
                new DataPoint(11, 1000),
                new DataPoint(12, 215),
                new DataPoint(13, 931),
                new DataPoint(14, 850),
                new DataPoint(15, 250),
                new DataPoint(16, 512),
                new DataPoint(17, 57),
                new DataPoint(18, 554),
                new DataPoint(19, 78),
                new DataPoint(20, 312),
                new DataPoint(21, 521),
                new DataPoint(22, 290),
                new DataPoint(23, 513),
                new DataPoint(24, 980),
                new DataPoint(25, 198),
                new DataPoint(26, 782),
                new DataPoint(27, 878),
                new DataPoint(28, 102),
                new DataPoint(29, 412),
                new DataPoint(30, 419),




        });
        graph.addSeries(series);
        // use static labels for horizontal and vertical labels
        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
       // staticLabelsFormatter.setHorizontalLabels(new String[] {"Time","Date"});
        staticLabelsFormatter.setVerticalLabels(new String[] {"0", "250", "500","750","1000"});
        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);






    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            simpleStepDetector.updateAccel(
                    event.timestamp, event.values[0], event.values[1], event.values[2]);
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void step(long timeNs) {

        numSteps++;
        miles=(numSteps*(0.001))*0.6;
        calories=numSteps*0.1;
        DecimalFormat mm = new DecimalFormat("#.##");
        //DecimalFormat ss = new DecimalFormat("#");
        DecimalFormat cc = new DecimalFormat("#");


        circularProgressBar1.setProgress(numSteps);
        TvMiles.setText((mm.format(miles))+TEXT_Miles_STEPS);
        TvCalories.setText((cc.format(calories))+TEXT_Miles_Calorie);

        //return numSteps;


        dateFormat = new SimpleDateFormat("yyyyMMdd");
        currentDate = Calendar.getInstance().getTime();

        dateString = dateFormat.format(currentDate);
        databaseReference.child("Step Count").child(dateString).setValue(numSteps);
    }




}
