package com.example.rentage.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentage.R;
import com.example.rentage.adapter.BookingModelAdapter;
import com.example.rentage.model.BookingModel;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView bookingRecyclerView;
    private List<BookingModel> bookingModelList = new ArrayList<>();
    private String[] searchList = {"Car", "Ship", "Helicopter", "Boat"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.page_home, container, false);

        bookingRecyclerView = rootView.findViewById(R.id.booking_recycleview);

        bookingDeals();
        return rootView;
    }

    private void bookingDeals() {
        bookingModelList.add(new BookingModel(new Date().toString(), R.drawable.motor,
                R.string.motors, R.string.motor_text));
        bookingModelList.add(new BookingModel(new Date().toString(), R.drawable.yachts,
                R.string.yachts, R.string.yachits_text));
        bookingModelList.add(new BookingModel(new Date().toString(), R.drawable.helicopter,
                R.string.helicopter_rides, R.string.helicopter_text));
        bookingModelList.add(new BookingModel(new Date().toString(), R.drawable.motor,
                R.string.motors, R.string.motor_text));
        bookingModelList.add(new BookingModel(new Date().toString(), R.drawable.yachts,
                R.string.yachts, R.string.yachits_text));
        bookingModelList.add(new BookingModel(new Date().toString(), R.drawable.helicopter,
                R.string.helicopter_rides, R.string.helicopter_text));

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        bookingRecyclerView.setLayoutManager(layoutManager);
        BookingModelAdapter adapter = new BookingModelAdapter(getContext(), bookingModelList);
        bookingRecyclerView.setAdapter(adapter);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        MenuItem item = menu.findItem(R.id.search);
        super.onCreateOptionsMenu(menu, inflater);
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

}
