package com.example.planahead_capstone;
import android.os.Parcel;
import android.os.Parcelable;

public class UpcomingEvent implements Parcelable {
    private String eventId;
    private String eventName;
    private String eventDate;
    private String eventLocation;
    private String eventTime;
    private String eventBudget;
    private int imageResource;
    private String countdownText; // Modified field for countdown text

    public UpcomingEvent(String eventId, String eventName, String eventLocation,String eventDate , String eventTime, String eventBudget, int imageResource) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.imageResource = imageResource;
        this.eventLocation = eventLocation;
        this.eventTime = eventTime;
        this.eventBudget = eventBudget;
    }

    protected UpcomingEvent(Parcel in) {
        eventId = in.readString();
        eventName = in.readString();
        eventDate = in.readString();
        eventLocation = in.readString();
        eventTime = in.readString();
        eventBudget = in.readString();
        imageResource = in.readInt();
        countdownText = in.readString(); // Modified field for countdown text
    }

    public static final Creator<UpcomingEvent> CREATOR = new Creator<UpcomingEvent>() {
        @Override
        public UpcomingEvent createFromParcel(Parcel in) {
            return new UpcomingEvent(in);
        }

        @Override
        public UpcomingEvent[] newArray(int size) {
            return new UpcomingEvent[size];
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

    public void setEventBudget(String eventBudget) {
        this.eventBudget = eventBudget;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public String getCountdownText() {
        return countdownText;
    }

    public void setCountdownText(String countdownText) {
        this.countdownText = countdownText;
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
        dest.writeInt(imageResource);
        dest.writeString(countdownText); // Modified field for countdown text
    }
}
