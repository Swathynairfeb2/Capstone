package com.example.planahead_capstone;
public class Event {
    private int id;
    private int categoryId; // New field for category_id

    private String name;
    private String location;
    private String date;
    private String time;
    private String budget;

    public Event(int id, int categoryId, String name, String location, String date, String time, String budget) {
        this.id = id;
        this.categoryId = categoryId;
        this.name = name;
        this.location = location;
        this.date = date;
        this.time = time;
        this.budget = budget;
    }

    public int getId() {
        return id;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getBudget() {
        return budget;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
