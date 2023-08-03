package com.example.planahead_capstone;
public class UserEvent {
    private int userId;
    private EventDetails eventDetails;

    public UserEvent(int userId, EventDetails eventDetails) {
        this.userId = userId;
        this.eventDetails = eventDetails;
    }

    public int getUserId() {
        return userId;
    }

    public EventDetails getEventDetails() {
        return eventDetails;
    }
}
