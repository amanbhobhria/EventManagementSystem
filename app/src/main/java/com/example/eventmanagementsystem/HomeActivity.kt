package com.example.eventmanagementsystem


import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eventmanagementsystem.adapter.EventsAdapter
import com.example.eventmanagementsystem.common.Common
import com.example.eventmanagementsystem.model.EventsModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*

class HomeActivity : AppCompatActivity() {



   


    var progressBar: ProgressBar? = null
    var CHANNEL_ID = "my_channel_id"
    var b=false;
    lateinit var context :Context
    val notificationId = System.currentTimeMillis().toInt()


    var firebaseDatabase: FirebaseDatabase? = null
    var reference: DatabaseReference? = null
    var recyclerView: RecyclerView? = null
    lateinit var addNewBtn: FloatingActionButton
    lateinit var openSideNavBtn: ImageButton

    var bottomNavigationView: BottomNavigationView? = null


    private val recent = "recent"
    private val upcoming = "upcoming"
    private val completed = "completed"
    private var value = "default"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

       val extras = intent.extras
        if (extras != null) {
             value = extras.getString("key").toString()
        }


        firebaseDatabase = FirebaseDatabase.getInstance()
        reference = firebaseDatabase!!.getReference("events")


        initialize()


        addNewEvent()
        bottomNavigation()
        recyclerView!!.layoutManager = LinearLayoutManager(this)
        popupnotification()
        sideNav()


    }

    private fun sideNav() {
        val navigationView = findViewById<NavigationView>(R.id.nav_menu)

        val drawer = findViewById<DrawerLayout>(R.id.my_drawer_layout)

        openSideNavBtn.setOnClickListener()
        {
            drawer.openDrawer(navigationView)
        }





        navigationView.setNavigationItemSelectedListener { item ->
            if (item.itemId == R.id.profile) {
                try {

                    Toast.makeText(this@HomeActivity, value, Toast.LENGTH_SHORT).show()
                }
                catch (e:Exception)
                {
                    Toast.makeText(this@HomeActivity, e.toString(), Toast.LENGTH_SHORT).show()
                }
            } else if (item.itemId == R.id.events) {
                Toast.makeText(this@HomeActivity, "Events", Toast.LENGTH_SHORT).show()
            } else if (item.itemId == R.id.myReg) {
                Toast.makeText(this@HomeActivity, "My Registrations", Toast.LENGTH_SHORT).show()
            } else if (item.itemId == R.id.logout) {
                Toast.makeText(this@HomeActivity, "Logout", Toast.LENGTH_SHORT).show()
            }
            false
        }
    }


    private fun initialize() {


        addNewBtn=findViewById(R.id.addNewEventBtn)
        openSideNavBtn=findViewById(R.id.openDrawerBtn)
        recyclerView = findViewById(R.id.showOnGoingRecyclerView)

        progressBar = findViewById(R.id.loadingProgressBar)
        bottomNavigationView = findViewById(R.id.bottom_nav)

        context= applicationContext


    }


    private fun addNewEvent()
    {
        addNewBtn.setOnClickListener()
        {
            val intent = Intent(this, AdminActivity::class.java)
            startActivity(intent)
        }
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



                        upcoming -> if (  eventModel.eventDate>sdf.format(d.time))
                         {
//

//                             Toast.makeText(this@HomeActivity,eventModel.eventDate+" "+sdf.format(d.time),Toast.LENGTH_SHORT).show()
                            eventModel.setEventId(sp.key)
                            list.add(eventModel)
                             b=true


                        }
                        recent -> if (eventModel.eventDate==sdf.format(d.time)
                        ) {
//                            Toast.makeText(this@HomeActivity,eventModel.eventDate+" "+sdf.format(d.time),Toast.LENGTH_SHORT).show()
                            eventModel.setEventId(sp.key)
                            list.add(eventModel)
                            b=true
                        }
                        completed -> if (  eventModel.eventDate<sdf.format(d.time))

                         {


                            try {
                                eventModel.setEventId(sp.key)
                                list.add(eventModel)
                            }
                            catch(e:Exception){
                                Toast.makeText(this@HomeActivity, e.toString(), Toast.LENGTH_SHORT).show()
                            }
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


    private fun popupnotification()
    {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel
            val name = "Channel Name"
            val descriptionText = "Channel Description"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("channel_id", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)

            // Set the CHANNEL_ID for the notification
            CHANNEL_ID = "channel_id"
        } else {
            // For older versions of Android, use a default channel ID
            CHANNEL_ID = "default_channel_id"
        }



        reference?.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {
                // handle the new child node being added here

                val title =   dataSnapshot.child("eventName").getValue().toString()
                val message = dataSnapshot.child("eventDesc").getValue().toString()
                val notification = NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .build()
                val notificationManager = NotificationManagerCompat.from(context)
                notificationManager.notify(notificationId, notification)
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, previousChildName: String?) {}

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {}

            override fun onChildMoved(dataSnapshot: DataSnapshot, previousChildName: String?) {}

            override fun onCancelled(databaseError: DatabaseError) {}


        })


    }




//

}


