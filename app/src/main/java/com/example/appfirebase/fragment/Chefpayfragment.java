package com.example.appfirebase.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appfirebase.Adaptrs.ChefPendingOrderAdapter;
import com.example.appfirebase.Adaptrs.CustomerCartAdapter;
import com.example.appfirebase.Class.Cart;
import com.example.appfirebase.Class.ChefClass;
import com.example.appfirebase.Class.CustomerPendingOrders;
import com.example.appfirebase.Class.CustomerPendingOrders1;
import com.example.appfirebase.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;


public class Chefpayfragment extends Fragment {
    RecyclerView recyclercart;
    private List<Cart> cartModelList;
    private CustomerCartAdapter adapter;
    private LinearLayout Totalbtn;
    DatabaseReference databaseReference,data,reference,ref,getRef,dataaa;
    public static TextView grandt;
    Button remove,placeOrder;
    String address,Addnote;
    String DishId,RandomUId,ChefId;
    private ProgressDialog progressDialog;
    //public  APIService apiService;


    public Chefpayfragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
View view = inflater.inflate(R.layout.fragment_chef_order, container, false);
        getActivity().setTitle("Customer Cart");
        recyclercart= view.findViewById(R.id.recayclcart);
        recyclercart.setHasFixedSize(true);
        recyclercart.setLayoutManager(new LinearLayoutManager(getContext()));
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        cartModelList=new ArrayList<>();
        grandt= view.findViewById(R.id.GT);
        remove=view.findViewById(R.id.removeall);
        placeOrder=view.findViewById(R.id.placeorder);
        Totalbtn=view.findViewById(R.id._Tptalprice);
      //  apiService=Client.getClient("https://fcm.googleapis.com/").create(APIService.class);
        customercart();








