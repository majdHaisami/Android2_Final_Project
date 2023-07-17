package com.example.appfirebase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Mainmenue extends AppCompatActivity {
Button siginemail,siginphone,sigup;
ImageView bgimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenue);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
final Animation zoomin = AnimationUtils.loadAnimation(this,R.anim.zoomin);
final Animation zoomout=AnimationUtils.loadAnimation(this,R.anim.zoomout);
    bgimage=findViewById(R.id.back2);
    bgimage.setAnimation(zoomin);
    bgimage.setAnimation(zoomout);

zoomout.setAnimationListener(new Animation.AnimationListener() {
    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
bgimage.setAnimation(zoomin);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
});
zoomout.setAnimationListener(new Animation.AnimationListener() {
    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
bgimage.setAnimation(zoomout);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
});
siginemail=(Button)findViewById(R.id.SigneithEmail);
siginphone=(Button)findViewById(R.id.Signeithphone);
sigup=(Button)findViewById(R.id.Sigiup);

siginemail.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent siginemail = new Intent(Mainmenue.this ,ChefLogin.class );
        siginemail.putExtra("Home","Email");
        startActivity(siginemail);
        finish();
    }
});
siginphone.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent siginphone = new Intent(Mainmenue.this,phonelogin.class);
        siginphone.putExtra("Home" , "Phone");
        startActivity(siginphone);
        finish();
    }
});
//sigup.setOnClickListener(new View.OnClickListener() {
//    @Override
//    public void onClick(View v) {
//        Intent sigup=new Intent(Mainmenue.this,Choseone.class);
//        sigup.putExtra("Home", "Signup");
//        startActivity(sigup);
//        finish();
//    }
//});
    }
}