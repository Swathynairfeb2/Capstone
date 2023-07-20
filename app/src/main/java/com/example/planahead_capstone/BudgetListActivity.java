package com.example.planahead_capstone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class BudgetListActivity extends Activity {

    private ListView budgetListView;
    private Button addBudgetButton;
    private ProgressBar horizontalProgressBar;
    private TextView percentageTextView;
    public Double totalSpent = 0.0;

    private List<Budget> budget;
    private BudgetTaskAdapter adapter;
    private DatabaseHelper categoryDBHelper;
    private String eventId;
    public Double realBudget;

    public Double percentage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.budget_page);
        Intent intent = getIntent();
        categoryDBHelper = new DatabaseHelper(this);


        if (intent != null) {
            eventId = String.valueOf(intent.getIntExtra("eventId", 0));
            realBudget = intent.getDoubleExtra("budget",0);

        }


        budgetListView = findViewById(R.id.budgetListView);
        addBudgetButton = findViewById(R.id.addBudgetButton);
        horizontalProgressBar = findViewById(R.id.horizontalProgressBar);
        percentageTextView = findViewById(R.id.percentageTextView);

        budget = new ArrayList<>();

        adapter = new BudgetTaskAdapter(this, budget,eventId,realBudget);
        updateProgressBar();

        budgetListView.setAdapter(adapter);




        addBudgetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the AddTaskActivity to add a new task
                Intent intent = new Intent(BudgetListActivity.this, AddBudgetActivity.class);
                startActivityForResult(intent, 1);
                intent.putExtra("realbudget", realBudget);
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

    private void updateProgressBar() {
        calculatePercentage();
        horizontalProgressBar.setProgress((percentage.intValue()));
        String percentageText = String.format("%.2f", percentage) + "%";
        percentageTextView.setText(percentageText);
    }
    private void calculatePercentage() {
        percentage = (totalSpent / realBudget) * 100;
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

            if (budgetName != null && !budgetName.isEmpty()) {
                long id = categoryDBHelper.insertBudget(budgetName, budgetAmount, eventId);
                if (id != -1) {
                    Budget budget = new Budget((int)id, budgetName, budgetAmount, eventId);
                    adapter.addBudget(budget);


                }
            }
        }
    }
}