package com.example.psusweng.carcompanion;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AddCarActivity extends AppCompatActivity
{
    private TextView mTxtViewScreenTitle = null;
    private TextView mTxtViewMake = null;
    private TextView mTxtViewYear = null;
    private TextView mTxtViewModel = null;
    private TextView mTxtViewMilage = null;
    private TextView mTxtViewEstMilageYear = null;
    private TextView mTxtViewUploadPhoto = null;
    private TextView mTxtViewLasOilChange = null;

    private Spinner mSpinMake = null;
    private Spinner mSpinModel = null;

    private EditText mEditTxtYear = null;
    private EditText mEditTxtMilage = null;
    private EditText mEditTxtOilChange = null;
    private EditText mEditTxtEstMilesYear = null;

    private ImageButton mImageBtnUploadPhoto = null;

    private Button mBtnAddCar = null;

    private String mSelectedMake;
    private String mSelectedModel;

    DatabaseHelper mDatabaseHelper;

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

        mBtnAddCar = (Button)findViewById(R.id.addCarBtn);


        mDatabaseHelper = new DatabaseHelper(this);


        mSpinMake.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSelectedMake = mSpinMake.getItemAtPosition(position).toString();
                toastMessage("You chose " + mSelectedMake);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mSpinModel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSelectedModel = mSpinModel.getItemAtPosition(position).toString();
                toastMessage("You chose " + mSelectedModel);
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
//                String[] NewCar = new String[]{make, model, year, miles, yearlyMileage, lastOilChange};
//
//                Boolean okToAdd = false;
//
//                for (int i = 0; i <= NewCar.length; i++)
//                {
//                    if (NewCar[i].isEmpty()) {
//                        okToAdd = false;
//                        break;
//                    } else {
//                        okToAdd = true;
//                    }
//                }
//                if(okToAdd)
//                {
                    AddNewCar(make,model,year,miles,yearlyMileage,lastOilChange);
//                }
//                else
//                {
//                    toastMessage("You must inset a name.");
//                }
            }
        });
    }

    public void AddNewCar(String make, String model, String year, String miles, String YearlyMiles, String LastOilChange)
    {
        boolean insertData = mDatabaseHelper.addNewCar(make, model, year, miles, YearlyMiles, LastOilChange);

        if(insertData)
        {
            toastMessage("Car Successfully Added.");
        }
        else
        {
            toastMessage("Car Was Not Added.");
        }
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