package com.example.planahead_capstone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.planahead_capstone.DatabaseHelper;
import com.google.android.material.textfield.TextInputLayout;

public class SignUp extends AppCompatActivity {
    Button signUp, signIn;
    TextInputLayout username, password, cpassword;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);

        databaseHelper = new DatabaseHelper(this);

        signUp = findViewById(R.id.signup);
        signIn = findViewById(R.id.login);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        cpassword = findViewById(R.id.repassword);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getEditText().getText().toString().trim();
                String pwd = password.getEditText().getText().toString().trim();
                String cnf_pwd = cpassword.getEditText().getText().toString().trim();

                if (user.isEmpty() || pwd.isEmpty() || cnf_pwd.isEmpty()) {
                    Toast.makeText(SignUp.this, "Please fill the form", Toast.LENGTH_SHORT).show();
                } else {
                    if (pwd.equals(cnf_pwd)) {
                        boolean success = registerUser(user, pwd, cnf_pwd);
                        if (success) {
                            Toast.makeText(SignUp.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignUp.this, Login.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(SignUp.this, "Registration failed. Only one user allowed.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(SignUp.this, "Please enter the same password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp.this, Login.class);
                startActivity(intent);
            }
        });
    }

    public boolean registerUser(String username, String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match. Registration failed.", Toast.LENGTH_SHORT).show();
            return false; // Passwords don't match, registration failed
        }

        // Check if a user already exists in the table
        int userCount = databaseHelper.getUsersCount();
        if (userCount > 0) {
            Toast.makeText(this, "Another user already exists. Only one user is allowed.", Toast.LENGTH_SHORT).show();
            return false; // Another user already exists, registration failed
        }

        boolean success = databaseHelper.insertUser(username, password);
        if (success) {
            Toast.makeText(this, "Registered successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SignUp.this, Login.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show();
        }

        return success;
    }


}
