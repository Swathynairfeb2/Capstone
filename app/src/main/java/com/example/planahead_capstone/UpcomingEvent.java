package com.example.planahead_capstone;

public class UpcomingEvent {
    private String eventName;
    private String eventDate;
    private int imageResource;
    public UpcomingEvent(String eventName, String eventDate,int imageResource) {
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.imageResource = imageResource;


    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public int getImageResource() {
        return imageResource;
    }
}
