package com.example.planahead_capstone;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

        holder.categoryOptionImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (categoryClickListener != null) {
                    categoryClickListener.onCategoryOptionClick(category);
                }
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
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
        TextView categoryNameTextView;
        ImageView categoryOptionImageView;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryNameTextView = itemView.findViewById(R.id.categoryNameTextView);
            categoryOptionImageView = itemView.findViewById(R.id.categoryoption);
        }
    }

    public interface CategoryClickListener {
        void onCategoryClick(Category category);
        void onCategoryOptionClick(Category category);
    }
}
