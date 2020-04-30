package com.example.rentage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.rentage.adapter.CartAdapter;
import com.example.rentage.adapter.FeaturedDealsAdapter;
import com.example.rentage.model.CartModel;
import com.example.rentage.model.FeaturedDealsModel;

import java.util.ArrayList;
import java.util.List;

public class AddToCartActivity extends AppCompatActivity {

    private Toolbar toolbarCart;
    private List<FeaturedDealsModel> featuredDealsModelList = new ArrayList<>();
    private List<CartModel> cartModelList = new ArrayList<>();
    private RecyclerView featuredDealsRecyclerview, cart_recycler_view;
    Button addToCartButton, byeItNow;
    private PopupWindow popupWindow;
    private LinearLayout linearLayout;
    private View customView;
    private TextView haveCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_cart);

        initialize();
    }

    private void initialize() {

        toolbarCart = findViewById(R.id.toolbar_add_to_cart);
        addToCartButton = findViewById(R.id.addToCartButton);
        byeItNow = findViewById(R.id.byeItNowButton);
        linearLayout = findViewById(R.id.linear_layout);

        setSupportActionBar(toolbarCart);
        toolbarCart.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbarCart.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        getSupportActionBar().setTitle("Add on your cart");
        featuredDealsRecyclerview = findViewById(R.id.may_like_recyclerview);
        initCustomView();
        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCart();
                showCartWindow();
            }
        });
        byeItNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), InfoCheckoutActivity.class));
            }
        });

        // cart_recycler_view = findViewById(R.id.cart_recycler_view);
        mayAlsoLike();
    }

    private void initCustomView() {
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        customView = inflater.inflate(R.layout.custom_cart_pop_up, null);
        haveCart = customView.findViewById(R.id.haveCart);
        cart_recycler_view = customView.findViewById(R.id.cart_recycler_view);
    }

    private void addCart() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        cart_recycler_view.setLayoutManager(layoutManager);
        cartModelList.add(new CartModel(R.drawable.helicopter, "Mercedes G Class", "Qty : 1"));
        cartModelList.add(new CartModel(R.drawable.helicopter, "Mercedes G Class", "Qty : 1"));
        cartModelList.add(new CartModel(R.drawable.helicopter, "Mercedes G Class", "Qty : 1"));

        CartAdapter adapter = new CartAdapter(getApplicationContext(),
                cartModelList);
        cart_recycler_view.setAdapter(adapter);
        cart_recycler_view.setHasFixedSize(true);
    }

    private void showCartWindow() {

        if (cartModelList.isEmpty()) {
            haveCart.setVisibility(View.VISIBLE);
        } else {
            haveCart.setVisibility(View.GONE);
            cart_recycler_view.setVisibility(View.VISIBLE);
        }
        Button continueShopping = customView.findViewById(R.id.continue_shopping);
        Button viewYourCart = customView.findViewById(R.id.view_cart);

        continueShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddToCartActivity.class));
            }
        });
        viewYourCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddedCartActivity.class));
                finish();
            }
        });

        popupWindow = new PopupWindow(
                customView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );

        popupWindow.setAnimationStyle(R.style.pop_up_window_animation);

        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.update(0, 0, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (Build.VERSION.SDK_INT >= 21) {
            popupWindow.setElevation(5.0f);
        }

        ImageButton closeButton = customView.findViewById(R.id.button_close);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
        popupWindow.showAtLocation(linearLayout, Gravity.TOP, 0, 130);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed(); // go to previous page
        return super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        menu.findItem(R.id.shopping).setVisible(true);
        menu.findItem(R.id.search).setVisible(false);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                return true;
            case R.id.shopping:
                //addCart();
                showCartWindow();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
