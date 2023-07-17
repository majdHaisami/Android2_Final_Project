package com.example.appfirebase.Adaptrs;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appfirebase.Class.MealDetailse;
import com.example.appfirebase.R;
import com.example.appfirebase.Update_Delet_Dish;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ChefhomeAdapter extends RecyclerView.Adapter<ChefhomeAdapter.ViewHolder> {
private Context mcont;
private List<MealDetailse> mealDetailses;

    public ChefhomeAdapter(Context context, List<MealDetailse>updateModellist) {
        this.mealDetailses=updateModellist;
        this.mcont = context;

    }

    @NonNull

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcont).inflate(R.layout.chefhomeadapterlayout,parent,false);

        return new ChefhomeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  ViewHolder holder, int position) {

        final MealDetailse postmail = mealDetailses.get(position);

        holder.dishes.setText(postmail.getDishes());
        Picasso.get().load(postmail.getImageURL()).resize(70,70).centerCrop().into(holder.imagedish);
        postmail.getRandomUID();
        holder.dishes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mcont, Update_Delet_Dish.class);
                intent.putExtra("updatedeleteddish",postmail.getRandomUID());
                mcont.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mealDetailses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView dishes;
        CircularImageView imagedish;
        public ViewHolder(@NonNull  View itemView) {
            super(itemView);

            dishes=itemView.findViewById(R.id.textlayout);
            imagedish=itemView.findViewById(R.id._dishimagerul);
        }
    }
}
