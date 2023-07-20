package com.example.planahead_capstone;

public class Guest {

    private String name;
    private String email;
    private String phone;
    private String eventId;

    public Guest(String name, String email, String phone, String eventId, String id) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.eventId = eventId;
    }



    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getEventId() {
        return eventId;
    }

    public long setGuestId(long guestId) {
        return guestId;
    }
}
