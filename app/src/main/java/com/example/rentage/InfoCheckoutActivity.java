package com.example.rentage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Objects;

public class InfoCheckoutActivity extends AppCompatActivity {

    private Toolbar toolbarCart;
    String[] countryArray;
    private Spinner spinnerCountry;
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_checkout);
        initialize();
    }

    private void initialize() {

        toolbarCart = findViewById(R.id.toolbar_info_checkout);
        spinnerCountry = findViewById(R.id.country_name_spinner);

        setSupportActionBar(toolbarCart);
        toolbarCart.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbarCart.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        getSupportActionBar().setTitle("");

        implementSpinnerCountry();
    }

    private void implementSpinnerCountry() {
        countryArray = new String[]{"Bangladesh","India","Pakistan","Bhutan","Nepal"};
        adapter = new ArrayAdapter<>(this, R.layout.sample_view, R.id.sampleTextId, countryArray);
        spinnerCountry.setAdapter(adapter);

        spinnerCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "" + countryArray[position] + " is Selected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
