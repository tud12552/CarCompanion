package com.example.psusweng.carcompanion.Demo;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.psusweng.carcompanion.Data.J1939Data;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BlueToothHelper {
    private DatabaseReference reference;
    private J1939Data jData;
    private BlueToothListener blueToothListener;
    private static final String sample = "Demos/Sample00";

    public BlueToothHelper()
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        reference = database.getReference(sample);
        blueToothListener = new BlueToothListener(this);
        reference.addValueEventListener(blueToothListener);
    }

    public J1939Data Upload() {
        return jData;
    }

    // method is only intended for the BlueToothListener
    public void set_jData(J1939Data data)
    {
        jData = data;
    }
}
