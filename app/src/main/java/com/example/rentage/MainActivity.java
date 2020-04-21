package com.example.rentage;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.bumptech.glide.request.RequestOptions;
import com.example.rentage.adapter.FeaturedDealsAdapter;
import com.glide.slider.library.SliderLayout;
import com.glide.slider.library.animations.DescriptionAnimation;
import com.glide.slider.library.slidertypes.BaseSliderView;
import com.glide.slider.library.slidertypes.TextSliderView;
import com.glide.slider.library.tricks.ViewPagerEx;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener,
        ViewPagerEx.OnPageChangeListener {

    private Toolbar toolbarHome;
    private RecyclerView featuredDealsRecyclerview;

    List<Integer> featuredImageList = new ArrayList<>();
    List<String> featuredNameList = new ArrayList<>();
    List<String> featuredPriceList = new ArrayList<>();

    private SliderLayout slider;

    MaterialSearchView searchView;
    FrameLayout frameLayout;

    String[] searchList = new String[]{"Motor", "Helicopter", "Car", "sheep", "Motorcycle"};
    List<Integer> slideImageList = new ArrayList<>();
    List<String> slideNameList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
    }

    private void initialize() {

        toolbarHome = findViewById(R.id.toolbar_home);
        featuredDealsRecyclerview = findViewById(R.id.featured_deals_recyclerview);
        frameLayout = findViewById(R.id.frame_layout);
        searchView = findViewById(R.id.search_view);


//
//        View view = getLayoutInflater().inflate(R.layout.activity_main,null,false);
//        frameLayout.addView(view);
        //  LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        frameLayout.setLayoutParams(layoutParams);


        setSupportActionBar(toolbarHome);
//        toolbarHome.setTitle("Home");
        toolbarHome.setTitleTextColor(Color.BLACK);
        getSupportActionBar().setTitle("Home");
        slider = findViewById(R.id.slider);
        featuredDeals();
        slider();
        searchViewCode();
    }

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
        slider.setDuration(4000);
        slider.addOnPageChangeListener(this);
        slider.stopCyclingWhenTouch(false);
    }

    private void searchViewCode() {

        searchView.setCursorDrawable(R.drawable.custom_cursor);
        // searchView.setSuggestions(getResources().getStringArray(R.array.query_suggestions));
        searchView.setSuggestions(searchList);
        searchView.setEllipsize(true);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(getApplicationContext(), query, Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
            }

            @Override
            public void onSearchViewClosed() {
                setSupportActionBar(toolbarHome);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem item = menu.findItem(R.id.search);
        searchView.setMenuItem(item);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }

    private void featuredDeals() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        featuredDealsRecyclerview.setLayoutManager(gridLayoutManager);

        featuredImageList.add(R.drawable.helicopter);
        featuredImageList.add(R.drawable.motor);
        featuredImageList.add(R.drawable.yachts);
        featuredImageList.add(R.drawable.helicopter);

        featuredNameList.add("Mercedes G Class");
        featuredNameList.add("Mercedes G Class");
        featuredNameList.add("Mercedes G Class");
        featuredNameList.add("Mercedes G Class");

        featuredPriceList.add("AED 3150.00");
        featuredPriceList.add("AED 3150.00");
        featuredPriceList.add("AED 3150.00");
        featuredPriceList.add("AED 3150.00");

        featuredDealsRecyclerview.setAdapter(new FeaturedDealsAdapter(getApplicationContext(), featuredNameList, featuredPriceList, featuredImageList));

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
