package com.example.psusweng.carcompanion;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListViewCars extends AppCompatActivity {

    private static final String TAG = "ListAllCarsActivity";

    DatabaseHelper mDatabaseHelper;

    FirebaseDatabase firebaseDatabase = null;
    DatabaseReference databaseReference = null;

    ArrayList<CarHelper> dbCars;
    ArrayList<String> listData;

    private ListView mListView;
    private Button mBtnDeleteAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_cars);

        mListView = (ListView)findViewById(R.id.listViewAllCars);
        mDatabaseHelper = new DatabaseHelper(this);
        mBtnDeleteAll = (Button)findViewById(R.id.btnDeleteAll);

        dbCars = new ArrayList<>();

        LoadFirebaseCars();

        mBtnDeleteAll.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                mDatabaseHelper.deleteAllCars();

                toastMessage("All cars deleted.");

                Intent goToCarSelect = new Intent(ListViewCars.this,CarSelectActivity.class);
                startActivity(goToCarSelect);
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
            {
                CarHelper selectedCar = dbCars.get(position);

                Intent goToMaintHighLevel = new Intent(ListViewCars.this, CarMaintHighLevel.class);

                goToMaintHighLevel.putExtra("CURRENT_CAR_YEAR", String.valueOf(selectedCar.Year));
                goToMaintHighLevel.putExtra("CURRENT_CAR_MAKE", selectedCar.Make);
                goToMaintHighLevel.putExtra("CURRENT_CAR_MODEL",selectedCar.Model);
                goToMaintHighLevel.putExtra("CURRENT_CAR_MILES",selectedCar.Mileage);
                goToMaintHighLevel.putExtra("CURRENT_CAR_YEAR_MILES",selectedCar.EstYearlyMiles);
                goToMaintHighLevel.putExtra("CURRENT_CAR_OIL",selectedCar.LastOilChange);
                startActivity(goToMaintHighLevel);

            }
        });
    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }



    public void LoadFirebaseCars()
    {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("UserCars");

        listData = new ArrayList<>();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dbCars.clear();
                for(DataSnapshot databaseCars : dataSnapshot.getChildren()) {

                    CarHelper car = databaseCars.getValue(CarHelper.class);

                    displayCarsInListView(car);
                    Log.d(TAG, "A car was added.");
                    dbCars.add(car);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        /*databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                dbCars.clear();
                for(DataSnapshot databaseCars : dataSnapshot.getChildren()) {

                    CarHelper car = databaseCars.getValue(CarHelper.class);

                    displayCarsInListView(car);
                    Log.d(TAG, "A car was added.");
                    dbCars.add(car);
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });*/

    }


    public void displayCarsInListView(CarHelper car)
    {
        Log.d(TAG, "\n : Added car to listView.\n");
        // Get the data from the database in column 1
        // then add it to the ListView.
        listData.add(String.valueOf(car.Year) + " " + car.Make + " " + car.Model
                + "\nCurrent Mileage: " + String.valueOf(car.Mileage)
                + "\nEst. Miles/Year: " + String.valueOf(car.EstYearlyMiles)
                + "\nLast Oil Change: " + car.LastOilChange);

        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        mListView.setAdapter(adapter);
    }

    private void toastMessage(String msg)
    {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }
}
