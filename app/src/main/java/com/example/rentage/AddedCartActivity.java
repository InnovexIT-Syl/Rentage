package com.example.rentage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.rentage.adapter.AddedCartAdapter;
import com.example.rentage.model.AddedCartModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddedCartActivity extends AppCompatActivity {

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

        getSupportActionBar().setTitle("All cart");
        addedCart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();

        menuInflater.inflate(R.menu.sub_menu, menu);
        menu.findItem(R.id.about).setVisible(false);
        menu.findItem(R.id.settings).setVisible(true);
        menu.findItem(R.id.logout).setVisible(true);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                startActivity(new Intent(getApplicationContext(),AboutActivity.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
