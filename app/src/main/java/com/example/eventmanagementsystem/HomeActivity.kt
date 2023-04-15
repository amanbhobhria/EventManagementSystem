package com.example.eventmanagementsystem

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eventmanagementsystem.adapter.EventsAdapter
import com.example.eventmanagementsystem.model.EventsModel

import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationBarView

//import com.google.firebase.auth.FirebaseAuth


import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*

class HomeActivity : AppCompatActivity() {
    private val ChannelId="New Event Channel"
    private val NotificationId= 123

    var progressBar: ProgressBar? = null
//    lateinit var firebase : FirebaseAuth

    var firebaseDatabase: FirebaseDatabase? = null
    var reference: DatabaseReference? = null
    var recyclerView: RecyclerView? = null
    lateinit var addNewBtn: FloatingActionButton

    var bottomNavigationView: BottomNavigationView? = null


    private val recent = "recent"
    private val upcoming = "upcoming"
    private val completed = "completed"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        firebaseDatabase = FirebaseDatabase.getInstance()
//        firebase =FirebaseAuth.getInstance()
        reference = firebaseDatabase!!.getReference("events")


        initialize()

//        getEventsData()
        addNewEvent()
        bottomNavigation()
        recyclerView!!.layoutManager = LinearLayoutManager(this)


    }


    private fun initialize() {


        addNewBtn=findViewById(R.id.addNewEventBtn)
        recyclerView = findViewById(R.id.showOnGoingRecyclerView)

        progressBar = findViewById(R.id.loadingProgressBar)
        bottomNavigationView = findViewById(R.id.bottom_nav)


    }


    private fun addNewEvent()
    {
        addNewBtn.setOnClickListener()
        {
            val intent = Intent(this, AdminActivity::class.java)
            startActivity(intent)
        }
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



    private fun showBookings(status: String) {
        val list: MutableList<EventsModel> = ArrayList<EventsModel>()
        reference!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                list.clear()

                for (sp in snapshot.children) {
                    val eventModel: EventsModel = sp.getValue(EventsModel::class.java)!!

                    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    val d = Calendar.getInstance()
                    when (status) {



                        upcoming -> if (eventModel.eventDate>sdf.format(d.time))
                         {
                            eventModel.setEventId(sp.key)
                            list.add(eventModel)
                        }
                        recent -> if (eventModel.eventDate==sdf.format(d.time)
                        ) {

                            eventModel.setEventId(sp.key)
                            list.add(eventModel)
                        }
                        completed -> if (eventModel.eventDate<sdf.format(d.time)

                        ) {

                            eventModel.setEventId(sp.key)
                            list.add(eventModel)
                        }
                    }
                }
                val eventsAdapter = EventsAdapter(list, this@HomeActivity)
                recyclerView!!.adapter = eventsAdapter

                    recyclerView!!.visibility = View.VISIBLE
                }


            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@HomeActivity, error.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun bottomNavigation() {
        bottomNavigationView?.setOnItemSelectedListener(NavigationBarView.OnItemSelectedListener { item: MenuItem ->
            if (item.itemId == R.id.bottom_nav_upcoming) {
                try {
                    showBookings(upcoming)

                } catch (e: Exception) {
                    Toast.makeText(this@HomeActivity, e.toString(), Toast.LENGTH_SHORT).show()
                }
                item.isChecked = true
            } else if (item.itemId == R.id.bottom_nav_recent) {
                showBookings(recent)
                item.isChecked = true
            } else if (item.itemId == R.id.bottom_nav_completed) {
                showBookings(completed)
                item.isChecked = true
            }
            false
        })
    }



//

}


