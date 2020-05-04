package com.example.rentage;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.ViewGroup.LayoutParams;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.request.RequestOptions;

import com.example.rentage.adapter.BookingAndFeaturedViewPagerAdapter;
import com.example.rentage.fragments.FeaturedFragment;
import com.example.rentage.fragments.HomeFragment;

import com.glide.slider.library.SliderLayout;
import com.glide.slider.library.animations.DescriptionAnimation;
import com.glide.slider.library.slidertypes.BaseSliderView;
import com.glide.slider.library.slidertypes.TextSliderView;
import com.glide.slider.library.tricks.ViewPagerEx;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener,
        ViewPagerEx.OnPageChangeListener {

    ViewPager viewPager;
    private Toolbar toolbarHome;
    private SliderLayout slider;
    private PopupWindow popupWindow;
    RelativeLayout relativeLayout;

    private DrawerLayout drawerLayout;
    private View content;
    private NavigationView navigationView;
    private ActionBarDrawerToggle drawerToggle;
    private String uid = null;

    // firebase
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    // storage
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
    }

    private void initialize() {

        toolbarHome = findViewById(R.id.toolbar_home);
        viewPager = findViewById(R.id.tab_viewpager);
        relativeLayout = findViewById(R.id.relative_layout);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);

        setSupportActionBar(toolbarHome);
        toolbarHome.setTitleTextColor(Color.WHITE);
//        toolbarHome.setTitle("Home");
        getSupportActionBar().setTitle("Home");
        slider = findViewById(R.id.slider);


        // init firebase
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");
        storageReference = FirebaseStorage.getInstance().getReference(); // firebase storage reference

        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (viewPager != null) {
            setupViewPager(viewPager);
        }


        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabActions(tabLayout, viewPager);

        setupDrawerLayout();
        drawerToggle = setupDrawerToggle();

        slider();
        checkUserStatus();

        if (uid != null) {

            final TextView userName = navigationView.getHeaderView(0).findViewById(R.id.username);
            final TextView userEmail = navigationView.getHeaderView(0).findViewById(R.id.userEmail);
            final CircleImageView userProfileImage = navigationView.getHeaderView(0).findViewById(R.id.profileImage);


            ImageButton editButton = navigationView.getHeaderView(0).findViewById(R.id.edit_Button);
            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                }
            });
            // databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

            Query query = databaseReference.orderByChild("email").equalTo(firebaseUser.getEmail());
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    // checks until required data got
                    for (DataSnapshot data_snapshot : dataSnapshot.getChildren()) {

                        // get data
                        String name = data_snapshot.child("name").getValue().toString();
                        String email = data_snapshot.child("email").getValue().toString();
                        String profileImage = data_snapshot.child("profile_image").getValue().toString();

                        // set data
                        userName.setText(name);
                        userEmail.setText(email);


                        // for profile photo
                        try {
                            // if image is received then set
                            Picasso.get().load(profileImage).into(userProfileImage);
                        } catch (Exception e) {
                            // if there is any exception while setting image then set default
                            Picasso.get().load(R.drawable.index).into(userProfileImage);
                        }

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }

    }


    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, drawerLayout, toolbarHome, R.string.drawer_open, R.string.drawer_close);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }

    private void setupDrawerLayout() {

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.nav_home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        break;
                    case R.id.login_sign_in:
                        startActivity(new Intent(getApplicationContext(), AuthenticationActivity.class));
                        finish();
                        break;
                    case R.id.about:
                        startActivity(new Intent(getApplicationContext(), AboutActivity.class));
                        finish();
                        break;
                    case R.id.logout:
                        firebaseAuth.signOut();
                        Toast.makeText(MainActivity.this, "You are logout successfully.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                        break;
                }
                return true;
            }
        });
    }

    static void tabActions(TabLayout tabLayout, final ViewPager viewPager) {
        tabLayout.setupWithViewPager(viewPager);

        Objects.requireNonNull(tabLayout.getTabAt(0)).setIcon(R.drawable.ic_home);
        Objects.requireNonNull(tabLayout.getTabAt(1)).setIcon(R.drawable.icon_all_featured);

        Objects.requireNonNull(tabLayout.getTabAt(0)).setText("HOME");
        Objects.requireNonNull(tabLayout.getTabAt(1)).setText("FEATURED");

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                viewPager.getVerticalScrollbarPosition();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        BookingAndFeaturedViewPagerAdapter adapter = new BookingAndFeaturedViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new HomeFragment(), "0");
        adapter.addFrag(new FeaturedFragment(), "1");
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);

//        menu.findItem(R.id.search).setVisible(true);
//        menu.findItem(R.id.shopping).setVisible(true);

        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.shopping:
                // Initialize a new instance of LayoutInflater service
                LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);

                // Inflate the custom layout/view
                View customView = inflater.inflate(R.layout.custom_cart_pop_up, null);
                Button continueShopping = customView.findViewById(R.id.continue_shopping);
                Button viewYourCart = customView.findViewById(R.id.view_cart);

                continueShopping.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getApplicationContext(), AddToCartActivity.class));
                        finish();
                    }
                });
                viewYourCart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getApplicationContext(), AddedCartActivity.class));
                        finish();
                    }
                });
//                customView.setAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_in));
                popupWindow = new PopupWindow(
                        customView,
                        LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT
                );
                popupWindow.setAnimationStyle(R.style.pop_up_window_animation);

                popupWindow.setFocusable(true);
                popupWindow.setOutsideTouchable(true);
                popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                popupWindow.update(0, 0, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
                if (Build.VERSION.SDK_INT >= 21) {
                    popupWindow.setElevation(5.0f);
                }

                ImageButton closeButton = customView.findViewById(R.id.button_close);
                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Dismiss the popup window
                        popupWindow.dismiss();
                    }
                });

                popupWindow.showAtLocation(relativeLayout, Gravity.CENTER_VERTICAL, 0, 0);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @SuppressLint("CheckResult")
    private void slider() {
        ArrayList<Integer> listImage = new ArrayList<>();
        ArrayList<String> listName = new ArrayList<>();

        listImage.add(R.drawable.rent_motor);
        listName.add("Mercedes G Class");
        listImage.add(R.drawable.helicopter);
        listName.add("Mercedes G Class");
        listImage.add(R.drawable.motor);
        listName.add("Mercedes G Class");

        listImage.add(R.drawable.yachts);
        listName.add("Mercedes G Class");

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.centerCrop();

        for (int i = 0; i < listImage.size(); i++) {
            TextSliderView sliderView = new TextSliderView(this);
            // if you want show image only / without description text use DefaultSliderView instead
            sliderView
                    .image(listImage.get(i))
                    .description(listName.get(i))
                    .setRequestOption(requestOptions)
                    .setProgressBarVisible(true)
                    .setOnSliderClickListener(this);

            //add your extra information
            sliderView.bundle(new Bundle());
            sliderView.getBundle().putString("extra", listName.get(i));
            slider.addSlider(sliderView);
        }

        // set Slider Transition Animation
        slider.setPresetTransformer(SliderLayout.Transformer.Stack);

        slider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        slider.setCustomAnimation(new DescriptionAnimation());
        slider.setDuration(3000);
        slider.addOnPageChangeListener(this);
        slider.stopCyclingWhenTouch(false);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        slider.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        Toast.makeText(this, slider.getBundle().getString("extra") + "", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void checkUserStatus() {
        // get current user

        if (firebaseUser != null) {
            // user is signed in stay here
            // set email of logged in user
            // emailText.setText(user.getEmail());
            uid = firebaseUser.getUid();
        }
    }

}
