package com.example.planahead_capstone;

import java.io.Serializable;

public class EventCategory implements Serializable {
    private int eventId;
    private int categoryId;

    public EventCategory(int eventId, int categoryId) {
        this.eventId = eventId;
        this.categoryId = categoryId;
    }

    public int getEventId() {
        return eventId;
    }

    public int getCategoryId() {
        return categoryId;
    }
}
