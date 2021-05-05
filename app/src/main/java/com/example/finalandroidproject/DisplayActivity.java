package com.example.finalandroidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class DisplayActivity extends AppCompatActivity {

    private TextView txt_display_name;
    private TextView txt_display_address;
    private TextView txt_display_city;
    private TextView txt_display_region;
    private TextView txt_display_desc;

    private DBHandler dbHandler;

    private PropertyModel propertyModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        txt_display_name = findViewById(R.id.txt_display_name);
        txt_display_address = findViewById(R.id.txt_display_address);
        txt_display_city = findViewById(R.id.txt_display_city);
        txt_display_region = findViewById(R.id.txt_display_region);
        txt_display_desc = findViewById(R.id.txt_display_description);

        Bundle bundle;
        bundle=getIntent().getExtras();
        if (bundle!=null){
            String propertyName = bundle.getString("name");

            dbHandler = new DBHandler(DisplayActivity.this);

            PropertyModel propertyModel = dbHandler.searchForSpecificPropertyByName(propertyName);

            txt_display_name.setText("Name: " + propertyName);


            txt_display_name.setText("Name: " + propertyModel.getName());
            txt_display_address.setText("Address: " + propertyModel.getAddress());
            txt_display_city.setText("City: " + propertyModel.getCity());
            txt_display_region.setText("Region: " + propertyModel.getRegion());
            txt_display_desc.setText("Description: " + propertyModel.getDescription());



        }
    }
}