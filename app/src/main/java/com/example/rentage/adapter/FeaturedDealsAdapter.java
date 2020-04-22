package com.example.rentage.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentage.R;
import com.example.rentage.model.FeaturedDealsModel;

import java.util.ArrayList;
import java.util.List;

public class FeaturedDealsAdapter extends RecyclerView.Adapter<FeaturedDealsAdapter.FeaturedDealsHolder> {

//    List<Integer> featuredImageList = new ArrayList<>();
//    List<String> featuredNameList = new ArrayList<>();
//    List<String> featuredPriceList = new ArrayList<>();
//    private Context context;
//
//    public FeaturedDealsAdapter(Context applicationContext, List<String> featuredNameList, List<String> featuredPriceList, List<Integer> featuredImageList) {
//        this.featuredNameList = featuredNameList;
//        this.featuredPriceList = featuredPriceList;
//        this.featuredImageList = featuredImageList;
//        this.context = applicationContext;
//    }
    private List<FeaturedDealsModel> featuredDealsList;
    private Context context;

    public FeaturedDealsAdapter(Context context, List<FeaturedDealsModel> featuredDealsList) {
        this.context = context;
     this.featuredDealsList = featuredDealsList;

    }


    @NonNull
    @Override
    public FeaturedDealsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_featured_deals, viewGroup, false);

        return new FeaturedDealsHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull FeaturedDealsHolder holder, int position) {
       holder.featuredImage.setImageResource(featuredDealsList.get(position).getFeaturedImage());
       holder.featuredName.setText(featuredDealsList.get(position).getFeaturedName());
       holder.featuredCost.setText("AED "+ featuredDealsList.get(position).getFeaturedPrice());
    }

    @Override
    public int getItemCount() {
        return featuredDealsList.size();
    }

    static class FeaturedDealsHolder extends RecyclerView.ViewHolder {
        private ImageView featuredImage;
        private TextView featuredName;
        private TextView featuredCost;
        FeaturedDealsHolder(@NonNull View itemView) {
            super(itemView);

            featuredImage = itemView.findViewById(R.id.rent_car_image);
            featuredName = itemView.findViewById(R.id.rent_car_name);
            featuredCost = itemView.findViewById(R.id.rent_car_cost);
        }
    }
}
