package com.example.planahead_capstone;

public class TodoTask {

    private String name;
    private boolean completed;

    public TodoTask(String name, boolean completed) {
        this.name = name;
        this.completed = completed;
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
}
