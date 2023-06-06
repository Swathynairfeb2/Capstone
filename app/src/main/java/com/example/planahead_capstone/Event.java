package com.example.planahead_capstone;

public class Event {
    private long id;
    private String name;
    // Add more event details as needed

    public Event(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    // Add getters and setters for other event details
}
