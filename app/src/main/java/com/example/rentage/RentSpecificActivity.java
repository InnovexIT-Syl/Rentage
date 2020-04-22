package com.example.rentage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
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

        getSupportActionBar().setTitle("");

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.tab_viewpager);

        setUpViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        tabActions(tabLayout, viewPager);

    }

    static void tabActions(TabLayout tabLayout, final ViewPager viewPager) {
        tabLayout.setupWithViewPager(viewPager);


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
