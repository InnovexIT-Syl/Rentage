package com.example.rentage.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
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
import com.example.rentage.adapter.BookingModelAdapter;
import com.example.rentage.adapter.CartAdapter;
import com.example.rentage.adapter.FeaturedDealsAdapter;
import com.example.rentage.model.BookingModel;
import com.example.rentage.model.FeaturedDealsModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

        filterArray = new String[]{"All products", "Mercedes", "Ferrari", "Range Rover"};
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
        sortByArray = new String[]{"Price, low to high","Price, high to low", "Date,old to new", "Date,new to old"};
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

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Services");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    FeaturedDealsModel featuredDealsModel = ds.getValue(FeaturedDealsModel.class);
                    featuredDealsModelList.add(featuredDealsModel);
                }

                FeaturedDealsAdapter adapter = new FeaturedDealsAdapter(getContext(), featuredDealsModelList);
                carRecyclerview.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        FeaturedDealsAdapter adapter = new FeaturedDealsAdapter(getActivity(),
                featuredDealsModelList);
        carRecyclerview.setAdapter(adapter);
    }
}
