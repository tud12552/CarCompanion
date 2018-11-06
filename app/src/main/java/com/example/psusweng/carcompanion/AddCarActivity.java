package com.example.psusweng.carcompanion;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

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


    }
}