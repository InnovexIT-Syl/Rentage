package com.example.rentage.adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentage.R;
import com.example.rentage.model.CartModel;
import com.squareup.picasso.Picasso;


import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartHolder> {
    private List<CartModel> cartModelList;
    private Context context;

    public CartAdapter(Context context, List<CartModel> cartModelList) {
        this.context = context;
        this.cartModelList = cartModelList;

    }

    @NonNull
    @Override
    public CartHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_cart, viewGroup, false);

        return new CartHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartHolder holder, int position) {
        try {
            Picasso.get().load(cartModelList.get(position).getCartImage()).placeholder(R.drawable.helicopter).into(holder.cartImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.cartName.setText(cartModelList.get(position).getCartTitle());
        holder.cartQuantity.setText("Qty : " + cartModelList.get(position).getCartQuantity());
    }

    @Override
    public int getItemCount() {
        return cartModelList.size();
    }

    static class CartHolder extends RecyclerView.ViewHolder {
        private ImageView cartImage;
        private TextView cartName;
        private TextView cartQuantity;

        CartHolder(@NonNull View itemView) {
            super(itemView);

            cartImage = itemView.findViewById(R.id.cart_image);
            cartName = itemView.findViewById(R.id.cart_name);
            cartQuantity = itemView.findViewById(R.id.cart_quantity);
        }
    }
}
