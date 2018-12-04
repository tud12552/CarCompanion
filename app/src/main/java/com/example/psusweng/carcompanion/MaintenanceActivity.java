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

    String mCurrentCarModel, mCurrentCarMake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance);

        Intent previousScreenIntent = getIntent();
        mCurrentCarMake = previousScreenIntent.getStringExtra("CURRENT_CAR_MAKE");
        mCurrentCarModel = previousScreenIntent.getStringExtra("CURRENT_CAR_MODEL");

        mTxtViewReadyFor = (TextView)findViewById(R.id.textViewReadyFor);

        mTxtViewReadyFor.setText("Maintenance Needed for: " + mCurrentCarMake + " " + mCurrentCarModel);

        mBtnPartStore = (Button)findViewById(R.id.buttonPartStores);
        mBtnDealers = (Button)findViewById(R.id.buttonDealers);
        mBtnExportReport = (Button) findViewById(R.id.buttonExportReport);

        mImageBtnStats = (ImageButton)findViewById(R.id.imageButtonStats);

        mBtnPartStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), R.string.nearbyAutoPartsStores, Toast.LENGTH_SHORT).show();
                goToGoogleForAutoPartsStore(v);
            }
        });

        mBtnDealers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), R.string.nearbyDealerships, Toast.LENGTH_SHORT).show();
                goToGoogleForDealerships(v);
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
    protected void onResume() {
        super.onResume();
    }

    public void goToGoogleForAutoPartsStore (View view) {
        goToUrl("https://www.google.com/maps/search/auto+parts+store");
    }

    public void goToGoogleForDealerships (View view) {
        goToUrl("https://www.google.com/maps/search/"+mCurrentCarMake+"+dealerships");
    }

    private void goToUrl (String url) {
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra("url", url);
        startActivity(intent);
    }
}
