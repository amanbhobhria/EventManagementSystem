package com.example.eventmanagementsystem.model;

public class EventsModel {
    private String eventId;
    private String eventName;
    private String eventDesc;
    private String eventDate;
    private String eventVenue;


public EventsModel(String eventId,String eventName,String eventDesc,String eventDate,String eventVenue)
{
    this.eventId=eventId;
    this.eventName=eventName;
    this.eventDesc=eventDesc;
    this.eventDate=eventDate;
    this.eventVenue=eventVenue;

}
}