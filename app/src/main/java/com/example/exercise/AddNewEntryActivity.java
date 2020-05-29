package com.example.exercise;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.room.Database;
import androidx.room.Room;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddNewEntryActivity extends AppCompatActivity {
    AppDataBase dataBase;
    EditText writeNewEntry;
    EditText writeNewEntryTitle;
    EditText writeNewEntryPlace;
    CheckBox isNewEntryWork;
    private TextView displayDate;
    Button setGPSbutton;
    TextView coordinates;
    LocationManager locationManager;
    LocationListener locationListener;
    private DatePickerDialog.OnDateSetListener onDateSetListener;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_entry);
        writeNewEntry = (EditText) findViewById(R.id.writeEntry);
        writeNewEntryTitle = (EditText) findViewById(R.id.newEntryTitle);
        writeNewEntryPlace = (EditText) findViewById(R.id.placeEntry);
        isNewEntryWork = (CheckBox) findViewById(R.id.isWorkCheck);
        displayDate = (TextView) findViewById(R.id.newEntryDate);
        displayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddNewEntryActivity.this, android.R.style.Theme_Holo_Dialog_MinWidth,
                        onDateSetListener, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
        configureLocation();
        dataBase = Room.databaseBuilder(getApplicationContext(), AppDataBase.class, "diaryDatabase").fallbackToDestructiveMigration().allowMainThreadQueries().build();
        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                Log.d("AddNewEntryActivity", "onDateSet: " + year + "/" + month + "/" + dayOfMonth);
                String date = year + "/" + month + "/" + dayOfMonth;
                displayDate.setText(date);
            }
        };
        configureSaveButton();

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void configureLocation() {
        setGPSbutton=(Button)findViewById(R.id.setGPSCoordinates);
        coordinates=(TextView)findViewById(R.id.GPSCoordinates);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                coordinates.append("\n "+ location.getLatitude()+" "+location.getLongitude());
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        };
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                     Manifest.permission.INTERNET},1);
            return;
        }else{
            configureGPSButton(); }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[]permissions, int[]grantResults){
        switch(requestCode){
            case 1:
                if(grantResults.length>0&& grantResults[0]==PackageManager.PERMISSION_GRANTED){
                configureGPSButton();
                return;
        }
    }}


    public void configureGPSButton(){
        setGPSbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationManager.requestLocationUpdates("gps", 5000, 0, locationListener);}
        });
    }
    private void configureSaveButton(){
        Button saveButton = (Button)findViewById(R.id.SaveNewEntryButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(writeNewEntryTitle.getText())){
                    writeNewEntryTitle.setError("Title is required to make new entry!");
                    return;
                }
                if(TextUtils.isEmpty(writeNewEntry.getText())){
                    writeNewEntry.setError("Journal entry is meaningless without the description!");
                    return;
                }
                ArrayList<Entry> list=new ArrayList<>();
                Entry newEntry = new Entry(writeNewEntryTitle.getText().toString(),
                        writeNewEntry.getText().toString(),writeNewEntryPlace.getText().toString(),
                        displayDate.getText().toString(),isNewEntryWork.isChecked(),coordinates.getText().toString());
                int id = ListEntriesActivity.entries.size();
                newEntry.setId(id);
                dataBase.entryDao().insertAll(newEntry);
                ListEntriesActivity.addToEntries(newEntry);
                finish();
            }
        });
    }
}
