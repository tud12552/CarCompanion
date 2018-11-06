package com.example.psusweng.carcompanion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MaintenanceActivity extends AppCompatActivity {

    private TextView mTxtViewReadyFor = null;
    private TextView mTxtViewSearch = null;

    private ImageView mImageViewCurrentCarPic = null;

    private CheckBox mCheckBoxOilChange = null;
    private CheckBox mCheckBoxTireRot = null;

    private Button mBtnPartStore = null;
    private Button mBtnDealers = null;
    private Button mBtnExportReport = null;

    private ImageButton mImageBtnStats = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance);

        Intent previousScreenIntent = getIntent();
        final String mCurrentCarMake = previousScreenIntent.getStringExtra("CURRENT_CAR_MAKE");
        final String mCurrentCarModel = previousScreenIntent.getStringExtra("CURRENT_CAR_MODEL");

        mBtnPartStore = (Button)findViewById(R.id.buttonPartStores);
        mBtnDealers = (Button)findViewById(R.id.buttonDealers);
        mBtnExportReport = (Button) findViewById(R.id.buttonExportReport);

        mImageBtnStats = (ImageButton)findViewById(R.id.imageButtonStats);

        mBtnPartStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"@string/openInternetStr", Toast.LENGTH_SHORT).show();
                // Implement going to internet to browse for a part store
            }
        });

        mBtnDealers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "@string/openInternetStr", Toast.LENGTH_SHORT).show();
                // Implement going to internet to browse for an applicable dealer.
            }
        });

        mImageBtnStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToStatsScreen = new Intent(MaintenanceActivity.this,StatsActivity.class);
                goToStatsScreen.putExtra("CAR_MAKE",mCurrentCarMake);
                goToStatsScreen.putExtra("CAR_MODEL",mCurrentCarModel);
                startActivity(goToStatsScreen);
            }
        });
    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }
}
