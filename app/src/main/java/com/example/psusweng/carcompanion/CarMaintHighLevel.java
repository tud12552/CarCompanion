package com.example.psusweng.carcompanion;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CarMaintHighLevel extends AppCompatActivity {

    private int mGalOfGas;
    private TextView mTxTViewScreenTitle = null;
    private TextView mTxtViewUpcomingMaint = null;
    private TextView mTxtViewRecentGas = null;
    private TextView mTxTViewNumGalsInput = null;
    private TextView mTxtViewFuelPrice = null;
    private Button mBtnAddGas = null;
    private Button mBtnViewAllMaint = null;
    private Button mBtnDelete = null;
    private EditText mEditTxtRefuelGals =  null;
    private EditText mEditTxtRefuelGalsPrice = null;
    private ImageView mImageViewCurrentCarPic = null;
    private ProgressBar mProgressBarMaintSoon = null;

    DatabaseHelper databaseHelper;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_maint_high_level);

        mGalOfGas = 12;
        mTxTViewScreenTitle = (TextView)findViewById(R.id.txtViewScreenTitle);
        mTxtViewUpcomingMaint = (TextView)findViewById(R.id.txtViewUpcomingCarMaint);
        mTxtViewRecentGas = (TextView)findViewById(R.id.txtViewRecentGas);
        mTxTViewNumGalsInput = (TextView)findViewById(R.id.txtViewGalsInput);
        mTxtViewFuelPrice = (TextView)findViewById(R.id.txtViewFuelPrice);

        mBtnAddGas = (Button)findViewById(R.id.btnAddGas);
        mBtnViewAllMaint = (Button)findViewById(R.id.btnViewAllMaint);
        mBtnDelete = (Button)findViewById(R.id.btnDelete);


        mEditTxtRefuelGals = (EditText) findViewById(R.id.editTxtRefuelGals);
        mEditTxtRefuelGalsPrice = (EditText)findViewById(R.id.editTxtRefuelGalsPrice);

        mImageViewCurrentCarPic = (ImageView) findViewById(R.id.CurrentCarPic);

        mProgressBarMaintSoon = (ProgressBar)findViewById(R.id.progressBarMaintSoon);

        databaseHelper = new DatabaseHelper(this);

        Intent previousScreenIntent = getIntent();
        final String currentCarYear = previousScreenIntent.getStringExtra("CURRENT_CAR_YEAR");
        final String currentCarMake = previousScreenIntent.getStringExtra("CURRENT_CAR_MAKE");
        final String currentCarModel = previousScreenIntent.getStringExtra("CURRENT_CAR_MODEL");
        final String currentCarMiles = previousScreenIntent.getStringExtra("CURRENT_CAR_MILES");
//        final String currentOilChange = previousScreenIntent.getStringExtra("CURRENT_CAR_OIL");
//        final String currentYearlyMiles = previousScreenIntent.getStringExtra("CURRENT_CAR_YEAR_MILES");

        final String[]currentCar = new String[]{currentCarYear, currentCarMake, currentCarModel, currentCarMiles};//, currentYearlyMiles, currentOilChange};
//        final String[]currentCar = new String[]{currentCarMake, currentCarModel};

        mTxtViewUpcomingMaint.setText("Your " + currentCarMake + " " + currentCarModel + " is nearing: " + "");

        mBtnAddGas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"@string/GasAddedMsgStr", Toast.LENGTH_SHORT).show();

                mGalOfGas = Integer.parseInt(mEditTxtRefuelGals.getText().toString());

            }
        });

        mBtnViewAllMaint.setOnClickListener(new View.OnClickListener()
        {
           @Override
           public void onClick(View v)
           {
               Intent goToFullMaintenanceScreen = new Intent(CarMaintHighLevel.this,MaintenanceActivity.class);
               goToFullMaintenanceScreen.putExtra("CURRENT_CAR_MAKE",currentCarMake);
               goToFullMaintenanceScreen.putExtra("CURRENT_CAR_MODEL",currentCarModel);
               startActivity(goToFullMaintenanceScreen);
           }
        });

        mBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelper.deleteCar(currentCar);

                toastMessage("Deleted from database.");

                Intent goBack = new Intent(CarMaintHighLevel.this,ListViewCars.class);
                startActivity(goBack);
            }
        });
    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }

public void toastMessage(String msg)
        {
        Toast.makeText(getApplicationContext(), msg,Toast.LENGTH_SHORT).show();
        }
}
