package com.example.eventmanagementsystem

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import com.example.eventmanagementsystem.model.EventModel
import com.example.eventmanagementsystem.model.EventsModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*

class AdminActivity : AppCompatActivity() {

    private lateinit var eventNameEditText: EditText
    private lateinit var eventCodeEditText: EditText
    private lateinit var venueEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var dateEditText: EditText
    lateinit var homeBtn: Button
    private lateinit var createEventButton: Button
    private var selectedDate: Calendar = Calendar.getInstance()

    private var firebaseDatabase: FirebaseDatabase? = null
    private var referenceHm: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        firebaseDatabase = FirebaseDatabase.getInstance()
        referenceHm = firebaseDatabase!!.getReference("events")
        intitalize()
        createEventButton.setOnClickListener { createEvent() }
//        goToHome()
    }
    fun intitalize(){
        eventNameEditText = findViewById(R.id.edit_text_event)
        eventCodeEditText = findViewById(R.id.edit_text_event_code)
        venueEditText = findViewById(R.id.edit_text_venue)
        descriptionEditText = findViewById(R.id.edit_text_description)
        dateEditText = findViewById(R.id.edit_text_date)
//        homeBtn = findViewById(R.id.goToHomeBtn)
        createEventButton= findViewById(R.id.button_create_event)
        dateEditText.setOnClickListener { showDatePicker() }
        val datePickerButton = findViewById<ImageButton>(R.id.image_button_date_picker)
        datePickerButton.setOnClickListener { showDatePicker() }



    }

    private fun showDatePicker() {
        val datePicker = DatePickerDialog(
            this,
            { view: DatePicker?, year: Int, month: Int, dayOfMonth: Int ->
                selectedDate.set(Calendar.YEAR, year)
                selectedDate.set(Calendar.MONTH, month)
                selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateEditText()
            },
            selectedDate.get(Calendar.YEAR),
            selectedDate.get(Calendar.MONTH),
            selectedDate.get(Calendar.DAY_OF_MONTH)
        )
        datePicker.show()
    }

    private fun updateDateEditText() {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        dateEditText.setText(dateFormat.format(selectedDate.time))
    }

    private fun createEvent() {

        val eventName = eventNameEditText.text.toString().trim()
        val eventCode = eventCodeEditText.text.toString().trim()
        val venue = venueEditText.text.toString().trim()
        val description = descriptionEditText.text.toString().trim()
        val date = dateEditText.text.toString().trim()

        if (eventName.isEmpty() || eventCode.isEmpty() || venue.isEmpty() || description.isEmpty() || date.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val eventModel = EventModel(eventCode,eventName, description,date,venue)
//        val reference = referenceHm!!.push()
//        val eventId = reference.key // get the unique identifier
        referenceHm?.child(eventCode)?.setValue(eventModel)
            ?.addOnSuccessListener {
                Toast.makeText(this, "Event created successfully", Toast.LENGTH_SHORT).show()
                finish()
            }
            ?.addOnFailureListener {
                Toast.makeText(this, "Failed to create event", Toast.LENGTH_SHORT).show()
            }
    }

//    private fun goToHome(){
//        homeBtn.setOnClickListener {
//            val intent = Intent(this, HomeActivity::class.java)
//            startActivity(intent)
//        }
//    }

}