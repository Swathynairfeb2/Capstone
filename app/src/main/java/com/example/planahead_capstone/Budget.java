package com.example.planahead_capstone;
public class Budget {
    private String name;
    private double amount;
    private int id;

private String eventId;

public Budget(int id, String name, double amount, String eventId) {
    this.id = id;
    this.name = name;
    this.amount = amount;
    this.eventId = eventId;
}

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }

    public int getId() {
        return id;
    }

//    public int getEventId() {
public String getEventId() {
        return eventId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

//    public void setEventId(int eventId) {
public void setEventId(String eventId) {
        this.eventId = eventId;
    }
}
