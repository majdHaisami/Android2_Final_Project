package com.example.appfirebase.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.appfirebase.R;
import com.example.appfirebase.add_wagba_only;

public class Chefuploadfragmernt extends Fragment {

Button next;
    public Chefuploadfragmernt() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
View view =inflater.inflate(R.layout.fragment_chef_meal, container, false);
next =(Button) view.findViewById(R.id.tmam);
next.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent i = new Intent(getContext(), add_wagba_only.class );
        startActivity(i);
    }
});
return view;
    }
}