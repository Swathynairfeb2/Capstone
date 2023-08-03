package com.example.planahead_capstone;
public class ThemeIdea {
    private String title;
    private String description;
    private int imageResource; // Resource ID for the image

    public ThemeIdea(String title, String description, int imageResource) {
        this.title = title;
        this.description = description;
        this.imageResource = imageResource;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getImageResource() {
        return imageResource;
    }
}
