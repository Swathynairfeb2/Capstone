package com.example.planahead_capstone;//package com.example.planahead_capstone;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.planahead_capstone.R;
//import com.example.planahead_capstone.Category;
//
//import java.util.List;
//
//public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
//
//    private List<Category> categoryList;
//
//    public CategoryAdapter(List<Category> categoryList) {
//        this.categoryList = categoryList;
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        Category category = categoryList.get(position);
//        holder.categoryNameTextView.setText(category.getCategoryName());
//    }
//
//    @Override
//    public int getItemCount() {
//        return categoryList.size();
//    }
//
//    public static class ViewHolder extends RecyclerView.ViewHolder {
//        TextView categoryNameTextView;
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            categoryNameTextView = itemView.findViewById(R.id.categoryNameEditText);
//        }
//    }
//}
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.List;
//
//public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
//
//    private List<Category> categoryList;
//
//    public CategoryAdapter(List<Category> categoryList) {
//        this.categoryList = categoryList;
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        Category category = categoryList.get(position);
//        holder.categoryNameTextView.setText(category.getCategoryName());
//    }
//
//    @Override
//    public int getItemCount() {
//        return categoryList.size();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        public TextView categoryNameTextView;
//
//        public ViewHolder(View itemView) {
//            super(itemView);
//            categoryNameTextView = itemView.findViewById(R.id.categoryNameTextView);
//        }
//    }
//}

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private List<Category> categoryList;
    private CategoryClickListener categoryClickListener;

    public CategoryAdapter(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public void setCategoryClickListener(CategoryClickListener listener) {
        this.categoryClickListener = listener;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = categoryList.get(position);
        holder.categoryNameTextView.setText(category.getCategoryName());

        holder.categoryLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (categoryClickListener != null) {
                    categoryClickListener.onCategoryClick(category);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        LinearLayout categoryLinearLayout;
        TextView categoryNameTextView;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryLinearLayout = itemView.findViewById(R.id.categoryLinearLayout);
            categoryNameTextView = itemView.findViewById(R.id.categoryNameTextView);
        }
    }

    public interface CategoryClickListener {
        void onCategoryClick(Category category);
    }
}
