package com.example.psusweng.carcompanion;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListViewCars extends AppCompatActivity {

    private static final String TAG = "ListAllCarsActivity";

    DatabaseHelper mDatabaseHelper;

    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_cars);

        mListView = (ListView)findViewById(R.id.listViewAllCars);
        mDatabaseHelper = new DatabaseHelper(this);

        populateListView();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        populateListView();
    }

    private void populateListView()
    {
        Log.d(TAG, "populateListView: Display data in the ListView.");
        // Get the data from the database and append to the ListView.
        Cursor data = mDatabaseHelper.getAllCars();

        ArrayList<String> listData = new ArrayList<>();

        while(data.moveToNext())
        {
            // Get the data from the database in column 1
            // then add it to the ListView.
            listData.add(data.getString(1) + " " + data.getString(2) + " " + data.getString(3)); // Year Make Model
        }

        ListAdapter adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,listData);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
            {
                String car = adapterView.getItemAtPosition(position).toString();
                String[] temp = car.split(" ");

                Intent goToMaintHighLevel = new Intent(ListViewCars.this, CarMaintHighLevel.class);
                goToMaintHighLevel.putExtra("CURRENT_CAR_MAKE", temp[1]);
                goToMaintHighLevel.putExtra("CURRENT_CAR_MODEL", temp[2]);
                startActivity(goToMaintHighLevel);

            }
        });
    }

    private void toastMessage(String msg)
    {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }
}
