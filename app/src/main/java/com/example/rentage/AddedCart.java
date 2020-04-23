package com.example.rentage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.rentage.adapter.AddedCartAdapter;
import com.example.rentage.model.AddedCartModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddedCart extends AppCompatActivity {

    private RecyclerView addedCartRecyclerView;
    private List<AddedCartModel> addedCartModelList = new ArrayList<>();
    private Toolbar toolbarCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_added_cart);

        initialize();
    }

    private void initialize() {
        addedCartRecyclerView = findViewById(R.id.added_cart_recyclerView);
        toolbarCart = findViewById(R.id.toolbar_cart);

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
        addedCart();
    }

    private void addedCart() {
        addedCartModelList.add(new AddedCartModel(new Date().toString(),R.drawable.helicopter,"Azimut Yacht 48 ft ","AED 1,200.00","6","AED 4,800.00"));
        addedCartModelList.add(new AddedCartModel(new Date().toString(),R.drawable.helicopter,"Azimut Yacht 48 ft ","AED 1,200.00","6","AED 4,800.00"));
        addedCartModelList.add(new AddedCartModel(new Date().toString(),R.drawable.helicopter,"Azimut Yacht 48 ft ","AED 1,200.00","6","AED 4,800.00"));


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        addedCartRecyclerView.setLayoutManager(linearLayoutManager);

        AddedCartAdapter addedCartAdapter = new AddedCartAdapter(this,addedCartModelList);
        addedCartRecyclerView.setAdapter(addedCartAdapter);
    }
}
