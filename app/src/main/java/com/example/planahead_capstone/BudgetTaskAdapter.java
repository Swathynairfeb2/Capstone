// BudgetTaskAdapter.java
package com.example.planahead_capstone;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import java.util.List;

public class BudgetTaskAdapter extends ArrayAdapter<Budget> {

    private Context context;
    private List<Budget> budgets;
    private DatabaseHelper db;
    private String eventId;

    private Double realBudget;
    private Double totalSpent;
    private int per;
    private ProgressBar pgdr;
    private TextView percentageTextView;
    private BudgetListActivity budgetListActivity;
    private Double percentage;

    // Add a callback to communicate with the BudgetListActivity
    public interface BudgetAdapterCallback {
        void onBudgetDeleted();
        void onBudgetEdited();
    }

    private BudgetAdapterCallback adapterCallback;

    public void setAdapterCallback(BudgetAdapterCallback callback) {
        adapterCallback = callback;
    }


    public BudgetTaskAdapter(Context context, List<Budget> budgets, String eventId, Double realBudget) {
        super(context, 0, budgets);
        this.context = context;
        this.budgets = budgets;
        this.db = new DatabaseHelper(context);
        this.eventId = eventId;
        this.realBudget = realBudget;

        loadFromDatabase();
    }

    private void loadFromDatabase() {
        budgets.clear();
        totalSpent = 0.0;

        SQLiteDatabase DB = db.getReadableDatabase();

        String[] columns = {DatabaseHelper.COLUMN_BUDGET_ID, DatabaseHelper.COLUMN_BUDGET_CATEGORY_NAME, DatabaseHelper.COLUMN_BUDGET_AMOUNT};
        String selection = DatabaseHelper.COLUMN_BUDGET_EVENT_ID + " = ?";
        String[] selectionArgs = {String.valueOf(eventId)};
        Cursor cursor = DB.query(DatabaseHelper.TABLE_BUDGET, columns, selection, selectionArgs, null, null, null);

        while (cursor.moveToNext()) {
            int budgetIdColumnIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_BUDGET_ID);
            int budgetNameColumnIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_BUDGET_CATEGORY_NAME);
            int budgetAmountColumnIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_BUDGET_AMOUNT);

            int budgetId = cursor.getInt(budgetIdColumnIndex);
            String name = cursor.getString(budgetNameColumnIndex);
            Double amount = cursor.getDouble(budgetAmountColumnIndex);

            Budget budget = new Budget(budgetId, name, amount, eventId);
            budgets.add(budget);
            // Add the budget amount to the total spent
            totalSpent += amount;
        }
        per = updatePercentage();
        updateProgress(per);

        cursor.close();
        DB.close();

        notifyDataSetChanged(); // Notify the adapter that the data has changed
    }

    private void updateProgress(int per) {
        if (pgdr != null && percentageTextView != null) {
            pgdr.setProgress(per);

            // Change progress bar color when over budget
            if (per > 100) {
                pgdr.setProgressTintList(ColorStateList.valueOf(Color.RED));
            } else {
                pgdr.setProgressTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.progress)));
            }

            String percentageText = String.format("%.2f", (float) per) + "%";
            percentageTextView.setText(percentageText);
        } else {
            Log.e("BudgetListActivity", "horizontalProgressBar is null.");
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.budget_item, parent, false);
        }

        Budget budget = budgets.get(position);
        int budgetId = budget.getId();
        per = updatePercentage();
        percentageTextView = view.findViewById(R.id.percentageTextView);

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
                db.deleteBudget(budgetId);
                loadFromDatabase();
                per = updatePercentage();
                updateProgress(per);

                if (adapterCallback != null) {
                    adapterCallback.onBudgetDeleted(); // Notify the BudgetListActivity about budget deletion
                }
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
                per = updatePercentage();

                if (adapterCallback != null) {
                    adapterCallback.onBudgetEdited(); // Notify the BudgetListActivity about budget edit
                }
            }
        });

        return view;
    }

    private int updatePercentage() {
        if (realBudget != 0) {
            percentage = (totalSpent / realBudget) * 100;
        } else {
            percentage = 0.0;
        }
        return percentage.intValue();
    }

    private void performEditOperation(final Budget budget) {
        db = new DatabaseHelper(this.getContext());

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        // Inflate the layout for the dialog
        View dialogView = LayoutInflater.from(context).inflate(R.layout.edit_budget, null);
        builder.setView(dialogView);

        final EditText budgetNameEditText = dialogView.findViewById(R.id.editCatogery);
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
                int id = budget.getId();
                // Update the budget object
                budget.setName(updatedName);
                budget.setAmount(updatedAmount);

                // Notify the adapter that the data has changed
                notifyDataSetChanged();

                db.updateBudget(id, updatedName, updatedAmount);

                // Reload data from the database and refresh the progress bar
                loadFromDatabase();
                per = updatePercentage();
                updateProgress(per);

                // Notify the BudgetListActivity about budget edit
                if (adapterCallback != null) {
                    adapterCallback.onBudgetEdited();
                }
            }
        });

        builder.setNegativeButton("Cancel", null);

        // Create and show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void addBudget(Budget budget) {
        budgets.add(budget);
        notifyDataSetChanged();
    }

    public void updateTotalSpent() {
        totalSpent = 0.0;
        for (Budget budget : budgets) {
            totalSpent += budget.getAmount();
        }
        per = updatePercentage();
        updateProgress(per);
    }
}
