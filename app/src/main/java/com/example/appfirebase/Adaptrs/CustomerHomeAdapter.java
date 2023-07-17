package com.example.appfirebase.Adaptrs;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appfirebase.Class.MealDetailse;
import com.example.appfirebase.OrderDish;
import com.example.appfirebase.R;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class CustomerHomeAdapter extends RecyclerView.Adapter<CustomerHomeAdapter.ViewHolder> {
private Context mcontext;
private List<MealDetailse> updateDishModellist;
DatabaseReference databaseReference;

    public CustomerHomeAdapter(Context context, List<MealDetailse> updateDishModellist) {

        this.updateDishModellist = updateDishModellist;
        this.mcontext = context;
    }

    @NonNull

    @Override
    public ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.customer_menudis,parent,false);
        return new CustomerHomeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final MealDetailse updateDishModel =updateDishModellist.get(position);
        Glide.with(mcontext).load(updateDishModel.getImageURL()).into(holder.imageView);
holder.name.setText(updateDishModel.getDishes());
        updateDishModel.getRandomUID();
        updateDishModel.getChefId();
        holder.price.setText("Price :" + updateDishModel.getPrice()+"₪");
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mcontext, OrderDish.class);
                intent.putExtra("FoodMenu",updateDishModel.getRandomUID());
                intent.putExtra("ChefId" , updateDishModel.getChefId());
                mcontext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return updateDishModellist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
ImageView imageView;
TextView name,price;
        public ViewHolder(@NonNull View view) {
            super(view);
            imageView = view.findViewById(R.id.imagecard);
            name=view.findViewById(R.id.dis);
            price=view.findViewById(R.id.priceee);
        }
    }
}
