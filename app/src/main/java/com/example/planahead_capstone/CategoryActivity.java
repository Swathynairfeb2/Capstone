package com.example.planahead_capstone;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity implements CategoryAdapter.CategoryClickListener {

    private List<Category> categoryList;
    private CategoryAdapter categoryAdapter;
    private ImageView addCategoryImageView;
    private DatabaseHelper categoryDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        categoryDBHelper = new DatabaseHelper(this);

        // Bottom Navigation View
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(navItemSelectedListener);

        categoryList = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(categoryList);
        categoryAdapter.setCategoryClickListener(this);

        RecyclerView categoryRecyclerView = findViewById(R.id.categoryRecyclerView);
        categoryRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        categoryRecyclerView.setAdapter(categoryAdapter);

        addCategoryImageView = findViewById(R.id.addCategoryImageView);
        addCategoryImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddCategoryDialog();
            }
        });

        loadCategories();
    }

    private void loadCategories() {
        categoryList.clear();

        // Add default categories to the database if they don't exist
        addDefaultCategoriesIfNotExists();

        // Load categories from the database
        categoryList.addAll(categoryDBHelper.getAllCategories());
        categoryAdapter.notifyDataSetChanged();
    }

    private void addDefaultCategoriesIfNotExists() {
        List<Category> defaultCategories = new ArrayList<>();
        defaultCategories.add(new Category(1, "Wedding"));
        defaultCategories.add(new Category(2, "Birthday"));
        defaultCategories.add(new Category(3, "Wedding Anniversary"));
        defaultCategories.add(new Category(4, "Baptism"));

        List<Category> existingCategories = categoryDBHelper.getAllCategories();

        for (Category defaultCategory : defaultCategories) {
            boolean exists = false;
            for (Category existingCategory : existingCategories) {
                if (defaultCategory.getCategoryName().equals(existingCategory.getCategoryName())) {
                    exists = true;
                    break;
                }
            }

            if (!exists) {
                categoryDBHelper.addCategory(defaultCategory.getCategoryName());
            }
        }
    }

    private void showAddCategoryDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.popup_add_category);

        EditText categoryNameEditText = dialog.findViewById(R.id.categoryNameEditText);
        Button addButton = dialog.findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String categoryName = categoryNameEditText.getText().toString();
                if (!categoryName.isEmpty()) {
                    categoryDBHelper.addCategory(categoryName);
                    loadCategories();
                    dialog.dismiss();
                }
            }
        });

        dialog.show();
    }

    @Override
    public void onCategoryClick(Category category) {
        Intent intent = new Intent(this, EventCreationActivity.class);
        intent.putExtra("categoryId", category.getId()); // Pass the category ID
        startActivity(intent);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(MenuItem item) {
                    Fragment selectedFragment = null;
                    Intent intent;

                    switch (item.getItemId()) {
                        case R.id.menu_home:
                            // Handle the home action
                            intent = new Intent(CategoryActivity.this, HomeActivity.class);
                            startActivity(intent);
                            break;
                        case R.id.menu_categories:
                            // Handle the categories action
                            intent = new Intent(CategoryActivity.this, CategoryActivity.class);
                            startActivity(intent);
                            break;
                        case R.id.menu_my_events:
                            // Start the EventCreationActivity
                            intent = new Intent(CategoryActivity.this, EventsActivity.class);
                            startActivity(intent);
                            break;
                        case R.id.menu_my_account:
                            // Handle the my account action
                            Toast.makeText(CategoryActivity.this, "My Account", Toast.LENGTH_SHORT).show();
                            break;
                    }

                    return true;
                }
            };
}


