package com.example.psusweng.carcompanion;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.psusweng.carcompanion.Data.J1939Data;
import com.example.psusweng.carcompanion.Demo.BlueToothHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
public class AddCarActivity extends AppCompatActivity
{
    private Spinner mSpinMake = null;
    private Spinner mSpinModel = null;

    private EditText mEditTxtYear = null;
    private EditText mEditTxtMilage = null;
    private EditText mEditTxtOilChange = null;
    private EditText mEditTxtEstMilesYear = null;

    private ImageButton mImageBtnUploadPhoto = null;
    private Button mBtnAddCar = null;
    private ImageView mImageView = null;
    private Button mBtnPullEngineMiles = null;

    private String mSelectedMake;
    private String mSelectedModel;
    private static final String TAG = "AddCarActivity";
    private static final int REQUEST_IMAGE_CAPTURE = 1;

    DatabaseHelper mDatabaseHelper;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    DatabaseReference makesRef;

    private BlueToothHelper blueToothHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);


        mSpinMake = (Spinner)findViewById(R.id.makeDropdown);
        mSpinModel = (Spinner)findViewById(R.id.modelDropdown);

        mEditTxtEstMilesYear = (EditText)findViewById(R.id.estMileageBox);
        mEditTxtMilage = (EditText)findViewById(R.id.enterMileageBox);
        mEditTxtOilChange = (EditText)findViewById(R.id.lastOilChangeBox);
        mEditTxtYear = (EditText)findViewById(R.id.enterYearBox);

        mImageBtnUploadPhoto = (ImageButton) findViewById(R.id.uploadPhotoBtn);
        mBtnAddCar = (Button)findViewById(R.id.addCarBtn);
        mBtnPullEngineMiles = (Button)findViewById(R.id.pullMileageBtn);

        mSpinModel.setEnabled(false);


        mDatabaseHelper = new DatabaseHelper(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("UserCars");
        makesRef = firebaseDatabase.getReference("Makes");

        blueToothHelper = new BlueToothHelper();

        mImageBtnUploadPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Uploading photo", Toast.LENGTH_SHORT).show();
                dispatchTakePictureIntent();
            }
        });

        mSpinMake.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSelectedMake = mSpinMake.getItemAtPosition(position).toString();

                if(!mSelectedMake.equals("-"))
                {
                    toastMessage("You chose " + mSelectedMake);
                    mSpinModel.setEnabled(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mSpinModel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSelectedModel = mSpinModel.getItemAtPosition(position).toString();
                if(!mSelectedMake.equals("-"))
                {
                    toastMessage("You chose " + mSelectedModel);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mBtnAddCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String make = mSelectedMake;
                String model = mSelectedModel;
                String year = mEditTxtYear.getText().toString();
                String miles = mEditTxtMilage.getText().toString();
                String yearlyMileage = mEditTxtEstMilesYear.getText().toString();
                String lastOilChange = mEditTxtOilChange.getText().toString();

                Boolean okToAdd = true;
                int i = 0;

                String[] NewCar = new String[6];
                NewCar[0] = make;
                NewCar[1] = model;
                NewCar[2] = year;
                NewCar[3] = miles;
                NewCar[4] = yearlyMileage;
                NewCar[5] = lastOilChange;

                while(i < 6)
                {
                    if(NewCar[i].length() <= 0 || NewCar[i] == null)
                    {
                        okToAdd = false;
                        Log.d(TAG, "checkingNewCar: Failed");
                        toastMessage("A field is empty.");
                        break;
                    }
                        i++;

                }


                if(okToAdd)
                {
                    AddNewCar(make, model, year, miles, yearlyMileage, lastOilChange);
                    Intent goToCarSelect = new Intent(AddCarActivity.this, CarSelectActivity.class);
                    startActivity(goToCarSelect);
                }
            }
        });

        mBtnPullEngineMiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toastMessage("Pulling data from vehicle network.");
                J1939Data item = blueToothHelper.Upload();
                if (item == null)  // No connection
                {
                    toastMessage("Not paired with OBDII Adapter");
                } else {
                    Double miles = item.getDistanceTotal() * 0.621;  // conversion from km to miles
                    mEditTxtMilage.setText(miles + "");
                    Log.d(TAG, "Mileage is " + item.getDistanceTotal());
                }
            }
        });
    }

    public void AddNewCar(String make, String model, String year, String miles, String YearlyMiles, String LastOilChange)
    {
        final CarHelper newCar = new CarHelper(LastOilChange, Integer.parseInt(year), Integer.parseInt(YearlyMiles), Double.valueOf(miles), model, make);
        databaseReference.push().setValue(newCar).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                boolean insertData = task.isSuccessful();
                if(insertData)
                {
                    toastMessage("Car Successfully Added.");
                }
                else
                {
                    toastMessage("Car Was Not Added.");
                }
            }
        });

        makesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            boolean tempMake;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot makes : dataSnapshot.getChildren()) {
                    tempMake = makes.getValue().equals(newCar.Make);
                    if(tempMake)
                    {
                        break;
                    }
                }
                if(!tempMake)
                {
                    makesRef.push().setValue(newCar.Make);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageView.setImageBitmap(imageBitmap);
        }
    }
}