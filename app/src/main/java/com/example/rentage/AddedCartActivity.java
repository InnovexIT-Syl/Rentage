package com.example.rentage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.rentage.adapter.AddedCartAdapter;
import com.example.rentage.adapter.CartAdapter;
import com.example.rentage.model.AddedCartModel;
import com.example.rentage.model.CartModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddedCartActivity extends AppCompatActivity {

    private RecyclerView addedCartRecyclerView;
    private List<CartModel> cartModelList = new ArrayList<>();
    private Toolbar toolbarCart;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    String uid;
    SwipeRefreshLayout swipeRefreshLayout;
    AddedCartAdapter addedCartAdapter;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_added_cart);

//        this.handler = new Handler();
//        runnable.run();
        initialize();
    }

//    private final Runnable runnable = new Runnable() {
//        @Override
//        public void run() {
//           AddedCartActivity.this.handler.postDelayed(runnable,1000);
//        }
//    };

    private void initialize() {
        addedCartRecyclerView = findViewById(R.id.added_cart_recyclerView);
        toolbarCart = findViewById(R.id.toolbar_cart);

       // swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
//        swipeRefreshLayout.setOnRefreshListener(this);
        setSupportActionBar(toolbarCart);
        toolbarCart.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbarCart.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        checkUserStatus();
        getSupportActionBar().setTitle("All cart");

        addedCart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();

        menuInflater.inflate(R.menu.sub_menu, menu);
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void addedCart() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        addedCartRecyclerView.setLayoutManager(linearLayoutManager);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("added_cart");
        Query query = databaseReference.orderByChild("cartOrderById").equalTo(uid);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    CartModel cartModel = ds.getValue(CartModel.class);
                    cartModelList.add(cartModel);
                }
                addedCartAdapter = new AddedCartAdapter(AddedCartActivity.this, cartModelList);
//                addedCartRecyclerView.getRecycledViewPool().clear();
//                addedCartAdapter.notifyDataSetChanged();
                addedCartRecyclerView.setAdapter(addedCartAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        cartModelList.clear();
    }

    private void checkUserStatus() {
        if (firebaseUser != null) {
            uid = firebaseUser.getUid();
        }
    }

//    @Override
//    public void onRefresh() {
//        cartModelList.clear();
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                addedCart();
//                swipeRefreshLayout.setRefreshing(false);
//            }
//        }, 50);
//    }

}
