package com.simplifiers.locatemydevice;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddDevice extends AppCompatActivity {

    FirebaseFirestore db;
    EditText imei,lati,longi;
     String username,password;
     Intent i;
    Button addDevice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device);

        Bundle extras = getIntent().getExtras();

        username = extras.getString("username");
        password = extras.getString("password");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(AddDevice.this,UserActivity.class);
                Bundle extras = new Bundle();
                extras.putString("username", username);
                extras.putString("password", password);
                i.putExtras(extras);
                startActivity(i);
            }
        });

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_back);

        imei = (EditText)findViewById(R.id.imei);
        lati = (EditText)findViewById(R.id.lati);
        longi = (EditText)findViewById(R.id.longi);
        addDevice =(Button)findViewById(R.id.buttonadddevice);
        addDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDevice();
            }
        });

    }

    private void addDevice() {
        final String imeino,latitude,longitude;

        imeino = imei.getText().toString().trim();
        latitude = lati.getText().toString().trim();
        longitude = longi.getText().toString().trim();

        if (TextUtils.isEmpty(imeino)) {
            imei.setError("Please enter IMEI no");
            imei.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(latitude)) {
            lati.setError("Please enter your latitude");
            lati.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(longitude))
        {
            longi.setError("Please enter longitude");
            longi.requestFocus();
            return;
        }

        Map<String,Object> device= new HashMap<>();
        device.put("IMEI",imeino);
        device.put("lat",latitude);
        device.put("long",longitude);

        db = FirebaseFirestore.getInstance();
        db.collection("Registered_User").document(username)
                .collection("Devices")
                .document()
                .set(device)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(AddDevice.this, "Device Registered", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(AddDevice.this, "Device Not Registered", Toast.LENGTH_SHORT).show();
                        }
                    }

                });
    }
}
