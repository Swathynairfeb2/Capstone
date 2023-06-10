package com.example.planahead_capstone;//package com.example.planahead_capstone;
//
//public class Category {
//    private int id;
//    private String categoryName;
//
//    public Category(int id, String categoryName) {
//        this.id = id;
//        this.categoryName = categoryName;
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public String getCategoryName() {
//        return categoryName;
//    }
//}
import java.io.Serializable;

public class Category implements Serializable {
    private int id;
    private String categoryName;

    public Category(int id, String categoryName) {
        this.id = id;
        this.categoryName = categoryName;
    }

    public int getId() {
        return id;
    }

    public String getCategoryName() {
        return categoryName;
    }
}
