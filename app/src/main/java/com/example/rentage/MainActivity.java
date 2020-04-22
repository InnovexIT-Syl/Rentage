package com.example.rentage;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.request.RequestOptions;
import com.example.rentage.adapter.BookingModelAdapter;
import com.example.rentage.adapter.FeaturedDealsAdapter;
import com.example.rentage.adapter.ViewPagerAdapter;
import com.example.rentage.fragments.FeaturedFragment;
import com.example.rentage.fragments.HomeFragment;
import com.example.rentage.model.BookingModel;
import com.example.rentage.model.FeaturedDealsModel;
import com.glide.slider.library.SliderLayout;
import com.glide.slider.library.animations.DescriptionAnimation;
import com.glide.slider.library.slidertypes.BaseSliderView;
import com.glide.slider.library.slidertypes.TextSliderView;
import com.glide.slider.library.tricks.ViewPagerEx;
import com.google.android.material.tabs.TabLayout;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener,
        ViewPagerEx.OnPageChangeListener {

    ViewPager viewPager;
    private Toolbar toolbarHome;
    private SliderLayout slider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
    }

    private void initialize() {

        toolbarHome = findViewById(R.id.toolbar_home);
        viewPager = findViewById(R.id.tab_viewpager);


        setSupportActionBar(toolbarHome);
        toolbarHome.setTitleTextColor(Color.WHITE);
        toolbarHome.setTitle("Home");
        slider = findViewById(R.id.slider);

        if (viewPager != null) {
            setupViewPager(viewPager);
        }


        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabActions(tabLayout, viewPager);
        slider();
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


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new HomeFragment(), "Services");
        adapter.addFrag(new FeaturedFragment(), "Featured");
        viewPager.setAdapter(adapter);
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
        //.diskCacheStrategy(DiskCacheStrategy.NONE)
        //.placeholder(R.drawable.placeholder)
        //.error(R.drawable.placeholder);

        for (int i = 0; i < listImage.size(); i++) {
            TextSliderView sliderView = new TextSliderView(this);
            // if you want show image only / without description text use DefaultSliderView instead
            // initialize SliderLayout
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
        slider.setPresetTransformer(SliderLayout.Transformer.Foreground2Background);

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
}
