package com.example.psusweng.carcompanion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class CarSelectActivity extends AppCompatActivity {

    private TextView mTxtViewScreenTitle = null;
    private TextView mTxtViewCar = null;
    private TextView mTxtViewAddCar = null;
    private ImageButton mImageBtnViewCar = null;
    private ImageButton mImageBtnAddCar = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_select);


        mTxtViewScreenTitle = findViewById(R.id.txtViewScreenTitle);
        mTxtViewCar = findViewById(R.id.viewCarText);
        mTxtViewAddCar = findViewById(R.id.addCarText);

        mImageBtnAddCar = findViewById(R.id.addCarButton);
        mImageBtnViewCar = findViewById(R.id.viewCarButton);


        mImageBtnViewCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent goToMaintenanceScreen = new Intent(CarSelectActivity.this, CarMaintHighLevel.class);
                startActivity(goToMaintenanceScreen);
            }
        });

        mImageBtnAddCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToAddCarScreen = new Intent(CarSelectActivity.this,AddCarActivity.class);
                startActivity(goToAddCarScreen);
            }
        });

    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }

}
