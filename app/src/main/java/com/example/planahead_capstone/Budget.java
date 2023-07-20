package com.example.planahead_capstone;

public class Budget {

    private String name;
    private double amount;
    private int id;
    private String eventID;



    public Budget(int BudgetId,String name, double amount,String evnetId) {
        this.name = name;
        this.amount = amount;
        this.id = BudgetId;
        this.eventID=evnetId;

    }

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
    public String  getEventId () {return eventID; };

    public int getId() {
        return id;
    }
}