
package com.example.appfirebase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class phonelogin extends AppCompatActivity {
    TextInputLayout editText;
Button sendotp,siginemail;
FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phonelogin);
       editText=(TextInputLayout)findViewById(R.id.numberphone);
         sendotp=(Button)findViewById(R.id.send123);

        firebaseAuth=FirebaseAuth.getInstance();
        sendotp.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
               String Phonenumber=editText.getEditText().getText().toString().trim();
               Intent p = new Intent(phonelogin.this,ChefLoginOtp.class);
            p.putExtra("Phonenumber",Phonenumber);
               startActivity(p);
                finish();
          }
     });

    }
}