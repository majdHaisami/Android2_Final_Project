package com.example.appfirebase;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class ChefLoginOtp extends AppCompatActivity {
    String verificationId;
    FirebaseAuth FAuth;
    Button verify , Resend ;
    TextView txt;
    TextInputLayout entercode;
    String phoneno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_login_otp);



            if (getIntent().getExtras()!=null){
                phoneno = getIntent().getStringExtra("Phonenumber").trim();
                Toast.makeText(this, phoneno, Toast.LENGTH_SHORT).show();
            }

            entercode = (TextInputLayout) findViewById(R.id.code);
            txt = (TextView) findViewById(R.id.txtview);
            Resend = (Button)findViewById(R.id.Resend);
            verify = (Button) findViewById(R.id.Virify);
            FAuth = FirebaseAuth.getInstance();

            Resend.setVisibility(View.INVISIBLE);
            txt.setVisibility(View.INVISIBLE);

            sendverificationcode(phoneno);

            verify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String code = entercode.getEditText().getText().toString().trim();
                    Resend.setVisibility(View.INVISIBLE);

                    if (code.isEmpty() && code.length()<6){
                        entercode.setError("Enter code");
                        entercode.requestFocus();
                        return;
                    }
                    verifyCode(code);
                }
            });

            new CountDownTimer(60000,1000){

                @Override
                public void onTick(long millisUntilFinished) {

                    txt.setVisibility(View.VISIBLE);
                    txt.setText("Resend Code Within "+millisUntilFinished/1000+" Seconds");

                }

                /**
                 * Callback fired when the time is up.
                 */
                @Override
                public void onFinish() {
                    Resend.setVisibility(View.VISIBLE);
                    txt.setVisibility(View.INVISIBLE);

                }
            }.start();

            Resend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Resend.setVisibility(View.INVISIBLE);
                    Resendotp(phoneno);

                    new CountDownTimer(60000,1000){

                        @Override
                        public void onTick(long millisUntilFinished) {

                            txt.setVisibility(View.VISIBLE);
                            txt.setText("Resend Code Within "+millisUntilFinished/1000+" Seconds");

                        }

                        /**
                         * Callback fired when the time is up.
                         */
                        @Override
                        public void onFinish() {
                            Resend.setVisibility(View.VISIBLE);
                            txt.setVisibility(View.INVISIBLE);

                        }
                    }.start();
                }
            });
        }



        private void Resendotp(String phonenum) {

            sendverificationcode(phonenum);
        }



        private void sendverificationcode(String number) {

            PhoneAuthOptions options = PhoneAuthOptions.newBuilder(FAuth)
                    .setPhoneNumber(number)       // Phone number to verify
                    .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                    .setActivity(this)                 // Activity (for callback binding)
                    .setCallbacks(mcallBack)          // OnVerificationStateChangedCallbacks
                    .build();
            PhoneAuthProvider.verifyPhoneNumber(options);
        }



        private PhoneAuthProvider.OnVerificationStateChangedCallbacks mcallBack=new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                String code = phoneAuthCredential.getSmsCode();
                if(code != null){
                   // entercode.setText(code);  // Auto Verification
                    verifyCode(code);
                }
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(ChefLoginOtp.this , e.getMessage(),Toast.LENGTH_LONG).show();

            }

            @Override
            public void onCodeSent(String s , PhoneAuthProvider.ForceResendingToken forceResendingToken){
                super.onCodeSent(s,forceResendingToken);

                verificationId = s;

            }
        };

        private void verifyCode(String code) {

            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId , code);
            signInWithPhone(credential);
        }


        private void signInWithPhone(PhoneAuthCredential credential) {

            FAuth.signInWithCredential(credential)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){
                                startActivity(new Intent(ChefLoginOtp.this,ChefFoodPanel_BottomNavigation.class));
                                finish();

                            }else{
                                Toast.makeText(ChefLoginOtp.this,"Error :" + task.getException().getMessage(),Toast.LENGTH_LONG).show();
                            }

                        }
                    });

        }
}