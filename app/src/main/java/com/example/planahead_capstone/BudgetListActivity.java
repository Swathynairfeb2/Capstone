//BudgetListActivity.java
package com.example.planahead_capstone;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

import com.example.planahead_capstone.Budget;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.util.ArrayList;
import java.util.List;

public class BudgetListActivity extends Activity {

    private ListView budgetListView;
    private Button addBudgetButton;
    private CircularProgressIndicator circularProgressBar;
    private TextView percentageTextView;
    private TextView overBudgetTextView;
    private Double totalSpent = 0.0;
    private List<Budget> budget;
    private BudgetTaskAdapter adapter;
    private DatabaseHelper categoryDBHelper;
    private String eventId;
    private Double realBudget;
    private Double percentage;
    private boolean isOverBudget = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.budget_page);
        Intent intent = getIntent();
        categoryDBHelper = new DatabaseHelper(this);

        if (intent != null) {
            eventId = String.valueOf(intent.getIntExtra("eventId", 0));
            realBudget = intent.getDoubleExtra("budget", 0);
        }

        budgetListView = findViewById(R.id.budgetListView);
        addBudgetButton = findViewById(R.id.addBudgetButton);
        circularProgressBar = findViewById(R.id.circularProgressBar);
        percentageTextView = findViewById(R.id.percentageTextView);
        overBudgetTextView = findViewById(R.id.overBudgetTextView);

        budget = new ArrayList<>();

        adapter = new BudgetTaskAdapter(this, budget, eventId, realBudget);
        adapter.setAdapterCallback(new BudgetTaskAdapter.BudgetAdapterCallback() {
            @Override
            public void onBudgetDeleted() {
                updateTotalSpent();
                updateProgressBar();
            }

            @Override
            public void onBudgetEdited() {
                updateTotalSpent();
                updateProgressBar();
            }
        });
        budgetListView.setAdapter(adapter);

        addBudgetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the AddTaskActivity to add a new task
                Intent intent = new Intent(BudgetListActivity.this, AddBudgetActivity.class);
                intent.putExtra("realbudget", realBudget);
                startActivityForResult(intent, 1);
            }
        });

        budgetListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Budget budget1 = budget.get(position);
                Toast.makeText(BudgetListActivity.this, "Clicked: " + budget1.getName() + ", Amount: " + budget1.getAmount(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateTotalSpent();
        updateProgressBar();
    }
    public void updateProgressBar() {
        calculatePercentage();
        circularProgressBar.setProgress(percentage.intValue());

        // Show Over Budget message and change text color
        if (percentage > 100 && !isOverBudget) {
            isOverBudget = true;
            double overBudgetAmount = totalSpent - realBudget;
            String overBudgetText = "Over Budget by $" + String.format("%.2f", overBudgetAmount);
            overBudgetTextView.setVisibility(View.VISIBLE);
            overBudgetTextView.setTextColor(Color.parseColor("#EC5231"));
            circularProgressBar.setIndicatorColor(Color.parseColor("#EC5231"));
            overBudgetTextView.setText(overBudgetText);
            animateOverBudget();
        } else if (percentage <= 100) {
            isOverBudget = false;
            circularProgressBar.setIndicatorColor(ContextCompat.getColor(this, R.color.progress));
            overBudgetTextView.setVisibility(View.GONE);
            percentageTextView.setTextColor(Color.WHITE); // Reset the text color
        }

        String percentageText = String.format("%.2f", percentage) + "%";
        percentageTextView.setText(percentageText);
    }

    private void animateOverBudget() {
        Animation anim = new AlphaAnimation(1.0f, 0.0f);
        anim.setDuration(500); // Change duration as needed
        anim.setRepeatCount(10); // Flash  times
        anim.setRepeatMode(Animation.REVERSE);
        percentageTextView.setTextColor(Color.parseColor("#EC5231"));
        percentageTextView.startAnimation(anim);
    }

    private void calculatePercentage() {
        if (realBudget != 0) {
            percentage = (totalSpent / realBudget) * 100;
        } else {
            percentage = 0.0;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            String budgetName = data.getStringExtra("budgetName");
            double budgetAmount = data.getDoubleExtra("budgetAmount", 0.0);
            int budgetId = data.getIntExtra("budgetId", -1); //new
            totalSpent += budgetAmount;

            updateProgressBar();
            updateTotalSpent();

            if (budgetName != null && !budgetName.isEmpty()) {
                long id = categoryDBHelper.insertBudget(budgetName, budgetAmount, eventId);
                if (id != -1) {
                    Budget budget = new Budget((int) id, budgetName, budgetAmount, eventId);
                    adapter.addBudget(budget);
                    updateTotalSpent(); // Update totalSpent after adding a budget
                }
            }
        }
    }

    public void updateTotalSpent() {
        totalSpent = 0.0;
        for (Budget budget : budget) {
            totalSpent += budget.getAmount();
        }
        updateProgressBar();
    }
}
