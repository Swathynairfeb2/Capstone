package com.example.planahead_capstone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AddBudgetActivity extends AppCompatActivity {

    private EditText budgetNameEditText;
    private EditText budgetAmountEditText;
    private Button addBudgetButton;
    private Button cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_budget);

        budgetNameEditText = findViewById(R.id.categoryEditText);
        budgetAmountEditText = findViewById(R.id.budgetAmountEditText);
        addBudgetButton = findViewById(R.id.addBudgetButton);
        cancelButton = findViewById(R.id.cancelButton);

        addBudgetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String budgetName = budgetNameEditText.getText().toString();
                String budgetAmount = budgetAmountEditText.getText().toString();
                if (!budgetName.isEmpty() && !budgetAmount.isEmpty()) {
                    // Pass the budget name and amount to the BudgetListActivity
                    Intent intent = new Intent();
                    intent.putExtra("budgetName", budgetName);
                    intent.putExtra("budgetAmount", Double.parseDouble(budgetAmount));


                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Close the activity
            }
        });
    }
}

