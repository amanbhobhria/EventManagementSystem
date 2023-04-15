package com.example.eventmanagementsystem.model;

public class EventsModel{
    private String eventId;
    private String eventName;
    private String eventDesc;
    private String eventDate;
    private String eventVenue;







    public EventsModel(String eventId, String eventName, String eventDesc, String eventDate, String eventVenue)
{
    this.eventId=eventId;
    this.eventName=eventName;
    this.eventDesc=eventDesc;
    this.eventDate=eventDate;
    this.eventVenue=eventVenue;

}

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

    public String getEventDesc() {
        return eventDesc;
    }

    public void setEventDesc(String eventDesc) {
        this.eventDesc = eventDesc;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventVenue() {
        return eventVenue;
    }

    public void setEventVenue(String eventVenue) {
        this.eventVenue = eventVenue;
    }

    public EventsModel() {
    }
}