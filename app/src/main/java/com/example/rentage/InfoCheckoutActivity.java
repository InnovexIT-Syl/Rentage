package com.example.rentage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.rentage.model.InfoCheckout;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class InfoCheckoutActivity extends AppCompatActivity {

    private Toolbar toolbarCart;
    String[] countryArray;
    private Spinner spinnerCountry;
    ArrayAdapter<String> adapter;
    FirebaseAuth firebaseAuth;
    EditText email_or_mobile, first_name, last_name, address, city, postal_code;
    Button byeItNowButton;
    String country;
    DatabaseReference databaseReference;

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

        email_or_mobile = findViewById(R.id.email_or_mobile);
        first_name = findViewById(R.id.first_name);
        last_name = findViewById(R.id.last_name);
        address = findViewById(R.id.address);
        city = findViewById(R.id.city);
        postal_code = findViewById(R.id.postal_code);
        byeItNowButton = findViewById(R.id.byeItNowButton);
        getSupportActionBar().setTitle("Information checkout");

        implementSpinnerCountry();

        byeItNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadInfo();
            }
        });
    }

    private void uploadInfo() {
        // firebase database instance

        String name = first_name.getText().toString() + last_name.getText().toString();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference dbRef = databaseReference.child("Info_checkout").push();
        final String key = dbRef.getKey();
        InfoCheckout infoCheckout = new InfoCheckout(key, name, address.getText().toString(), city.getText().toString(), country,
                postal_code.getText().toString(), email_or_mobile.getText().toString());

        dbRef.setValue(infoCheckout).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Intent intent = new Intent(getApplicationContext(),
                        InfoCheckoutActivity.class);

                Toast.makeText(InfoCheckoutActivity.this, "Info checkout successfully uploaded",
                        Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(InfoCheckoutActivity.this, "Data upload failed!" + e.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sub_menu, menu);

        menu.findItem(R.id.about).setVisible(false);
        menu.findItem(R.id.logout).setVisible(true);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                startActivity(new Intent(getApplicationContext(), AboutActivity.class));
                finish();
                return true;
            case R.id.logout:
                firebaseAuth.signOut();
                Toast.makeText(getApplicationContext(), "You are logout successfully.", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void implementSpinnerCountry() {
        countryArray = new String[]{"Bangladesh", "India", "Pakistan", "Bhutan", "Nepal"};
        adapter = new ArrayAdapter<>(this, R.layout.sample_view, R.id.sampleTextId, countryArray);
        spinnerCountry.setAdapter(adapter);

        spinnerCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                country = countryArray[position];
                Toast.makeText(getApplicationContext(), "" + countryArray[position] + " is Selected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
