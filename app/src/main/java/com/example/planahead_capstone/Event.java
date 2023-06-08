package com.example.planahead_capstone;

public class Event {
    private int id;
    private String name;
    private String location;
    private String date;
    private String time;
    private String budget;

    public Event(int id, String name, String location, String date, String time, String budget) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.date = date;
        this.time = time;
        this.budget = budget;
    }

    public int getId() {
        return id;
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
