//package com.example.planahead_capstone;
//public class Event {
//    private int id;
//    private int categoryId; // New field for category_id
//
//    private String name;
//    private String location;
//    private String date;
//    private String time;
//    private String budget;
//
//    public Event(int id, int categoryId, String name, String location, String date, String time, String budget) {
//        this.id = id;
//        this.categoryId = categoryId;
//        this.name = name;
//        this.location = location;
//        this.date = date;
//        this.time = time;
//        this.budget = budget;
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public int getCategoryId() {
//        return categoryId;
//    }
//
//    public void setCategoryId(int categoryId) {
//        this.categoryId = categoryId;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public String getLocation() {
//        return location;
//    }
//
//    public String getDate() {
//        return date;
//    }
//
//    public String getTime() {
//        return time;
//    }
//
//    public String getBudget() {
//        return budget;
//    }
//
//    public void setDate(String date) {
//        this.date = date;
//    }
//}
package com.example.planahead_capstone;

public class Event {
    private int eventId;
    private String eventName;
    private String eventLocation;
    private String eventDate;
    private String eventTime;
    private String eventBudget;

    public Event(int eventId, String eventName, String eventLocation, String eventDate, String eventTime, String eventBudget) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventLocation = eventLocation;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        this.eventBudget = eventBudget;
    }

    public int getEventId() {
        return eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public String getEventDate() {
        return eventDate;
    }

    public String getEventTime() {
        return eventTime;
    }

    public String getEventBudget() {
        return eventBudget;
    }
}
