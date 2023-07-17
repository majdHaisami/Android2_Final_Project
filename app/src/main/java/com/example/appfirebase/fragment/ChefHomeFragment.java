package com.example.appfirebase.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appfirebase.Adaptrs.ChefhomeAdapter;
import com.example.appfirebase.Class.ChefClass;
import com.example.appfirebase.Class.MealDetailse;
import com.example.appfirebase.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ChefHomeFragment extends Fragment {
RecyclerView recyclerView;
private ArrayList<MealDetailse> updateDishModelList;
DatabaseReference dataa;


    public ChefHomeFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
View view =inflater.inflate(R.layout.fragment_chef_home, container, false);
getActivity().setTitle("Fast Cars");

setHasOptionsMenu(true);

recyclerView=view.findViewById(R.id.recrec);
recyclerView.setHasFixedSize(true);
recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
updateDishModelList=new ArrayList<MealDetailse>();


String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
dataa= FirebaseDatabase.getInstance().getReference("Chef").child(userid);
dataa.addListenerForSingleValueEvent(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        ChefClass chefc=snapshot.getValue(ChefClass.class);

        chefDishes();
    }

    @Override
    public void onCancelled(@NonNull  DatabaseError error) {

    }
});






return view;
    }
    private void chefDishes(){
        String useridd=FirebaseAuth.getInstance().getCurrentUser().getUid();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("FoodSupplyDetails").child(useridd);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    updateDishModelList.clear();
                    for (DataSnapshot dataSnapshot :snapshot.getChildren()){
                        MealDetailse updateDishModel = dataSnapshot.getValue(MealDetailse.class);
                        updateDishModelList.add(updateDishModel);
                    }
                    ChefhomeAdapter adapter = new ChefhomeAdapter(getContext(),updateDishModelList);
                     recyclerView.setAdapter(adapter);
                }catch (Exception e){
                    Toast.makeText(getContext(),e.toString(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {
                Toast.makeText(getContext(),"errrrrrrrrrrrrrrror2",Toast.LENGTH_SHORT).show();
            }
        });
    }
}

