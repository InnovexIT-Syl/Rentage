package com.example.rentage.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentage.AddToCartActivity;
import com.example.rentage.AddedCartActivity;
import com.example.rentage.R;
import com.example.rentage.adapter.BookingModelAdapter;
import com.example.rentage.model.BookingModel;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class HomeFragment extends Fragment {

    private RecyclerView bookingRecyclerView;
    private List<BookingModel> bookingModelList = new ArrayList<>();
    private String[] searchList = {"Car", "Ship", "Helicopter", "Boat"};
    private PopupWindow popupWindow;
    RelativeLayout relativeLayout;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.page_home, container, false);

        bookingRecyclerView = rootView.findViewById(R.id.booking_recycleview);
        relativeLayout = rootView.findViewById(R.id.relative_layout);
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
}
