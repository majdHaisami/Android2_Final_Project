package com.example.appfirebase;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class Loginphone extends AppCompatActivity {
    TextInputLayout editText;
    Button sendotp,siginemail;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginphone);
        editText=(TextInputLayout)findViewById(R.id.numberphone123);
        sendotp=(Button)findViewById(R.id.send12345);

        firebaseAuth= FirebaseAuth.getInstance();
        sendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Phonenumber=editText.getEditText().getText().toString().trim();
//                Intent p = new Intent(Loginphone.this,CustmloginOTP.class);
//                p.putExtra("Phonenumber",Phonenumber);
              //  startActivity(p);
               // finish();
            }
        });
    }
}