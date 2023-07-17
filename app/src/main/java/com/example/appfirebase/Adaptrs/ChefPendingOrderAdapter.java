package com.example.appfirebase.Adaptrs;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appfirebase.Class.ChefPendingOrders;
import com.example.appfirebase.Class.ChefpendingOrder1;
import com.example.appfirebase.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;

public class ChefPendingOrderAdapter extends RecyclerView.Adapter<ChefPendingOrderAdapter.ViewHolder> {
private Context context;
private List<ChefpendingOrder1> chefpendingOrder1List;
String userid,dishid;

    public ChefPendingOrderAdapter(Context context, List<ChefpendingOrder1> chefpendingOrder1List) {
        this.context = context;
        this.chefpendingOrder1List = chefpendingOrder1List;
    }

    @NonNull

    @Override
    public ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(context).inflate(R.layout.cheforderpending,parent,false);
        return new ChefPendingOrderAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  ChefPendingOrderAdapter.ViewHolder holder, int position) {
        ChefpendingOrder1 chefpendingOrder1=chefpendingOrder1List.get(position);
        holder.address.setText(chefpendingOrder1.getAddress());
        holder.Grandtotal.setText("Grand Total : $"+chefpendingOrder1.getGrandTotalPrice());
       final String random=chefpendingOrder1.getRandomUID();

        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Intent intent = new Intent();
             //   String UID = intent.getStringExtra("UId");
                Log.e("tttttttttttttttttttttttttttttttttttttttttt","hhhh"+random);

                DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("ChefPendingOrders")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(random).child("Dishes");

                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull  DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                            final ChefPendingOrders chefPendingOrders=snapshot.getValue(ChefPendingOrders.class);

                            HashMap<String,String> hashMap=new HashMap<>();
                            String ChefId=chefPendingOrders.getChefId();

                            String dishID = chefPendingOrders.getDishID();
                            Log.e("hhhhhhhgffffffffffffffffffffff","gg"+dishid);
                            hashMap.put("ChefId",chefPendingOrders.getChefId());
                            hashMap.put("DishID",chefPendingOrders.getDishID());
                            hashMap.put("DishName",chefPendingOrders.getDishName());
                            hashMap.put("Price",chefPendingOrders.getPrice());
                            hashMap.put("DishQuantity",chefPendingOrders.getDishQuantity());
                            hashMap.put("RandomUID",random);
                            hashMap.put("TotalProce",chefPendingOrders.getTotalProce());
                            hashMap.put("Userid",chefPendingOrders.getUserid());
                            Log.e("mohand",""
                            +ChefId);
                            Log.e("mohand2",""
                                    +dishID);
                            FirebaseDatabase.getInstance().getReference("ChefPaymentOrders").child(ChefId)
                                    .child(random).child("Dishes").child(dishID
                            )
                                    .setValue(hashMap);

                        }
                        DatabaseReference data =FirebaseDatabase.getInstance().getReference("ChefPendingOrders")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(random)
                                .child("OtherInformation");
                        data.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull  DataSnapshot dataSnapshot) {
                                ChefpendingOrder1 chefpendingOrder11 =dataSnapshot.getValue(ChefpendingOrder1.class);
                                HashMap<String,String> hashMap1=new HashMap<>();
                                hashMap1.put("Address", chefpendingOrder11.getAddress());
                                hashMap1.put("GrandTotalPrice",chefpendingOrder11.getGrandTotalPrice());
                                hashMap1.put("MobileNumber",chefpendingOrder11.getMobileNumber());
                                hashMap1.put("Name",chefpendingOrder11.getName());
                                hashMap1.put("Note",chefpendingOrder11.getNote());
                                hashMap1.put("RandomUID",random);
                                FirebaseDatabase.getInstance().getReference("ChefPaymentOrders")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .child(random).child("OtherInformation").setValue(hashMap1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull  Task<Void> task) {

                                        DatabaseReference Referance =FirebaseDatabase.getInstance().getReference("ChefPendingOrders")
                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .child(random).child("Dishes");
                                        Referance.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                                                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                                                    final ChefPendingOrders chefPendingOrders=dataSnapshot.getValue(ChefPendingOrders.class);
                                                    HashMap<String,String> hashMap2=new HashMap<>();
                                                    userid=chefPendingOrders.getUserid();
                                                    dishid=chefPendingOrders.getDishID();
                                                    Log.e("erfmkjrenfernfkejrnferf","efmnkjferferf"+dishid);
                                                    hashMap2.put("ChefId",chefPendingOrders.getChefId());
                                                    hashMap2.put("DishId",chefPendingOrders.getDishID());
                                                    hashMap2.put("dishname",chefPendingOrders.getDishName());
                                                    hashMap2.put("price",chefPendingOrders.getPrice());
                                                    hashMap2.put("DishQuntity",chefPendingOrders.getDishQuantity());
                                                    hashMap2.put("RandomUID",random);
                                                    hashMap2.put("totalprice",chefPendingOrders.getTotalProce());
                                                    hashMap2.put("UserId",chefPendingOrders.getUserid());
                                                    Log.e("mohand3",""+userid);
                                                    Log.e("mohand4",""+random);
                                                    Log.e("mohand5",""+dishid);

                                                    FirebaseDatabase.getInstance().getReference("CustomerPaymentOrders")
                                                            .child(userid).child(random).child("Dishes")
                                                            .child(dishid).setValue(hashMap2);



                                                }

                                                DatabaseReference dataa=FirebaseDatabase.getInstance().getReference("ChefPendingOrders")
                                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                        .child(random).child("OtherInformation");
                                                dataa.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull  DataSnapshot datasnapshot) {

                                                        ChefpendingOrder1 chefpendingOrder1=datasnapshot.getValue(ChefpendingOrder1.class);
                                                        HashMap<String,String>hashMap3=new HashMap<>();
                                                        hashMap3.put("Address",chefpendingOrder1.getAddress());
                                                        hashMap3.put("GrandTotalPrice",chefpendingOrder1.getGrandTotalPrice());
                                                        hashMap3.put("Mobilenumber",chefpendingOrder1.getMobileNumber());
                                                        hashMap3.put("Name",chefpendingOrder1.getName());
                                                        hashMap3.put("Note",chefpendingOrder1.getNote());
                                                        hashMap3.put("RandomUID",random);
                                                        FirebaseDatabase.getInstance().getReference("CustomerPaymentOrders")
                                                                .child(userid).child(random)
                                                                .child("OtherInformation").setValue(hashMap3).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull  Task<Void> task) {

                                                                FirebaseDatabase.getInstance().getReference("CustomerPendingOrders").child(userid)
                                                                        .child(random).child("Dishes").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull  Task<Void> task) {
                                                                        FirebaseDatabase.getInstance().getReference("CustomerPendingOrders")
                                                                                .child(userid).child(random).child("OtherInformation")
                                                                                .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                            @Override
                                                                            public void onComplete(@NonNull  Task<Void> task) {

                                                                                FirebaseDatabase.getInstance().getReference("ChefPendingOrders")
                                                                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                                                        .child(random).child("Dishes")
                                                                                        .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                    @Override
                                                                                    public void onComplete(@NonNull  Task<Void> task) {
                                                                                        FirebaseDatabase.getInstance().getReference("ChefPendingOrders")
                                                                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())

                                                                                                .child(random).child("OtherInformation").removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                            @Override
                                                                                            public void onSuccess(@NonNull @NotNull Void unused) {
                                                                                                Toast.makeText(v.getContext(), "Donnnnn",Toast.LENGTH_SHORT).show();
                                                                                            }
                                                                                        });

                                                                                    }
                                                                                });

                                                                            }
                                                                        });


                                                                    }
                                                                });

                                                            }
                                                        });





                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull  DatabaseError error) {
                                                        Toast.makeText(v.getContext(), "Donnnn2n",Toast.LENGTH_SHORT).show();


                                                    }
                                                });

                                            }

                                            @Override
                                            public void onCancelled(@NonNull  DatabaseError error) {
                                                Toast.makeText(v.getContext(), "Don5nnnn",Toast.LENGTH_SHORT).show();


                                            }
                                        });


                                    }
                                });


                            }

                            @Override
                            public void onCancelled(@NonNull  DatabaseError error) {
                                Toast.makeText(v.getContext(), "D877onnnnn",Toast.LENGTH_SHORT).show();


                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(v.getContext(), "Donnn9n",Toast.LENGTH_SHORT).show();


                    }
                });







            }
        });

    }

    @Override
    public int getItemCount() {
        return chefpendingOrder1List.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView address,Grandtotal;
        Button view,accept,reject;
        public ViewHolder(@NonNull  View itemView) {
            super(itemView);
            address=itemView.findViewById(R.id.address);
            Grandtotal=itemView.findViewById(R.id.grandtotal);
            view=itemView.findViewById(R.id.viewbtn);
            accept=itemView.findViewById(R.id.acceptbtn);
            reject=itemView.findViewById(R.id.rejectbtn);



        }
    }
}
