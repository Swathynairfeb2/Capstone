package com.example.planahead_capstone;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EventDetails implements Parcelable {

    private String eventId;
    private String eventName;
    private String eventLocation;
    private String eventDate;
    private String eventTime;
    private String eventBudget;
    private String countdownText; // Modified field for countdown text

    public EventDetails(String eventId, String eventName, String eventLocation, String eventDate, String eventTime, String eventBudget) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventLocation = eventLocation;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        this.eventBudget = eventBudget;
    }

    protected EventDetails(Parcel in) {
        eventId = in.readString();
        eventName = in.readString();
        eventLocation = in.readString();
        eventDate = in.readString();
        eventTime = in.readString();
        eventBudget = in.readString();
    }

    public static final Creator<EventDetails> CREATOR = new Creator<EventDetails>() {
        @Override
        public EventDetails createFromParcel(Parcel in) {
            return new EventDetails(in);
        }

        @Override
        public EventDetails[] newArray(int size) {
            return new EventDetails[size];
        }
    };

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }



    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public String getEventBudget() {
        return eventBudget;
    }

    public String getCountdownText() {
        return countdownText;
    }

    public void setCountdownText(String countdownText) {
        this.countdownText = countdownText;
    }
    public void setEventBudget(String eventBudget) {
        this.eventBudget = eventBudget;
    }

    public Date getEventDateTime() {
        String dateTimeString = eventDate + " " + eventTime;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        try {
            return format.parse(dateTimeString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(eventId);
        dest.writeString(eventName);
        dest.writeString(eventLocation);
        dest.writeString(eventDate);
        dest.writeString(eventTime);
        dest.writeString(eventBudget);
    }
}
