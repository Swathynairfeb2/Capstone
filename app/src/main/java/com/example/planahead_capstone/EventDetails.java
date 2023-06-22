package com.example.planahead_capstone;

public class EventDetails {
    private String eventId;
    private String eventName;
    private String eventLocation;
    private String eventDate;
    private String eventTime;
    private String eventBudget;

    public EventDetails(String eventId,String eventName, String eventLocation, String eventDate, String eventTime, String eventBudget) {
        this.eventId=eventId;
        this.eventName = eventName;
        this.eventLocation = eventLocation;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        this.eventBudget = eventBudget;
    }

    public String getEventId() {
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
