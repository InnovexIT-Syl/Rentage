package com.example.rentage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.rentage.adapter.BookingAndFeaturedViewPagerAdapter;
import com.example.rentage.adapter.RentSpecificViewPagerAdapter;
import com.example.rentage.fragments.CarFragment;
import com.example.rentage.fragments.HelicopterFragment;
import com.example.rentage.fragments.YchtsFragment;
import com.glide.slider.library.tricks.ViewPagerEx;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RentSpecificActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbarCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_specific);

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

        getSupportActionBar().setTitle("Make your choice");

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.tab_viewpager);

        setUpViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        tabActions(tabLayout, viewPager);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation_view, menu);
        menu.findItem(R.id.about).setVisible(true);
        menu.findItem(R.id.nav_home).setVisible(false);
        menu.findItem(R.id.settings).setVisible(true);
        menu.findItem(R.id.login_sign_in).setVisible(false);
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
    static void tabActions(TabLayout tabLayout, final ViewPager viewPager) {
        tabLayout.setupWithViewPager(viewPager);

        Objects.requireNonNull(tabLayout.getTabAt(0)).setIcon(R.drawable.ic_car);
        Objects.requireNonNull(tabLayout.getTabAt(1)).setIcon(R.drawable.icon_helicopter);
        Objects.requireNonNull(tabLayout.getTabAt(2)).setIcon(R.drawable.icon_ship_);

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

    void setUpViewPager(ViewPager viewPager) {
        RentSpecificViewPagerAdapter viewPageAdapter = new RentSpecificViewPagerAdapter(getSupportFragmentManager());
        viewPageAdapter.addMyFragments(new CarFragment(), "Car");
        viewPageAdapter.addMyFragments(new HelicopterFragment(), "Helicopter");
        viewPageAdapter.addMyFragments(new YchtsFragment(), "Ychts");
        viewPager.setAdapter(viewPageAdapter);
    }

}
