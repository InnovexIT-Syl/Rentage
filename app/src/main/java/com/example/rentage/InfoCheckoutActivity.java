package com.example.rentage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.rentage.model.InfoCheckout;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


import java.text.SimpleDateFormat;
import java.util.Date;



public class InfoCheckoutActivity extends AppCompatActivity {

    private Toolbar toolbarCart;
    ArrayAdapter<String> adapter;
    FirebaseAuth firebaseAuth;
    EditText email_or_mobile, first_name, last_name, address, postal_code;
    Button byeItNowButton;
    String uid;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    FirebaseUser firebaseUser;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_checkout);
        initialize();
    }

    private void initialize() {

        toolbarCart = findViewById(R.id.toolbar_info_checkout);

        setSupportActionBar(toolbarCart);
        toolbarCart.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbarCart.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        email_or_mobile = findViewById(R.id.email_or_mobile);
        first_name = findViewById(R.id.first_name);
        last_name = findViewById(R.id.last_name);
        address = findViewById(R.id.address);
        postal_code = findViewById(R.id.postal_code);
        byeItNowButton = findViewById(R.id.byeItNowButton);
        getSupportActionBar().setTitle("Information checkout");

        progressDialog = new ProgressDialog(this);

        checkUserStatus();
        byeItNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadInfo();
            }
        });
    }

    private void uploadInfo() {
        progressDialog.setMessage("Added info..");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        if (uid != null) {
            databaseReference = firebaseDatabase.getReference("Users");
            Query query = databaseReference.orderByChild("email").equalTo(firebaseUser.getEmail());
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    // checks until required data got
                    for (DataSnapshot data_snapshot : dataSnapshot.getChildren()) {

                        String email = data_snapshot.child("email").getValue().toString();
                        String orderById = uid;

                        String name = first_name.getText().toString() + " " + last_name.getText().toString();
                        databaseReference = FirebaseDatabase.getInstance().getReference();
                        final DatabaseReference dbRef = databaseReference.child("Info_checkout").push();
                        final String key = dbRef.getKey();

                        Date date = new Date();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy hh.mm aa zzzz ");
                        String originalDate = dateFormat.format(date);

                        InfoCheckout infoCheckout = new InfoCheckout(key, name, address.getText().toString(), email, orderById,
                                postal_code.getText().toString(), email_or_mobile.getText().toString(),originalDate);

                        dbRef.setValue(infoCheckout).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                progressDialog.dismiss();
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
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    progressDialog.dismiss();
                }
            });
        } else {
            startActivity(new Intent(getApplicationContext(), AuthenticationActivity.class));
        }
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

    private void checkUserStatus() {
        if (firebaseUser != null) {
            uid = firebaseUser.getUid();
        }
    }
}
