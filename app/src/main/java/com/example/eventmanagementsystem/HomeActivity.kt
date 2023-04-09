package com.example.eventmanagementsystem

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eventmanagementsystem.adapter.EventsAdapter
import com.example.eventmanagementsystem.model.EventsModel
import com.google.firebase.database.*

class HomeActivity : AppCompatActivity() {
    var progressBar: ProgressBar? = null


    var firebaseDatabase: FirebaseDatabase? = null
    var reference: DatabaseReference? = null
    var recyclerView: RecyclerView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        firebaseDatabase = FirebaseDatabase.getInstance()
        reference = firebaseDatabase!!.getReference("events")


        initialize()

        getEventsData()
        recyclerView!!.layoutManager = LinearLayoutManager(this)


    }


    private fun initialize() {

        recyclerView = findViewById(R.id.showOnGoingRecyclerView)

        progressBar = findViewById(R.id.loadingProgressBar)


    }



    private fun getEventsData() {
        val list: MutableList<EventsModel> = ArrayList<EventsModel>()

        reference!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                list.clear()
                for (sp in snapshot.children) {

                    //  Log.i("Users ", "onDataChange: "+sp.child("userName").getValue().toString()+" "+sp.child("fName").getValue().toString());
                    val eventModel: EventsModel = sp.getValue(EventsModel::class.java)!!
                    eventModel.setEventId(sp.key)
                    Toast.makeText(this@HomeActivity,eventModel.eventId+" "+eventModel.eventName,Toast.LENGTH_SHORT).show()
                    Log.i("TAG", "onDataChange: " + sp.key)
                    list.add(eventModel)

                }
                val eventsAdapter = EventsAdapter(list, this@HomeActivity)
                recyclerView!!.adapter = eventsAdapter
                progressBar!!.visibility= View.GONE
                recyclerView!!.visibility=View.VISIBLE

                //pass list to adapter
            }

            override fun onCancelled(error: DatabaseError) {}
        })

    }
}

