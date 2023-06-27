package com.example.planahead_capstone;

public class TodoTask {
    private long id;
    private String name;
    private boolean completed;
    private String eventId;

    public TodoTask(long id, String name, boolean completed, String eventId) {
        this.id = id;
        this.name = name;
        this.completed = completed;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
    public void setEventId(String eventId) {
        this.eventId = eventId;
    }
}
