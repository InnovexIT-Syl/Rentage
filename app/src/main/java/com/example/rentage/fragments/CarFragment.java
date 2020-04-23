package com.example.rentage.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.rentage.R;
import com.example.rentage.adapter.FeaturedDealsAdapter;
import com.example.rentage.model.FeaturedDealsModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CarFragment extends Fragment {

    RecyclerView carRecyclerview;
    List<FeaturedDealsModel> featuredDealsModelList = new ArrayList<>();

    String[] filterArray,sortByArray;
    private Spinner spinnerFilter, spinnerSortBy;
    ArrayAdapter<String> adapter;

    public CarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_car, container, false);

        spinnerFilter = view.findViewById(R.id.filterSpinnerID);
        spinnerSortBy = view.findViewById(R.id.sortBySpinnerID);

        carRecyclerview = view.findViewById(R.id.car_recyclerview);

        implementSpinnerFilter();
        implementSpinnerSortBy();
        loadCar();
        return view;

    }

    private void implementSpinnerFilter() {

        filterArray = new String[]{"All products", "Cars", "Mercedes", "Ferrari", "Range Rover"};
        adapter = new ArrayAdapter<>(Objects.requireNonNull(getActivity()), R.layout.sample_view, R.id.sampleTextId, filterArray);
        spinnerFilter.setAdapter(adapter);

        spinnerFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "" + filterArray[position] + " is Selected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void implementSpinnerSortBy() {
        sortByArray = new String[]{"Price, low to high","Price, high to low" ,"Alphabetically,A-Z","Alphabetically,Z-A","Featured", "Best selling", "Date,old to new", "Date,new to old"};
        adapter = new ArrayAdapter<>(Objects.requireNonNull(getActivity()), R.layout.sample_view, R.id.sampleTextId, sortByArray);
        spinnerSortBy.setAdapter(adapter);

        spinnerSortBy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "" + sortByArray[position] + " is Selected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void loadCar() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        carRecyclerview.setLayoutManager(gridLayoutManager);

        featuredDealsModelList.add(new FeaturedDealsModel(R.drawable.helicopter, "Mercedes G " +
                "Class", 3150.00));
        featuredDealsModelList.add(new FeaturedDealsModel(R.drawable.motor, "Mercedes G " +
                "Class", 3150.00));
        featuredDealsModelList.add(new FeaturedDealsModel(R.drawable.yachts, "Mercedes G " +
                "Class", 3150.00));
        featuredDealsModelList.add(new FeaturedDealsModel(R.drawable.helicopter, "Mercedes G " +
                "Class", 3150.00));
        featuredDealsModelList.add(new FeaturedDealsModel(R.drawable.yachts, "Mercedes G " +
                "Class", 3150.00));
        featuredDealsModelList.add(new FeaturedDealsModel(R.drawable.helicopter, "Mercedes G " +
                "Class", 3150.00));
        featuredDealsModelList.add(new FeaturedDealsModel(R.drawable.yachts, "Mercedes G " +
                "Class", 3150.00));
        featuredDealsModelList.add(new FeaturedDealsModel(R.drawable.helicopter, "Mercedes G " +
                "Class", 3150.00));

        FeaturedDealsAdapter adapter = new FeaturedDealsAdapter(getActivity(),
                featuredDealsModelList);
        carRecyclerview.setAdapter(adapter);
    }

}