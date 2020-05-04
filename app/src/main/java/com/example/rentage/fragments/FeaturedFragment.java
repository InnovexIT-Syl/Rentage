package com.example.rentage.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentage.R;
import com.example.rentage.adapter.FeaturedDealsAdapter;
import com.example.rentage.model.FeaturedDealsModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Services");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    FeaturedDealsModel featuredDealsModel = ds.getValue(FeaturedDealsModel.class);
                    featuredDealsModelList.add(featuredDealsModel);
                }

                FeaturedDealsAdapter adapter = new FeaturedDealsAdapter(getContext(), featuredDealsModelList);
                featuredDealsRecyclerview.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        FeaturedDealsAdapter adapter = new FeaturedDealsAdapter(getContext(),
                featuredDealsModelList);
        featuredDealsRecyclerview.setAdapter(adapter);
    }

    private void searchFeatured(String searchQuery){

    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) menuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (!TextUtils.isEmpty(s)){
                    searchFeatured(s);

                }else {
                 featuredDeals();
                }
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
