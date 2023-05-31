package com.example.planahead_capstone;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

public class SignUp extends AppCompatActivity {
    Button signUp,signIn;
    ImageView logo;
    TextView slogan,text;
    TextInputLayout username,password,cpassword;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        signUp = findViewById(R.id.signup);
        signIn = findViewById(R.id.login);
        logo = findViewById(R.id.logoImage);
        text = findViewById(R.id.logoName);
        slogan = findViewById(R.id.slogan);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        cpassword = findViewById(R.id.repassword);



        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getEditText().getText().toString().trim();
                String pwd = password.getEditText().getText().toString().trim();
                String cnf_pwd = cpassword.getEditText().getText().toString().trim();

                // check cnf password and register

                if(user.equals("") || pwd.equals("") || cnf_pwd.equals("")){
                    Toast.makeText(SignUp.this, "Please Fill the form", Toast.LENGTH_SHORT).show();
                }
                else {

                    if (pwd.equals(cnf_pwd)) {
                            Toast.makeText(SignUp.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                            Intent moveToLogin = new Intent(SignUp.this, Login.class);
                            startActivity(moveToLogin);


                    } else {
                        Toast.makeText(SignUp.this, "Please enter Same Password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });





        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp.this,Login.class);
                Pair[] pairs = new Pair[7];
                pairs[0] = new Pair<View,String>(logo,"logo_image");
                pairs[1] = new Pair<View,String>(text,"logo_text");
                pairs[2] = new Pair<View,String>(slogan,"slogan");
                pairs[3] = new Pair<View,String>(username,"username");
                pairs[4] = new Pair<View,String>(password,"password");
                pairs[5] = new Pair<View,String>(signUp,"main");
                pairs[6] = new Pair<View,String>(signIn,"sub");

                ActivityOptions options = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    options = ActivityOptions.makeSceneTransitionAnimation(SignUp.this,pairs);
                }
                startActivity(intent,options.toBundle());
            }
        });
    }
}