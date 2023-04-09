package com.example.eventmanagementsystem.adapter


import android.content.Context

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eventmanagementsystem.R

import com.example.eventmanagementsystem.model.EventsModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase



class EventsAdapter(list: List<EventsModel>, context: Context) :
    RecyclerView.Adapter<EventsAdapter.EventsViewHolder>() {
    var list: List<EventsModel>
    var context: Context
    var firebaseDatabase: FirebaseDatabase
    var reference: DatabaseReference

    init {
        this.list = list
        this.context = context
        firebaseDatabase = FirebaseDatabase.getInstance()
        reference = firebaseDatabase.getReference("events")
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EventsViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.events_show_template, parent, false)
        return EventsViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: EventsViewHolder,
        position: Int
    ) {

        holder.eventName.text = list[position].eventName
        holder.eventId.text = list[position].eventId
        holder.eventVenue.text = list[position].eventVenue
        holder.eventDate.text = list[position].eventDate
        holder.eventDesc.text = list[position].eventDesc




    }

    override fun getItemCount(): Int {
        return list.size
    }

    class EventsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var item: LinearLayout
        var eventName: TextView
        var eventDate: TextView
        var eventVenue: TextView
        var eventId: TextView
        var eventDesc: TextView


        init {
            item = itemView.findViewById(R.id.eventsPreviewTemplate)
            eventName    = itemView.findViewById(R.id.eventNametxt)
            eventDate    = itemView.findViewById(R.id.dateTxt)
            eventVenue    = itemView.findViewById(R.id.venueTxt)
            eventId    = itemView.findViewById(R.id.eventIdTxt)
            eventDesc    = itemView.findViewById(R.id.eventDetailTxt)
        }
    }
}