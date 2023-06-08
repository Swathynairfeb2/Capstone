package com.example.planahead_capstone;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
public class CategoryActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonWedding;
    private Button buttonBirthday;
    private Button buttonBaptism;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        buttonWedding = findViewById(R.id.button2);
        buttonBirthday = findViewById(R.id.button3);
        buttonBaptism = findViewById(R.id.button4);

        buttonWedding.setOnClickListener(this);
        buttonBirthday.setOnClickListener(this);
        buttonBaptism.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        Intent intent = new Intent(CategoryActivity.this, EventCreationActivity.class);

        if (id == R.id.button2) {
            intent.putExtra("category", "Wedding");
        } else if (id == R.id.button3) {
            intent.putExtra("category", "Birthday");
        } else if (id == R.id.button4) {
            intent.putExtra("category", "Baptism");
        }

        startActivity(intent);
    }
}
