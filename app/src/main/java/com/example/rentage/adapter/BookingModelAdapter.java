package com.example.rentage.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentage.R;
import com.example.rentage.RentSpecificActivity;
import com.example.rentage.model.BookingModel;

import java.util.List;

public class BookingModelAdapter extends RecyclerView.Adapter<BookingModelAdapter.BookingModelHolder> {

    private Context context;
    private List<BookingModel> bookingModelList;

    public BookingModelAdapter(Context context, List<BookingModel> bookingModelList) {
        this.context = context;
        this.bookingModelList = bookingModelList;
    }

    @NonNull
    @Override
    public BookingModelHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.model_booking, parent, false);
        return new BookingModelHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingModelHolder holder, int position) {
        holder.bookingImage.setImageResource(bookingModelList.get(position).getBookingImage());
        holder.bookingTitle.setText(bookingModelList.get(position).getBookingTitle());
        holder.bookingDescription.setText(bookingModelList.get(position).getBookingDetails());

        holder.bookingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RentSpecificActivity.class);
                intent.putExtra("ID","");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookingModelList.size();
    }

    static class BookingModelHolder extends RecyclerView.ViewHolder {
        private ImageView bookingImage;
        private TextView bookingTitle;
        private TextView bookingDescription;
        private Button bookingButton;
        BookingModelHolder(@NonNull View itemView) {
            super(itemView);
            bookingImage = itemView.findViewById(R.id.booking_image);
            bookingTitle = itemView.findViewById(R.id.booking_title);
            bookingDescription = itemView.findViewById(R.id.booking_desc);
            bookingButton = itemView.findViewById(R.id.booking_button);
        }
    }
}
