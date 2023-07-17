package com.example.appfirebase;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appfirebase.Class.ReusableCodeForAll;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class ChefLogin extends AppCompatActivity {
TextInputLayout email,pass;
Button Sigin,SiginPhone;
TextView ForgtPassword;
TextView txt;
FirebaseAuth FAuth;
String em,pwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef);

        try {
email =(TextInputLayout)findViewById(R.id.Email12);
pass=(TextInputLayout)findViewById(R.id.Password12);
Sigin=(Button)findViewById(R.id.Login12);
txt=(TextView)findViewById(R.id.creatnewuser12);
ForgtPassword=(TextView)findViewById(R.id.ForgetPassword12);
SiginPhone=(Button)findViewById(R.id.Siginwhithphone12);
FAuth=FirebaseAuth.getInstance();
Sigin.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        em=email.getEditText().getText().toString().trim();
        pwd=pass.getEditText().getText().toString().trim();
        if (isValid()){
final ProgressDialog mDialog = new ProgressDialog(ChefLogin.this);
mDialog.setCanceledOnTouchOutside(false);
mDialog.setCancelable(false);
mDialog.setMessage("Loding in ...");
mDialog.show();
FAuth.signInWithEmailAndPassword(em,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
    @Override
    public void onComplete(@NonNull  Task<AuthResult> task) {
        if (task.isSuccessful()){
            mDialog.dismiss();

                mDialog.dismiss();
                Toast.makeText(getApplicationContext(),"you are login sucsses" ,Toast.LENGTH_SHORT).show();
                Intent z = new Intent(ChefLogin.this,ChefFoodPanel_BottomNavigation.class);
                startActivity(z);

        }else {
            mDialog.dismiss();
            ReusableCodeForAll.ShowAlert(ChefLogin.this,"Error",task.getException().getMessage());
        }

    }
});
txt.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent Register = new Intent(ChefLogin.this,Registeration.class);
        startActivity(Register);

    }
});
ForgtPassword.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent a = new Intent(ChefLogin.this,ChefForgetPassword.class);
        startActivity(a);
        finish();
    }
});







        }else {

        }
    }
});
















        }catch (Exception e){

        }
    }
    String emailpatterrn = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
public boolean isValid(){
    email.setErrorEnabled(false);
    email.setError("");
    pass.setErrorEnabled(false);
    pass.setError("");
    boolean issvalidemail =false,isvalidpassword = false,isvalid=false;
    if (TextUtils.isEmpty(em)){
        email.setErrorEnabled(true);
        email.setError("Email is required");
    }else {
        if (em.matches(emailpatterrn)){
            issvalidemail=true;
        }else {
            email.setErrorEnabled(true);
            email.setError("Enter a valid Email Adress");
        }
    }
    if (TextUtils.isEmpty(pwd)){
        pass.setErrorEnabled(true);
        pass.setError("Password is reqauired");
    }else {
        isvalidpassword=true;
    }
    isvalid=(issvalidemail && isvalidpassword) ? true : false ;
    return isvalid;
}


}