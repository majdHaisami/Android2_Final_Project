package com.example.appfirebase.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.appfirebase.Adaptrs.CustomerHomeAdapter;
import com.example.appfirebase.Class.MealDetailse;
import com.example.appfirebase.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ChefPendingOrdersFragment extends Fragment {
    RecyclerView recyclerView;
    private List<MealDetailse> updateDishModelList;
    private CustomerHomeAdapter adapter;
    String State,City;
    DatabaseReference dataa,databaseReference;
    SwipeRefreshLayout swipeRefreshLayout;
    SearchView searchView;


    public ChefPendingOrdersFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
View view = inflater.inflate(R.layout.fragment_chef_pending_orders, container, false);

        getActivity().setTitle("All Cars for the customer");

        setHasOptionsMenu(true);
        recyclerView=view.findViewById(R.id.ercrecrec);
        recyclerView.setHasFixedSize(true);
        Animation animation = AnimationUtils.loadAnimation(getContext(),R.anim.move);
        recyclerView.startAnimation(animation);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        updateDishModelList = new ArrayList<>();

        //     swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipwlayout);
        // swipeRefreshLayout.setOnRefreshListener(this);
        //() -> swipeRefreshLayout.setColorSchemeColors(R.color.colorPrimaryDark, R.color.colorPrimaryDark)


        String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        dataa= FirebaseDatabase.getInstance().getReference("Customer").child(userid);
        dataa.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
          //       ChefClass cust  = snapshot.getValue(ChefClass.class);
             //   State=cust.getStatee();
               // City=cust.getCityy();
                customermenu();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //       swipeRefreshLayout.setRefreshing(false);

            }
        });



        return view;
    }


    private void customermenu(){
        Log.e("hhhhhhhhhhhhhhhhhhhhhh","hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
        // swipeRefreshLayout.setRefreshing(true);

        databaseReference=FirebaseDatabase.getInstance().getReference("FoodSupplyDetails");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot dataSnapshot) {
                updateDishModelList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    for (DataSnapshot snapshot1:snapshot.getChildren()){
                        MealDetailse updatDishModel = snapshot1.getValue(MealDetailse.class);
                        updateDishModelList.add(updatDishModel);
                    }
                }
                adapter = new CustomerHomeAdapter(getContext(),updateDishModelList);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {
                //    swipeRefreshLayout.setRefreshing(false);
            }
        });

    }
    private  void search(final  String searchtext){
        ArrayList<MealDetailse> mylist = new ArrayList<>();
        for (MealDetailse object : updateDishModelList) {
            if (object.getDishes().toLowerCase().contains(searchtext.toLowerCase())) {
                mylist.add(object);
            }
        }
        adapter = new CustomerHomeAdapter(getContext() , mylist);
        recyclerView.setAdapter(adapter);
    }


    }
