package com.example.eventmanagementsystem


import android.os.Bundle
import android.widget.Button

import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.eventmanagementsystem.model.EventsModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class AdminActivity : AppCompatActivity(){

    private lateinit var eventNameEditText: EditText
    private lateinit var eventCodeEditText: EditText
    private lateinit var venueEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var dateEditText: EditText


    var firebaseDatabase: FirebaseDatabase? = null
    var referenceHm: DatabaseReference? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        firebaseDatabase = FirebaseDatabase.getInstance()
        referenceHm = firebaseDatabase!!.getReference("events")



        eventNameEditText = findViewById(R.id.edit_text_event)
        eventCodeEditText = findViewById(R.id.edit_text_event_code)
        venueEditText = findViewById(R.id.edit_text_venue)
        descriptionEditText = findViewById(R.id.edit_text_description)
        dateEditText = findViewById(R.id.edit_text_date)
//        dateEditText.setOnClickListener { showDatePicker() }

        val createEventButton: Button = findViewById(R.id.button_create_event)
        createEventButton.setOnClickListener { createEvent() }
    }

//    private fun showDatePicker() {
//        val calendar = Calendar.getInstance()
//        val year = calendar.get(Calendar.YEAR)
//        val month = calendar.get(Calendar.MONTH)
//        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
//        DatePickerDialog(this, this, year, month, dayOfMonth).show()
//    }
//
//    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
//        val calendar = Calendar.getInstance()
//        calendar.set(year, month, dayOfMonth)
//        val selectedDate = calendar.time
//        val formattedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(selectedDate)
//        dateEditText.setText(formattedDate)
//    }

    private fun createEvent() {
        val eventName = eventNameEditText.text.toString()
        val eventCode = eventCodeEditText.text.toString()
        val venue = venueEditText.text.toString()
        val description = descriptionEditText.text.toString()
        val date = dateEditText.text.toString()

        if (eventName.isEmpty() || eventCode.isEmpty() || venue.isEmpty() || description.isEmpty() || date.isEmpty()) {
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            return
        }

        // Get the selected date from the date picker
//        val selectedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(date)

        // Create a new event object
//        val event = Event(eventName, eventCode, venue, description, selectedDate)

        // TODO: Save the event to the database

        val eventsModel: EventsModel= EventsModel();
        eventsModel.eventDate=date
        eventsModel.eventVenue=venue
        eventsModel.eventName=eventName
        eventsModel.eventId=eventCode
        eventsModel.eventDesc=description

        referenceHm!!.child("unievent").setValue(eventsModel)

        Toast.makeText(this, "Event created successfully!", Toast.LENGTH_SHORT).show()

    }




}

//class Event(eventName: String, eventCode: String, venue: String, description: String, selectedDate: Date?) {
//
//}