        return view;
    }
    private void customercart(){
        String userid= FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference= FirebaseDatabase.getInstance().getReference("Cart").child("CartItems").child(userid);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot datasnapshot) {
                cartModelList.clear();
                for (DataSnapshot snapshot : datasnapshot.getChildren()){
                    Cart cart =snapshot.getValue(Cart.class);
                    cartModelList.add(cart);
                }

                if (cartModelList.size()==0){
                    Totalbtn.setVisibility(View.INVISIBLE);


                }else {
                    Totalbtn.setVisibility(View.VISIBLE);
                    remove.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setMessage("Are you shure to Remaove All Items");
                            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    FirebaseDatabase.getInstance().getReference("Cart").child("CartItems").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).removeValue();
                                    FirebaseDatabase.getInstance().getReference("Cart").child("GrandTotal").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).removeValue();


                                }
                            });
                            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                        }
                    });
                    String UserID=FirebaseAuth.getInstance().getCurrentUser().getUid();

                    data=FirebaseDatabase.getInstance().getReference("Customer").child(UserID);

                    data.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot datasnapshot) {

                            final ChefClass customerclass = datasnapshot.getValue(ChefClass.class);

                            placeOrder.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    FirebaseDatabase.getInstance().getReference("AlreadyOrdered").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .child("isOrdred").addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull  DataSnapshot datasnapshot) {
                                            String testOrder="";
                                            if (datasnapshot.exists()){
                                                testOrder=datasnapshot.getValue(String.class);

                                            }
                                            if (testOrder.trim().equalsIgnoreCase("false") || testOrder.trim().equalsIgnoreCase("")){
                                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                                builder.setTitle("Enter Addrese");
                                                LayoutInflater inflater=getActivity().getLayoutInflater();
                                                View view = inflater.inflate(R.layout.enter_address,null);
                                                final EditText localadress=(EditText)view.findViewById(R.id.LA);
                                                final EditText addnote = (EditText)view.findViewById(R.id.Note);
                                                RadioGroup group = (RadioGroup)view.findViewById(R.id.grp);
                                                final RadioButton home = (RadioButton)view.findViewById(R.id.HomeAdress);
                                                final RadioButton other=(RadioButton)view.findViewById(R.id.otherAdress);
                                                //apiService= Client.getClient("http://fcm.googleapis.com/").create(APIService.class);

                                                builder.setView(view);

                                                group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                                    @Override
                                                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                                                        if (home.isChecked()){
                                                            localadress.setText("Alhelal street" );


                                                        }else if (other.isChecked()){
                                                            localadress.getText().clear();
                                                            Toast.makeText(getContext(),"Enter your Address",Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        progressDialog.setMessage("Please Wait...");
                                                        progressDialog.show();

                                                        reference=FirebaseDatabase.getInstance().getReference("Cart").child("CartItems")
                                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                                        reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull  DataSnapshot dataSnapshot) {

                                                                RandomUId = UUID.randomUUID().toString();
                                                                Intent intent=new Intent();
                                                                intent.putExtra("UId",RandomUId);

                                                                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                                                                    final Cart cart1 = dataSnapshot1.getValue(Cart.class);

                                                                    DishId=cart1.getDishID();
                                                                    address=localadress.getText().toString().trim();
                                                                    Addnote=addnote.getText().toString().trim();

                                                                    String ChefId,DishID,DishName,DishQuantity,Price,TotalPrice;
                                                                    ChefId=cart1.getChefId();
                                                                    DishID=cart1.getDishID();
                                                                    Intent intent1 = new Intent(getActivity(), ChefPendingOrderAdapter.class);
                                                                    intent1.putExtra("dishId",DishID);

                                                                    DishName=cart1.getDishname();
                                                                    DishQuantity=cart1.getDishQuntity();
                                                                    Price=cart1.getPrice();
                                                                    TotalPrice=cart1.getTotalprice();

                                                                    Cart cartNowSending = new Cart(ChefId,DishID,DishName,DishQuantity,Price,TotalPrice);

                                                                    FirebaseDatabase.getInstance().getReference("CustomerPendingOrders")
                                                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(RandomUId).child("Dishes").child(DishId)
                                                                            .setValue(cartNowSending);

                                                                }


                                                                ref=FirebaseDatabase.getInstance().getReference("Cart").child("GrandTotal")
                                                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("GrandTotal");

                                                                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                    @Override
                                                                    public void onDataChange(@NonNull  DataSnapshot datasnapshot) {
                                                                        String grandtotal=datasnapshot.getValue(String.class);
                                                                        HashMap<String,String> hashMap1 =new HashMap<>();
                                                                        hashMap1.put("Address",address);
                                                                        hashMap1.put("GrandTotalPrice",String.valueOf(grandtotal));
                                                                        hashMap1.put("MobileNumber","0594689383");
                                                                        hashMap1.put("Name","Mohand Amer");
                                                                        hashMap1.put("Note",Addnote);

                                                                        FirebaseDatabase.getInstance().getReference("CustomerPendingOrders")
                                                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                                                .child(RandomUId).child("OtherInformation")
                                                                                .setValue(hashMap1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                            @Override
                                                                            public void onComplete(@NonNull Task<Void> task) {

                                                                                FirebaseDatabase.getInstance().getReference("Cart").child("CartItems")
                                                                                        .child(FirebaseAuth.getInstance().getUid()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                    @Override
                                                                                    public void onComplete(@NonNull  Task<Void> task) {


                                                                                        FirebaseDatabase.getInstance().getReference("Cart").child("GrandTotal")
                                                                                                .child(FirebaseAuth.getInstance().getUid()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                            @Override
                                                                                            public void onComplete(@NonNull  Task<Void> task) {

                                                                                                getRef=FirebaseDatabase.getInstance().getReference("CustomerPendingOrders")

                                                                                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                                                                        .child(RandomUId).child("Dishes");
                                                                                                getRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                                    @Override
                                                                                                    public void onDataChange(@NonNull  DataSnapshot dataSnapshot) {

                                                                                                        for (DataSnapshot dataSnapshot2 : dataSnapshot.getChildren()){
                                                                                                            final CustomerPendingOrders customerPendingOrders = dataSnapshot2.getValue(CustomerPendingOrders.class);

                                                                                                            String d =customerPendingOrders.getDishID();
                                                                                                            String userid=FirebaseAuth.getInstance().getCurrentUser().getUid();

                                                                                                            ChefId=customerPendingOrders.getChefId();
                                                                                                            Intent intent1 = new Intent(getActivity(), ChefPendingOrderAdapter.class);
                                                                                                            intent1.putExtra("DishID",d);

                                                                                                            final HashMap<String,String> hashMap2= new HashMap<>();
                                                                                                            hashMap2.put("ChefId",ChefId);
                                                                                                            hashMap2.put("DishId",customerPendingOrders.getDishID());
                                                                                                            hashMap2.put("dishname",customerPendingOrders.getDishName());
                                                                                                            hashMap2.put("dishQuntity",customerPendingOrders.getDishQuantity());
                                                                                                            hashMap2.put("Price",customerPendingOrders.getPrice());
                                                                                                            hashMap2.put("RandomUID",RandomUId);
                                                                                                            hashMap2.put("totalprice",customerPendingOrders.getTotalProce());
                                                                                                            hashMap2.put("UserId",userid);

                                                                                                            FirebaseDatabase.getInstance().getReference("ChefPendingOrders")
                                                                                                                    .child(ChefId).child(RandomUId).child("Dishes").child(d)
                                                                                                                    .setValue(hashMap2);


                                                                                                        }

                                                                                                        dataaa=FirebaseDatabase.getInstance().getReference("CustomerPendingOrders")
                                                                                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(RandomUId)
                                                                                                                .child("OtherInformation");

                                                                                                        dataaa.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                                            @Override
                                                                                                            public void onDataChange(@NonNull  DataSnapshot datasnapshot) {

                                                                                                                final CustomerPendingOrders1 customerPendingOrders1 = datasnapshot.getValue(CustomerPendingOrders1.class);
                                                                                                                HashMap<String,String> hashMap3=new HashMap<>();

                                                                                                                hashMap3.put("Address",customerPendingOrders1.getAddress());
                                                                                                                hashMap3.put("GrandTotalPrice",customerPendingOrders1.getGrandTotalPrice());
                                                                                                                hashMap3.put("MobileNumber",customerPendingOrders1.getMobileNumber());
                                                                                                                hashMap3.put("Name",customerPendingOrders1.getName());
                                                                                                                hashMap3.put("Note",customerPendingOrders1.getNote());

                                                                                                                hashMap3.put("RandomUID",RandomUId);

                                                                                                                FirebaseDatabase.getInstance().getReference("ChefPendingOrders")
                                                                                                                        .child(ChefId).child(RandomUId)
                                                                                                                        .child("OtherInformation").setValue(hashMap3).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                                                    @Override
                                                                                                                    public void onSuccess(@NonNull  Void aVoid) {
                                                                                                                        FirebaseDatabase.getInstance().getReference("AlreadyOrdered")
                                                                                                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                                                                                                .child("isOrdered").setValue("true")
                                                                                                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                                                                    @Override
                                                                                                                                    public void onSuccess(@NonNull  Void aVoid) {
                                                                                                                                        Toast.makeText(getActivity(),"Notification",Toast.LENGTH_SHORT).show();
                                                                                                                                        FirebaseDatabase.getInstance().getReference().child("Tokens")
                                                                                                                                                .child(ChefId).child("token").addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                                                                            @Override
                                                                                                                                            public void onDataChange(@NonNull  DataSnapshot dataSnapshot) {
                                                                                                                                                String usertoken =dataSnapshot.getValue(String.class);
                                                                                                                                                Log.e("usertoken",""+usertoken);
                                                                                                                                           //     sendNotifications(usertoken,"New Order","you have a new Order","customerCart");
                                                                                                                                                progressDialog.dismiss();
                                                                                                                                            }

                                                                                                                                            @Override
                                                                                                                                            public void onCancelled(@NonNull  DatabaseError error) {
                                                                                                                                                Toast.makeText(getContext(),"Faild Notigicationlk " ,Toast.LENGTH_SHORT).show();
                                                                                                                                                Toast.makeText(getContext(),"Faild Notigication lgf" ,Toast.LENGTH_SHORT).show();


                                                                                                                                            }
                                                                                                                                        });
                                                                                                                                        progressDialog.dismiss();
                                                                                                                                    }
                                                                                                                                });



                                                                                                                    }
                                                                                                                }).addOnFailureListener(new OnFailureListener() {
                                                                                                                    @Override
                                                                                                                    public void onFailure(@NonNull  Exception e) {
                                                                                                                        Toast.makeText(getActivity(),"Reeeeeeeeeeeeeeeeeeo1",Toast.LENGTH_SHORT).show();

                                                                                                                    }
                                                                                                                });


                                                                                                            }

                                                                                                            @Override
                                                                                                            public void onCancelled(@NonNull  DatabaseError error) {
                                                                                                                Toast.makeText(getActivity(),"Reeeeeeeeeeeeeeeeeeo2",Toast.LENGTH_SHORT).show();

                                                                                                            }
                                                                                                        });



                                                                                                    }

                                                                                                    @Override
                                                                                                    public void onCancelled(@NonNull  DatabaseError error) {
                                                                                                        Toast.makeText(getActivity(),"Reeeeeeeeeeeeeeeeeeo3",Toast.LENGTH_SHORT).show();

                                                                                                    }
                                                                                                });





                                                                                            }
                                                                                        }).addOnFailureListener(new OnFailureListener() {
                                                                                            @Override
                                                                                            public void onFailure(@NonNull  Exception e) {
                                                                                                Toast.makeText(getActivity(),"Reeeeeeeeeeeeeeeeeeo4",Toast.LENGTH_SHORT).show();

                                                                                            }
                                                                                        });

                                                                                    }
                                                                                }).addOnFailureListener(new OnFailureListener() {
                                                                                    @Override
                                                                                    public void onFailure(@NonNull  Exception e) {
                                                                                        Toast.makeText(getActivity(),"Reeeeeeeeeeeeeeeeeeo5",Toast.LENGTH_SHORT).show();

                                                                                    }
                                                                                });




                                                                            }
                                                                        }).addOnFailureListener(new OnFailureListener() {
                                                                            @Override
                                                                            public void onFailure(@NonNull  Exception e) {
                                                                                Toast.makeText(getActivity(),"Reeeeeeeeeeeeeeeeeeo6",Toast.LENGTH_SHORT).show();

                                                                            }
                                                                        });




                                                                    }

                                                                    @Override
                                                                    public void onCancelled(@NonNull  DatabaseError error) {
                                                                        Toast.makeText(getActivity(),"Reeeeeeeeeeeeeeeeeeo7",Toast.LENGTH_SHORT).show();

                                                                    }
                                                                });





                                                            }

                                                            @Override
                                                            public void onCancelled(@NonNull  DatabaseError error) {
                                                                Toast.makeText(getActivity(),"Reeeeeeeeeeeeeeeeeeo8",Toast.LENGTH_SHORT).show();

                                                            }
                                                        });
                                                        dialog.dismiss();



                                                    }
                                                });
                                                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.dismiss();

                                                    }
                                                });
                                                AlertDialog alert = builder.create();
                                                alert.show();

                                            }else {
                                                //         ReusableCodeForAll.ShowAlert(getContext(),"Error","");
                                                Toast.makeText(getActivity(),"Reeeeeeeeeeeeeeeeeeo99",Toast.LENGTH_SHORT).show();

                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull  DatabaseError error) {
                                            Toast.makeText(getActivity(),"Reeeeeeeeeeeeeeeeeeo9",Toast.LENGTH_SHORT).show();

                                        }
                                    });
                                }
                            });


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(getActivity(),"Reeeeeeeeeeeeeeeeeeo10",Toast.LENGTH_SHORT).show();

                        }
                    });


                }
                adapter = new CustomerCartAdapter(getContext(),cartModelList);
                recyclercart.setAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {
                Toast.makeText(getActivity(),"Reeeeeeeeeeeeeeeeeeo11",Toast.LENGTH_SHORT).show();


            }
        });
















    }

}