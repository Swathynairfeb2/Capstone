package com.example.planahead_capstone;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class BudgetTaskAdapter extends ArrayAdapter<Budget> {

    private Context context;
    private List<Budget> budgets;
    private DatabaseHelper db;
    private String eventid;
    public Double percentage;
    public Double realBudget;
    public Double totalSpent;
    public int per;

    BudgetListActivity Budget;


    public BudgetTaskAdapter(Context context, List<Budget> budgets,String eventid,Double realBudget) {
        super(context, 0, budgets);
        this.context = context;
        this.budgets = budgets;
        this.db = new DatabaseHelper(context);
        this.eventid=eventid;
        this.realBudget=realBudget;
        loadFromDatabse();
    }

    private void loadFromDatabse() {
        budgets.clear(); // Clear the existing budgets list
        totalSpent = 0.0;


        SQLiteDatabase DB = db.getReadableDatabase();

        String[] columns = {DatabaseHelper.COLUMN_BUDGET_ID, DatabaseHelper.COLUMN_BUDGET_CATEGORY_NAME, DatabaseHelper.COLUMN_BUDGET_AMOUNT};
        String selection = DatabaseHelper.COLUMN_BUDGET_EVENT_ID + " = ?";
        String[] selectionArgs = {String.valueOf(eventid)};
        Cursor cursor = DB.query(DatabaseHelper.TABLE_BUDGET, columns, selection, selectionArgs, null, null, null);

        while (cursor.moveToNext()) {
            int budgetIdColumnIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_BUDGET_ID);
            int budgetNameColumnIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_BUDGET_CATEGORY_NAME);
            int budgetAmountColumnIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_BUDGET_AMOUNT);


            int BudgetId = cursor.getInt(budgetIdColumnIndex);
            String Name = cursor.getString(budgetNameColumnIndex);
            Double Amount = cursor.getDouble(budgetAmountColumnIndex);



            Budget budget = new Budget(BudgetId, Name, Amount, eventid);
            budgets.add(budget);
            // Add the budget amount to the total spent
            totalSpent += Amount;
        }

        cursor.close();
        DB.close();

        notifyDataSetChanged(); // Notify the adapter that the data has changed
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.budget_item, parent, false);
        }

        Budget budget = budgets.get(position);
        int budgetid= budget.getId();
        per = updateProgressBar();

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
                db.deleteBudget(budgetid);
                loadFromDatabse();
                per = updateProgressBar();
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
                per = updateProgressBar();
            }
        });



        return view;
    }
    private int updateProgressBar() {
        percentage = (totalSpent / realBudget) * 100;
        return percentage.intValue();
    }
    private void performEditOperation(final Budget budget) {

        db = new DatabaseHelper(this.getContext());


        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Edit Budget");


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

                db.updateBudget(id,updatedName, updatedAmount);
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

}