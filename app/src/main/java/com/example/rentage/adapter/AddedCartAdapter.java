package com.example.rentage.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import android.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentage.R;
import com.example.rentage.model.CartModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Vector;

public class AddedCartAdapter extends RecyclerView.Adapter<AddedCartAdapter.AddedCartHolder> {
    private Context context;
    private List<CartModel> cartModelList;
    FirebaseDatabase firebaseDatabase;
    ProgressDialog progressDialog;
    public AddedCartAdapter(Context context, List<CartModel> cartModelList) {
        this.context = context;
        this.cartModelList = cartModelList;
    }

    @NonNull
    @Override
    public AddedCartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_added_cart, parent, false);
        return new AddedCartHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AddedCartHolder holder, final int position) {
        try {
            Picasso.get().load(cartModelList.get(position).getCartImage()).into(holder.cartImage);
//            Picasso.get().load(cartModelList.get(position).getCartImage()).placeholder(R.drawable.helicopter).into(holder.cartImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.cartModelName.setText(cartModelList.get(position).getCartTitle());
        holder.cost.setText("AED " + cartModelList.get(position).getSingleCost());
        holder.totalCost.setText("AED " + cartModelList.get(position).getTotalCost());
        holder.quantity.setText(cartModelList.get(position).getCartQuantity());

        holder.removeCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // show delete message conform dialog
                AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                alertDialog.setTitle("Delete");
                alertDialog.setMessage("Are you sure to delete this cart?");

                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                             deleteCart(position,holder);
                            }
                        });
                alertDialog.show();
            }
        });

        holder.positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer quantity = Integer.parseInt(cartModelList.get(position).getCartQuantity());
                quantity++;
                cartModelList.get(position).setCartQuantity(String.valueOf(quantity));
                holder.quantity.setText(cartModelList.get(position).getCartQuantity());
                Integer singleCost = Integer.parseInt(cartModelList.get(position).getSingleCost());
                cartModelList.get(position).setTotalCost(String.valueOf(quantity * singleCost));
                holder.totalCost.setText("AED " + cartModelList.get(position).getTotalCost());
            }
        });

        holder.negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = Integer.parseInt(cartModelList.get(position).getCartQuantity());
                if (quantity == 1) {
                    cartModelList.get(position).setCartQuantity(String.valueOf(quantity));
                    holder.quantity.setText(cartModelList.get(position).getCartQuantity());
                } else {
                    quantity--;
                    cartModelList.get(position).setCartQuantity(String.valueOf(quantity));
                    holder.quantity.setText(cartModelList.get(position).getCartQuantity());
                    int singleCost = Integer.parseInt(cartModelList.get(position).getSingleCost());
                    cartModelList.get(position).setTotalCost(String.valueOf(quantity * singleCost));
                    holder.totalCost.setText("AED " + cartModelList.get(position).getTotalCost());
                }

            }
        });
    }

    private void deleteCart(int position, AddedCartHolder holder) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Deleting cart..");
        Query query = firebaseDatabase.getInstance().getReference("added_cart").orderByChild("id").equalTo(cartModelList.get(position).getId());

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    ds.getRef().removeValue();
                }

                // deleted
                Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT).show();
            }
        });
        cartModelList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,cartModelList.size());
        holder.itemView.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return cartModelList.size();
    }

    public static class AddedCartHolder extends RecyclerView.ViewHolder {

        private ImageView cartImage;
        private TextView cartModelName;
        private TextView cost;
        private TextView quantity;
        private Button removeCart;
        private Button positiveButton;
        private Button negativeButton;
        private TextView totalCost;

        public AddedCartHolder(@NonNull View itemView) {
            super(itemView);
            cartImage = itemView.findViewById(R.id.added_cart_image);
            cartModelName = itemView.findViewById(R.id.added_cart_image_name);
            cost = itemView.findViewById(R.id.added_cart_cost);
            quantity = itemView.findViewById(R.id.quantity);
            removeCart = itemView.findViewById(R.id.cart_remove_button);
            totalCost = itemView.findViewById(R.id.rent_cart_total_cost);
            positiveButton = itemView.findViewById(R.id.positive_button);
            negativeButton = itemView.findViewById(R.id.negative_button);

//            removeCart.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    deleteCart(getAdapterPosition());
//                }
//            });
        }
    }

//    private void deleteCart(int adapterPosition) {
//        cartModelList.remove(adapterPosition);
//        notifyItemRemoved(adapterPosition);
//        notifyItemRangeChanged(adapterPosition,cartModelList.size());
//    }
}
