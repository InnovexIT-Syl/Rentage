package com.example.rentage.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.rentage.R;
import com.example.rentage.adapter.FeaturedDealsAdapter;
import com.example.rentage.model.FeaturedDealsModel;

import java.util.ArrayList;
import java.util.List;

public class FeaturedFragment extends Fragment {
    private RecyclerView featuredDealsRecyclerview;
    private List<FeaturedDealsModel> featuredDealsModelList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.page_featured, container, false);

        featuredDealsRecyclerview = rootView.findViewById(R.id.featured_deals_recyclerview);

        featuredDeals();
        return rootView;
    }

    private void featuredDeals() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        featuredDealsRecyclerview.setLayoutManager(gridLayoutManager);

        featuredDealsModelList.add(new FeaturedDealsModel(R.drawable.helicopter, "Mercedes G " +
                "Class", 3150.00));
        featuredDealsModelList.add(new FeaturedDealsModel(R.drawable.motor, "Mercedes G " +
                "Class", 3150.00));
        featuredDealsModelList.add(new FeaturedDealsModel(R.drawable.yachts, "Mercedes G " +
                "Class", 3150.00));
        featuredDealsModelList.add(new FeaturedDealsModel(R.drawable.helicopter, "Mercedes G " +
                "Class", 3150.00));
        featuredDealsRecyclerview.setLayoutManager(gridLayoutManager);

        featuredDealsModelList.add(new FeaturedDealsModel(R.drawable.helicopter, "Mercedes G " +
                "Class", 3150.00));
        featuredDealsModelList.add(new FeaturedDealsModel(R.drawable.motor, "Mercedes G " +
                "Class", 3150.00));
        featuredDealsModelList.add(new FeaturedDealsModel(R.drawable.yachts, "Mercedes G " +
                "Class", 3150.00));
        featuredDealsModelList.add(new FeaturedDealsModel(R.drawable.helicopter, "Mercedes G " +
                "Class", 3150.00));

        FeaturedDealsAdapter adapter = new FeaturedDealsAdapter(getContext(),
                featuredDealsModelList);
        featuredDealsRecyclerview.setAdapter(adapter);
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search);
        menu.findItem(R.id.shopping).setVisible(false);
        SearchView searchView = (SearchView) menuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
    }

}
