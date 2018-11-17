package com.example.psusweng.carcompanion;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

import java.util.ArrayList;

public class ListViewCars extends AppCompatActivity {

    private static final String TAG = "ListAllCarsActivity";

    DatabaseHelper mDatabaseHelper;

    private ListView mListView;
    private Button mBtnDeleteAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_cars);

        mListView = (ListView)findViewById(R.id.listViewAllCars);
        mDatabaseHelper = new DatabaseHelper(this);
        mBtnDeleteAll = (Button)findViewById(R.id.btnDeleteAll);

        populateListView();

        mBtnDeleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabaseHelper.deleteAllCars();

                toastMessage("All cars deleted.");

                Intent goToCarSelect = new Intent(ListViewCars.this,CarSelectActivity.class);
                startActivity(goToCarSelect);
            }
        });
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
            listData.add(data.getString(1) + " " + data.getString(2) + " " + data.getString(3) + " " + data.getString(4)); // Year Make Model Miles
        }

        ListAdapter adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,listData);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
            {
                String car = adapterView.getItemAtPosition(position).toString();
                String[] temp = car.split(" ");

                Cursor data = mDatabaseHelper.getOneCar(temp);

                Intent goToMaintHighLevel = new Intent(ListViewCars.this, CarMaintHighLevel.class);

                data.moveToFirst();
                String year = data.getString(1);

                goToMaintHighLevel.putExtra("CURRENT_CAR_YEAR", data.getString(1));
                goToMaintHighLevel.putExtra("CURRENT_CAR_MAKE", data.getString(2));
                goToMaintHighLevel.putExtra("CURRENT_CAR_MODEL",data.getString(3));
                goToMaintHighLevel.putExtra("CURRENT_CAR_MILES",data.getString(4));
                goToMaintHighLevel.putExtra("CURRENT_CAR_YEAR_MILES",data.getString(5));
                goToMaintHighLevel.putExtra("CURRENT_CAR_OIL",data.getString(6));
                startActivity(goToMaintHighLevel);

            }
        });
    }

    private void toastMessage(String msg)
    {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }
}
