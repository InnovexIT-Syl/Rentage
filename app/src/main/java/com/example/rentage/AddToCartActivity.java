package com.example.rentage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rentage.adapter.CartAdapter;
import com.example.rentage.adapter.FeaturedDealsAdapter;
import com.example.rentage.model.CartModel;
import com.example.rentage.model.FeaturedDealsModel;
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

import java.util.ArrayList;
import java.util.List;

public class AddToCartActivity extends AppCompatActivity {

    private Toolbar toolbarCart;
    private List<CartModel> cartModelList = new ArrayList<>();
    private RecyclerView pop;
    Button addToCartButton, byeItNow;
    private PopupWindow popupWindow;
    private LinearLayout linearLayout;
    private View customView;
    private TextView haveCart;
    EditText quantity;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    ImageView add_to_cart_image;
    TextView title, price, featured_description;
    String uid;
    String add_cart_image_url;
    Integer thePrice;
    ProgressDialog progressDialog;
    Button continueShopping, viewYourCart;

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
        quantity = findViewById(R.id.quantity);

        title = findViewById(R.id.add_to_cart_title);
        price = findViewById(R.id.add_to_cart_price);
        featured_description = findViewById(R.id.featured_description);

        add_to_cart_image = findViewById(R.id.add_cart_image);
        add_to_cart_image = findViewById(R.id.add_cart_image);
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        progressDialog = new ProgressDialog(this);
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

        initCustomView();
        checkUserStatus();
        showSelectedFeatured();

        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSelectedFeatured();
            }
        });
        byeItNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), InfoCheckoutActivity.class));
            }
        });
    }

    private void showSelectedFeatured() {
        Intent intent = getIntent();
        String id = intent.getStringExtra("ID");
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Services");
        Query query = databaseReference.orderByChild("id").equalTo(id);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    String p = ds.child("price").getValue().toString();
                    String d = ds.child("description").getValue().toString();
                    String t = ds.child("title").getValue().toString();
                    String imageUrl = ds.child("imageUrl").getValue().toString();
                    add_cart_image_url = imageUrl;
                    thePrice = Integer.parseInt(p);

                    title.setText(t);
                    featured_description.setText(d);
                    price.setText("AED " + p);

                    add_to_cart_image.setImageURI(Uri.parse(imageUrl));
//                    try {
//                        Picasso.get().load(imageUrl).into(add_to_cart_image);
//                    } catch (Exception e) {
//                        Toast.makeText(AddToCartActivity.this, "Image does not loaded successfully", Toast.LENGTH_SHORT).show();
//                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addSelectedFeatured() {
        if (quantity.getText().toString().equals("")){
            quantity.setError("Give your quantity value");
            return;
        }
        progressDialog.setMessage("Added card..");
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
                        String carAddedById = uid;

                        databaseReference = FirebaseDatabase.getInstance().getReference();
                        final DatabaseReference dbRef = databaseReference.child("added_cart").push();
                        final String key = dbRef.getKey();
                        Integer totalPrice = thePrice * Integer.parseInt(quantity.getText().toString());
                        CartModel cartModel = new CartModel(key, title.getText().toString(), add_cart_image_url, quantity.getText().toString(), email, carAddedById, String.valueOf(thePrice), String.valueOf(totalPrice));

                        dbRef.setValue(cartModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                progressDialog.dismiss();
                                Toast.makeText(AddToCartActivity.this, "Add to cart successfully uploaded",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(AddToCartActivity.this, "Add to cart upload failed!" + e.toString(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        } else {
            startActivity(new Intent(getApplicationContext(), AuthenticationActivity.class));
        }
    }


    private void initCustomView() {
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        customView = inflater.inflate(R.layout.custom_cart_pop_up, null);
        haveCart = customView.findViewById(R.id.haveCart);
        pop = customView.findViewById(R.id.pop_recyclerView);
        continueShopping = customView.findViewById(R.id.continue_shopping);
        viewYourCart = customView.findViewById(R.id.view_cart);
    }


    private void showCartRecyclerView() {
        pop.setVisibility(View.VISIBLE);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        pop.setLayoutManager(layoutManager);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("added_cart");
        Query query = databaseReference.orderByChild("cartOrderById").equalTo(uid);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    CartModel cartModel = ds.getValue(CartModel.class);
                    cartModelList.add(cartModel);
                }
                viewYourCart.setText("VIEW CART (" + cartModelList.size() + ")");
                if (cartModelList.isEmpty()) {
                    haveCart.setVisibility(View.VISIBLE);
                    pop.setVisibility(View.GONE);
                }
                CartAdapter adapter = new CartAdapter(getApplicationContext(),
                        cartModelList);


                pop.setAdapter(adapter);
                pop.setHasFixedSize(true);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void showCartWindow() {
        if (!cartModelList.isEmpty()) {
            haveCart.setVisibility(View.GONE);
        }
        continueShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        viewYourCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddToCartActivity.this, AddedCartActivity.class));
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
        cartModelList.clear();
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
                showCartRecyclerView();
                showCartWindow();
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
