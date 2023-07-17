package com.example.appfirebase;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    ImageView imageView ; TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
if (getSupportActionBar() != null){
    getSupportActionBar().hide();
}

imageView = findViewById(R.id.imagemain);
textView=findViewById(R.id.textViewmain);
imageView.animate().alpha(0f).setDuration(0);
textView.animate().alpha(0f).setDuration(0);
imageView.animate().alpha(1f).setDuration(1000).setListener(new Animator.AnimatorListener() {


    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {
    textView.animate().alpha(1f).setDuration(800);
    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }


});
new Handler().postDelayed(new Runnable() {
    @Override
    public void run() {
        Intent intent = new Intent(MainActivity.this,Mainmenue.class);
        startActivity(intent);
        finish();
    }
},3000);



    }
}
