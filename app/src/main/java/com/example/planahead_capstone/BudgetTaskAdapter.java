package com.example.planahead_capstone;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class BudgetTaskAdapter extends ArrayAdapter<Budget> {

    private Context context;
    private List<Budget> budgets;

    public BudgetTaskAdapter(Context context, List<Budget> budgets) {
        super(context, 0, budgets);
        this.context = context;
        this.budgets = budgets;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.budget_item, parent, false);
        }

        Budget budget = budgets.get(position);

        TextView budgetNameTextView = view.findViewById(R.id.budgetNameTextView);
        budgetNameTextView.setText(budget.getName());
        budgetNameTextView.setTextSize(25);



        TextView budgetAmountTextView = view.findViewById(R.id.budgetAmountTextView);
        budgetAmountTextView.setText(String.valueOf(budget.getAmount()));
        budgetAmountTextView.setTextSize(25);

        ImageView deleteButton = view.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                budgets.remove(position);
                notifyDataSetChanged();
            }
        });
        ImageView editImageView = view.findViewById(R.id.editButton);
        editImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve the budget object at the given position
                Budget budget = budgets.get(position);

                // Perform the edit operation for the budget
                performEditOperation(budget);
            }
        });



        return view;
    }
    private void performEditOperation(final Budget budget) {
        // Create a dialog or custom view for editing the budget name and amount


        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Edit Budget");

        // Inflate the layout for the dialog
        View dialogView = LayoutInflater.from(context).inflate(R.layout.edit_budget, null);
        builder.setView(dialogView);

        final EditText budgetNameEditText = dialogView.findViewById(R.id.editAmount);
        final EditText budgetAmountEditText = dialogView.findViewById(R.id.editAmount);

        // Set the current budget name and amount in the EditText fields
        budgetNameEditText.setText(budget.getName());
        budgetAmountEditText.setText(String.valueOf(budget.getAmount()));

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Get the updated budget name and amount from the EditText fields
                String updatedName = budgetNameEditText.getText().toString();
                double updatedAmount = Double.parseDouble(budgetAmountEditText.getText().toString());

                // Update the budget object
                budget.setName(updatedName);
                budget.setAmount(updatedAmount);

                // Notify the adapter that the data has changed
                notifyDataSetChanged();
            }
        });

        builder.setNegativeButton("Cancel", null);

        // Create and show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
