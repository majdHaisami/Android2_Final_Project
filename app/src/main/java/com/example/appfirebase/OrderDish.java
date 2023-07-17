package com.example.appfirebase;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.appfirebase.Class.Cart;
import com.example.appfirebase.Class.ChefClass;
import com.example.appfirebase.Class.MealDetailse;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OrderDish extends AppCompatActivity {
String RandomId,ChefId;
ImageView imageView;
ElegantNumberButton additem;
TextView FoodName,Chefname,ChefLocation,FoodQuntity,FoodPrice,FoodDescription;
DatabaseReference databaseReference,dataaa,chefdata,reference,data,dataa,dataref;
String State,City,Sub,dishname;
int dishprice;
String custID;
FirebaseDatabase firebaseDatabase;





@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_dish);
        FoodName = (TextView)findViewById(R.id.foodName);
        Chefname = (TextView)findViewById(R.id.Chef_name);
        ChefLocation =(TextView)findViewById(R.id.Chef_location);
        FoodQuntity=(TextView)findViewById(R.id.Food_Quntity);
        FoodPrice = (TextView)findViewById(R.id.Food_price);
        FoodDescription=(TextView)findViewById(R.id.Food_Description);
        imageView =(ImageView) findViewById(R.id.imageView555);
        additem=(ElegantNumberButton)findViewById(R.id._numberbtn);
        final String userid= FirebaseAuth.getInstance().getCurrentUser().getUid();
        dataaa=FirebaseDatabase.getInstance().getReference("Chef").child(userid);
        dataaa.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot datasnapshot) {
//                Customerclass cust = datasnapshot.getValue(Customerclass.class);
//                State =cust.getStatee();
//                City = cust.getCityy();
                RandomId = getIntent().getStringExtra("FoodMenu");
                ChefId = getIntent().getStringExtra("ChefId");
                databaseReference=FirebaseDatabase.getInstance().getReference("FoodSupplyDetails").child(ChefId).child(RandomId);
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull  DataSnapshot datasnapshot) {
                        MealDetailse updateDishModel =datasnapshot.getValue(MealDetailse.class);
                        FoodName.setText(updateDishModel.getDishes());
                        String qua ="<b>" + "Quntity : " + "</b>" + updateDishModel.getQuantity();
                        FoodQuntity.setText(Html.fromHtml(qua));
                        String ss = "<b>" + "Description : " + "</b>" + updateDishModel.getDescription();
                        FoodDescription.setText(Html.fromHtml(ss));
                        String pri = "<b>" + "Price  :شيكل "+"</b>" +updateDishModel.getPrice();
                        FoodPrice.setText(Html.fromHtml(pri));
                        Glide.with(OrderDish.this).load(updateDishModel.getImageURL()).into(imageView);
                        chefdata=FirebaseDatabase.getInstance().getReference("Chef").child(ChefId);
                        chefdata.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                                ChefClass   chefC = datasnapshot.getValue(ChefClass.class);
                                String name = "<b>" + "ChefName : " + "</b>" + chefC.getFname() + " " + chefC.getLname();
                                Chefname.setText(Html.fromHtml(name));
                                String loc = "</b>" + "Location : " ;
                                ChefLocation.setText(Html.fromHtml(loc));

                                custID=FirebaseAuth.getInstance().getCurrentUser().getUid();
                                databaseReference=FirebaseDatabase.getInstance().getReference("Cart").child("CartItems").child(custID).child(RandomId);
                                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull  DataSnapshot datasnapshot) {
                                        Cart cartnow = datasnapshot.getValue(Cart.class);
                                        if (datasnapshot.exists()){
                                            additem.setNumber(cartnow.getDishQuntity());
                                        }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull  DatabaseError error) {
                                        Toast.makeText(OrderDish.this,"NOOOOOOOOOOOOOOOOOOOOOOOOOO",Toast.LENGTH_SHORT).show();

                                    }
                                });
                            }

                            @Override
                            public void onCancelled(@NonNull  DatabaseError error) {
                                Toast.makeText(OrderDish.this,"NOOOOOOOOOOOOOOOOOOOOOOOOOO",Toast.LENGTH_SHORT).show();

                            }
                        });
                        additem.setOnClickListener(new ElegantNumberButton.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                try {
                                    dataref=FirebaseDatabase.getInstance().getReference("Cart").child("CartItems").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                    dataref.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                                            Cart cart1 = null;
                                            if (datasnapshot.exists()){
                                                int totalcount = 0;
                                                for (DataSnapshot snapshot:datasnapshot.getChildren()){
                                                    totalcount++;
                                                }
                                                int i = 0;
                                                for (DataSnapshot snapshot : datasnapshot.getChildren()){
                                                    i++;
                                                    if (i == totalcount){
                                                        cart1=snapshot.getValue(Cart.class);

                                                    }
                                                }

                                                if (ChefId.equals(cart1.getChefId())){
                                                    Log.e("bbbbbbbbbbbbbbbbbbb","bbbbbbbbbbbbbbbbbbbbbbbbbb");

                                                    data=FirebaseDatabase.getInstance().getReference("FoodSupplyDetails").child(ChefId).child(RandomId);
                                                    data.addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull  DataSnapshot datasnapshot) {
                                                            MealDetailse update = datasnapshot.getValue(MealDetailse.class);
                                                            dishname=update.getDishes();
                                                            dishprice=Integer.parseInt(update.getPrice());
                                                            int num = Integer.parseInt(additem.getNumber());
                                                            int totalprice = num * dishprice;
                                                            if (num != 0 ){
                                                                Cart newcart = new Cart(ChefId,RandomId,dishname,String.valueOf(num),String.valueOf(dishprice),String.valueOf(totalprice));
                                                                Log.e("nnnnnnnnnnnnnnnn","Nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn");
                                                                custID=FirebaseAuth.getInstance().getCurrentUser().getUid();
                                                                reference=FirebaseDatabase.getInstance().getReference("Cart").child("CartItems").child(custID).child(RandomId);
                                                                reference.setValue(newcart).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                    @Override
                                                                    public void onSuccess(@NonNull  Void unused) {
                                                                        Toast.makeText(OrderDish.this,"Added to Cart" , Toast.LENGTH_SHORT).show();
                                                                    }
                                                                });
                                                            }else {
                                                                firebaseDatabase.getInstance().getReference("Cart").child("CartItems").child(custID).child(RandomId).removeValue();
                                                            }
                                                        }

                                                        @Override
                                                        public void onCancelled(@NonNull  DatabaseError error) {
                                                            Toast.makeText(OrderDish.this,"Added not Cart" , Toast.LENGTH_SHORT).show();

                                                        }
                                                    });

                                                }else {
                                                    AlertDialog.Builder builder = new AlertDialog.Builder(OrderDish.this);
                                                    builder.setMessage("You Cant add food items of multiple at a time.Try to add items of smae chef");
                                                    builder.setCancelable(false);
                                                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            dialog.dismiss();
                                                            Intent intent = new Intent(OrderDish.this,ChefFoodPanel_BottomNavigation.class);
                                                            startActivity(intent);
                                                        }
                                                    });
                                                    AlertDialog alert = builder.create();
                                                    alert.show();
                                                }



                                            }else {
                                                data=FirebaseDatabase.getInstance().getReference("FoodSupplyDetails").child(ChefId).child(RandomId);
                                                data.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull  DataSnapshot datasnapshot) {
                                                        MealDetailse update = datasnapshot.getValue(MealDetailse.class);
                                                        dishname=update.getDishes();
                                                        dishprice=Integer.parseInt(update.getPrice());
                                                        int num = Integer.parseInt(additem.getNumber());
                                                        int totalprice = num * dishprice;
                                                        if (num != 0 ){
                                                            Cart newcart = new Cart(ChefId,RandomId,dishname,String.valueOf(num),String.valueOf(dishprice),String.valueOf(totalprice));
                                                            custID=FirebaseAuth.getInstance().getCurrentUser().getUid();
                                                            reference=FirebaseDatabase.getInstance().getReference("Cart").child("CartItems").child(custID).child(RandomId);
                                                            reference.setValue(newcart).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(@NonNull  Void unused) {
                                                                    Toast.makeText(OrderDish.this,"Added to Cart" , Toast.LENGTH_SHORT).show();
                                                                }
                                                            });
                                                        }else {
                                                            firebaseDatabase.getInstance().getReference("Cart").child("CartItems").child(custID).child(RandomId).removeValue();
                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull  DatabaseError error) {
                                                        Toast.makeText(OrderDish.this,"Added not Cart" , Toast.LENGTH_SHORT).show();

                                                    }
                                                });                                            }

                                        }

                                        @Override
                                        public void onCancelled(@NonNull  DatabaseError error) {
                                            Toast.makeText(OrderDish.this,"NOOOOOOOOOOOOOOOOOOOOOOOOOO",Toast.LENGTH_SHORT).show();

                                        }
                                    });








                                }catch (Exception e){
                                    Toast.makeText(OrderDish.this,"NOOOOOOOOOOOOOOOOOOOOOOOOOO",Toast.LENGTH_SHORT).show();

                                }

                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull  DatabaseError error) {
                        Toast.makeText(OrderDish.this,"NOOOOOOOOOOOOOOOOOOOOOOOOOO",Toast.LENGTH_SHORT).show();

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {
Toast.makeText(OrderDish.this,"NOOOOOOOOOOOOOOOOOOOOOOOOOO",Toast.LENGTH_SHORT).show();
            }
        });




}
}