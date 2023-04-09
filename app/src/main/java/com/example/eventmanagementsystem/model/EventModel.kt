package com.example.eventmanagementsystem.model

class EventModel {
    var eventId: String? = null
    var eventName: String? = null
    var eventDesc: String? = null
    var eventDate: String? = null
    var eventVenue: String? = null

    constructor(
        eventId: String?,
        eventName: String?,
        eventDesc: String?,
        eventDate: String?,
        eventVenue: String?
    ) {
        this.eventId = eventId
        this.eventName = eventName
        this.eventDesc = eventDesc
        this.eventDate = eventDate
        this.eventVenue = eventVenue
    }


    constructor() {}
}