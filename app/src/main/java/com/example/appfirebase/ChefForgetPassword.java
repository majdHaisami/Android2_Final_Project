package com.example.appfirebase;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appfirebase.Class.ReusableCodeForAll;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class ChefForgetPassword extends AppCompatActivity {
TextInputLayout forgetpassword;
Button Reset;
FirebaseAuth FAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_forget_password);
        forgetpassword=(TextInputLayout)findViewById(R.id.Email13);
        Reset =(Button)findViewById(R.id.NEXT);
        FAuth=FirebaseAuth.getInstance();
        String forgetPassword1= forgetpassword.getEditText().getText().toString().trim();
        Reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog mDialog = new ProgressDialog(ChefForgetPassword.this);
                mDialog.setCancelable(false);
                mDialog.setCanceledOnTouchOutside(false);
                mDialog.setMessage("Loding in ...");
                mDialog.show();
                FAuth.sendPasswordResetEmail(forgetPassword1).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            mDialog.dismiss();
                            ReusableCodeForAll.ShowAlert(ChefForgetPassword.this,"","done,Please check your mail");

                        }else {
                            mDialog.dismiss();
                            ReusableCodeForAll.ShowAlert(ChefForgetPassword.this,"Error","Sorry,please try later");

                        }
                    }
                });
            }
        });

    }
}