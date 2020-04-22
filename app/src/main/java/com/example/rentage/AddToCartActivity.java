package com.example.rentage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.rentage.adapter.FeaturedDealsAdapter;
import com.example.rentage.model.FeaturedDealsModel;

import java.util.ArrayList;
import java.util.List;

public class AddToCartActivity extends AppCompatActivity {

    private Toolbar toolbarCart;
    List<FeaturedDealsModel> featuredDealsModelList = new ArrayList<>();
    private RecyclerView featuredDealsRecyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_cart);

        initialize();
    }

    private void initialize() {

        toolbarCart = findViewById(R.id.toolbar_home);
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
        featuredDealsRecyclerview = findViewById(R.id.may_like_recyclerview);
        mayAlsoLike();
    }

    private void mayAlsoLike() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        featuredDealsRecyclerview.setLayoutManager(gridLayoutManager);

        featuredDealsModelList.add(new FeaturedDealsModel(R.drawable.helicopter, "Mercedes G " +
                "Class", 3150.00));
        featuredDealsModelList.add(new FeaturedDealsModel(R.drawable.motor, "Mercedes G " +
                "Class", 3150.00));
        featuredDealsModelList.add(new FeaturedDealsModel(R.drawable.yachts, "Mercedes G " +
                "Class", 3150.00));
        featuredDealsModelList.add(new FeaturedDealsModel(R.drawable.helicopter, "Mercedes G " +
                "Class", 3150.00));

        FeaturedDealsAdapter adapter = new FeaturedDealsAdapter(this,
                featuredDealsModelList);
        featuredDealsRecyclerview.setAdapter(adapter);
    }
}
