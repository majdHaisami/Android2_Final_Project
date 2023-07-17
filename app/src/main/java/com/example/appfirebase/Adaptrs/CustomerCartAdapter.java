package com.example.appfirebase.Adaptrs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.appfirebase.Class.Cart;
import com.example.appfirebase.R;

import java.util.List;

public class CustomerCartAdapter extends RecyclerView.Adapter<CustomerCartAdapter.ViewHolder> {
    private Context mcontext;
    private List<Cart> cartModellist;
    static int total =0;

    public CustomerCartAdapter(Context mcontext, List<Cart> cartModellist) {
        this.mcontext = mcontext;
        this.cartModellist = cartModellist;
    }

    @NonNull

    @Override
    public ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mcontext).inflate(R.layout.custcartadapter,parent,false);
        return new CustomerCartAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  ViewHolder holder, int position) {
        final Cart cart=cartModellist.get(position);
        holder.dishname.setText(cart.getDishname());
        holder.dishprice.setText(cart.getPrice() + "x" + cart.getDishQuntity());
        holder.dishtotal.setText(cart.getTotalprice());
        total +=Integer.parseInt(cart.getTotalprice());
        holder.elegantNumberButton.setNumber(cart.getDishQuntity());
        final int dishprice = Integer.parseInt(cart.getPrice());
    //    holder.elegantNumberButton.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
      //      @Override
        //    public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
          //      int num = newValue;
            //    int totalprice = num * dishprice;
              //  if (num != 0){
                //    HashMap<String,String> hashMap = new HashMap<>();
                  //  hashMap.put("DishID",cart.getDishID());
//                    hashMap.put("DishName",cart.getDishname());
  //                  hashMap.put("DishQuntity", String.valueOf(num));
    //                hashMap.put("Price",String.valueOf(dishprice));
      //              hashMap.put("TotalPrice",String.valueOf(totalprice));
        //            hashMap.put("ChefId",cart.getChefId());
                //    FirebaseDatabase.getInstance().getReference("Cart").child("CartItems").child().child(RandomId);

          //      }
           // }
        //});


    }

    @Override
    public int getItemCount() {
        return cartModellist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView dishname,dishprice,dishtotal;
        ElegantNumberButton elegantNumberButton;
        public ViewHolder(@NonNull  View itemView) {
            super(itemView);
            dishname=itemView.findViewById(R.id.dishnamename);
            dishprice=itemView.findViewById(R.id.Priceprice);
            dishtotal=itemView.findViewById(R.id.Totalpricetotalprice);
            elegantNumberButton=itemView.findViewById(R.id.elegentbutton);
        }
    }
}
