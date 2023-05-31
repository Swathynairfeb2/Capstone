package com.example.planahead_capstone;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity{




        private static int SPLASH_SCREEN = 3000;

        Animation topAnim,bottomAnim;
        ImageView logo;
        TextView t1,t2;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

            setContentView(R.layout.activity_splashscreen);

            topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
            bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

            logo = findViewById(R.id.splashLogo);
            t1 = findViewById(R.id.splashText1);
            t2 = findViewById(R.id.splashText2);

            logo.setAnimation(topAnim);
            t1.setAnimation(bottomAnim);
            t2.setAnimation(bottomAnim);


            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashScreen.this,Login.class);
                    Pair[] pairs = new Pair[2];
                    pairs[0] = new Pair<View,String>(logo,"logo_image");
                    pairs[1] = new Pair<View,String>(t1,"logo_text");

                    ActivityOptions options = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                        options = ActivityOptions.makeSceneTransitionAnimation(SplashScreen.this,pairs);

                    }
                    startActivity(intent,options.toBundle());
                }
            },SPLASH_SCREEN);
        }
    }
