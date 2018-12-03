package com.example.psusweng.carcompanion.Demo;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.psusweng.carcompanion.Data.J1939Data;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class BlueToothListener implements ValueEventListener {

    private BlueToothHelper blueToothHelper;
    private static final String TAG = "BlueToothListener";

    BlueToothListener(BlueToothHelper helper)
    {
        blueToothHelper = helper;
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
    {
        J1939Data item = dataSnapshot.getValue(J1939Data.class);
        blueToothHelper.set_jData(item);
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {
        // Failed to read value(s)
        Log.d(TAG, "Failed to read value");
    }

}
