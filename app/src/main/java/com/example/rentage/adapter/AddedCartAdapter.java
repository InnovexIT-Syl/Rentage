package com.example.rentage.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentage.R;
import com.example.rentage.model.AddedCartModel;

import java.util.List;

public class AddedCartAdapter extends RecyclerView.Adapter<AddedCartAdapter.AddedCartHolder> {
    private Context context;
    private List<AddedCartModel> addedCartModelList;

    public AddedCartAdapter(Context context, List<AddedCartModel> addedCartModelList) {
        this.context = context;
        this.addedCartModelList = addedCartModelList;
    }

    @NonNull
    @Override
    public AddedCartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_added_cart, parent, false);
        return new AddedCartHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddedCartAdapter.AddedCartHolder holder, int position) {
       holder.cartImage.setImageResource(addedCartModelList.get(position).getCartImage());
       holder.cartModelName.setText(addedCartModelList.get(position).getCartModelName());
       holder.cost.setText(addedCartModelList.get(position).getCost());
       holder.totalCost.setText(addedCartModelList.get(position).getTotalCost());

       holder.removeCart.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

           }
       });
    }

    @Override
    public int getItemCount() {
        return addedCartModelList.size();
    }

    public static class AddedCartHolder extends RecyclerView.ViewHolder {

        private ImageView cartImage;
        private TextView cartModelName;
        private TextView cost;
        private Button quantity;
        private Button removeCart;
        private TextView totalCost;
        public AddedCartHolder(@NonNull View itemView) {
            super(itemView);
            cartImage = itemView.findViewById(R.id.added_cart_image);
            cartModelName = itemView.findViewById(R.id.added_cart_image_name);
            cost = itemView.findViewById(R.id.added_cart_cost);
            //quantity = itemView.findViewById(R.id.quantity_number_button);
            removeCart = itemView.findViewById(R.id.cart_remove_button);
            totalCost = itemView.findViewById(R.id.rent_cart_total_cost);
        }
    }
}
