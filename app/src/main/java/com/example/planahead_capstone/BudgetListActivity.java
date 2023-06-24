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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class BudgetListActivity extends Activity {

    private ListView budgetListView;
    private Button addBudgetButton;

    private List<Budget> budget;
    private BudgetTaskAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.budget_page);

        budgetListView = findViewById(R.id.budgetListView);
        addBudgetButton = findViewById(R.id.addBudgetButton);

        budget = new ArrayList<>();

        adapter = new BudgetTaskAdapter(this, budget);
        budgetListView.setAdapter(adapter);

        addBudgetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the AddTaskActivity to add a new task
                Intent intent = new Intent(BudgetListActivity.this, AddBudgetActivity.class);
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

        budgetListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                budget.remove(position);
                adapter.notifyDataSetChanged();
                return true;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            String budgetName = data.getStringExtra("budgetName");
            double budgetAmount = data.getDoubleExtra("budgetAmount", 0.0);

            if (budgetName != null && !budgetName.isEmpty()) {
                Budget budget1 = new Budget(budgetName, budgetAmount);
                budget.add(budget1);
                adapter.notifyDataSetChanged();
            }
        }
    }
}
