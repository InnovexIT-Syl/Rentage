package com.example.rentage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class AuthenticationActivity extends AppCompatActivity {

    private Toolbar toolbarLogSign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        initialize();
    }

    private void initialize() {

        toolbarLogSign = findViewById(R.id.login_sign_in_toolbar);

        setSupportActionBar(toolbarLogSign);
        toolbarLogSign.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbarLogSign.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        getSupportActionBar().setTitle("Login / Sign up");
    }
}
