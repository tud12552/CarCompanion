package com.example.psusweng.carcompanion;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.psusweng.carcompanion.Data.J1939Data;
import com.example.psusweng.carcompanion.Demo.BlueToothHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;

public class StatsActivity extends AppCompatActivity {

    TextView mMilesPerGallon;
    TextView mFuelUsage;

    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        mMilesPerGallon = (TextView)findViewById(R.id.textViewMPG);
        mFuelUsage = (TextView)findViewById(R.id.textViewFuelUsage);

        ref = FirebaseDatabase.getInstance().getReference("Demos/Sample00");
    }

    @Override
    protected void onStart() {
        super.onStart();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                J1939Data item = dataSnapshot.getValue(J1939Data.class);

                Double miles = item.getDistanceTotal() * 0.621;  // Convert km to miles
                Double gal = item.getFuelTotal() * 0.264;  // Convert liters to gallons
                Double hours = item.getHoursTotal();

                DecimalFormat mpg = new DecimalFormat("###.#");
                DecimalFormat gph = new DecimalFormat("#.###");

                mMilesPerGallon.setText("MPG: " + mpg.format(miles/gal));
                mFuelUsage.setText("Gal/Hr: " + gph.format(gal/hours));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
