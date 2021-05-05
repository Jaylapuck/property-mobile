package com.example.finalandroidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateActivity extends AppCompatActivity {

    private Button btn_create_add;
    private EditText edt_name, edt_address, edt_city, edt_region, edt_description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        btn_create_add = findViewById(R.id.btn_create_add);
        edt_name = findViewById(R.id.et_property_name);
        edt_address = findViewById(R.id.et_property_address);
        edt_city = findViewById(R.id.et_property_city);
        edt_region = findViewById(R.id.et_property_region);
        edt_description = findViewById(R.id.ed_property_desc);

        btn_create_add.setOnClickListener(new View.OnClickListener() {
            PropertyModel propertyModel;
            @Override
            public void onClick(View v) {
                try {
                    propertyModel = new PropertyModel
                            (-1, edt_name.getText().toString(), edt_address.getText().toString(), edt_city.getText().toString(), edt_region.getText().toString(), edt_description.getText().toString());
                } catch (Exception e){
                    propertyModel = new PropertyModel(-1, "error",  "error", "error", "error", "error");
                }

                DBHandler dbHandler = new DBHandler(CreateActivity.this);
                dbHandler.addOne(propertyModel);

                Intent intent = new Intent(CreateActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

}