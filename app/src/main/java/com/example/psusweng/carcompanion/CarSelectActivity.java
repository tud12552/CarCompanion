package com.example.psusweng.carcompanion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class CarSelectActivity extends AppCompatActivity {

    private static final String TAG = "CarSelectActivity";

    private TextView mTxtViewScreenTitle = null;
    private TextView mTxtViewCar = null;
    private TextView mTxtViewAddCar = null;
    private ImageButton mImageBtnViewCar = null;
    private ImageButton mImageBtnAddCar = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_select);

        mTxtViewScreenTitle = (TextView) findViewById(R.id.txtViewScreenTitle);
        mTxtViewCar = (TextView) findViewById(R.id.viewCarText);
        mTxtViewAddCar = (TextView) findViewById(R.id.addCarText);

        mImageBtnAddCar = (ImageButton) findViewById(R.id.addCarButton);
        mImageBtnViewCar = (ImageButton) findViewById(R.id.viewCarButton);


        final String currentCarMake = "@string/Chevy";
        final String currentCarModel = "@string/Silverado";

        mImageBtnViewCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent goToMaintenanceScreen = new Intent(CarSelectActivity.this, ListViewCars.class);
                startActivity(goToMaintenanceScreen);
            }
        });

        mImageBtnAddCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToAddCarScreen = new Intent(CarSelectActivity.this, AddCarActivity.class);
                startActivity(goToAddCarScreen);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}
