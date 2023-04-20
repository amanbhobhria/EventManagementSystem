package com.example.eventmanagementsystem


import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eventmanagementsystem.adapter.EventsAdapter
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
    lateinit var  drawer: DrawerLayout
    lateinit var  navigationView:NavigationView

    var bottomNavigationView: BottomNavigationView? = null


    private val recent = "recent"
    private val upcoming = "upcoming"
    private val completed = "completed"
    private var value="user"


    val SHARED_PREFS = "shared_prefs"

    // key for storing email.
    val MESSAGE_KEY = "useremail"

    // variable for shared preferences.




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        initialize()


       val extras = intent.extras
        if (extras != null) {
             value = extras.getString("key").toString()
        }


        var toolbar: Toolbar = findViewById(R.id.toolbar)
        var actionBarDrawerToggle = ActionBarDrawerToggle(this,drawer,toolbar,
            R.string.navigation_drawer_open,R.string.navigation_drawer_close)
        drawer.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()




        firebaseDatabase = FirebaseDatabase.getInstance()
        reference = firebaseDatabase!!.getReference("events")





        addNewEvent()
        bottomNavigation()
        recyclerView!!.layoutManager = LinearLayoutManager(this)
        popupnotification()
        sideNav()

        saveSharedPref()





    }

    @SuppressLint("CommitPrefEdits")
    private fun saveSharedPref() {
        val sharedPreference =  getSharedPreferences("PREFERENCE_USERS",Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putString(MESSAGE_KEY, value);
        editor.commit()


    }

    private fun sideNav() {







        navigationView.setNavigationItemSelectedListener { item ->
            if (item.itemId == R.id.profile) {
                try {
                    if(value=="admin@gmail.com")
                    {
                        val intent = Intent(this, ProfileAdminActivity::class.java)
                        startActivity(intent)
                    }
                    else{

                        val intent = Intent(this, ProfileUserActivity::class.java)
                        intent.putExtra("key", value);
                        startActivity(intent)
                    }



                }
                catch (e:Exception)
                {
                    Toast.makeText(this@HomeActivity, e.toString(), Toast.LENGTH_SHORT).show()
                }
            } else if (item.itemId == R.id.events) {
                drawer.closeDrawer(navigationView)
            } else if (item.itemId == R.id.myReg) {
                val intent = Intent(this, MyRegistrationsActivity::class.java)
                startActivity(intent)
              Toast.makeText(this@HomeActivity, "My Registrations", Toast.LENGTH_SHORT).show()
            }
            else if (item.itemId == R.id.shareApp) {

                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, "This is my text to send.")
                    type = "text/plain"
                }

                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)



            }


            else if (item.itemId == R.id.logout) {


                try {
                    val intent = Intent(this, login::class.java)
                    startActivity(intent)
                    Toast.makeText(this@HomeActivity, "Logout Successfully", Toast.LENGTH_SHORT)
                        .show()
                }
                catch (e:Exception)
                {
                    Toast.makeText(this@HomeActivity, e.toString(), Toast.LENGTH_SHORT)
                        .show()
                }
            }
            false
        }
    }


    private fun initialize() {


        addNewBtn=findViewById(R.id.addNewEventBtn)
        recyclerView = findViewById(R.id.showOnGoingRecyclerView)

        progressBar = findViewById(R.id.loadingProgressBar)
        bottomNavigationView = findViewById(R.id.bottom_nav)

        context= applicationContext

        drawer = findViewById(R.id.my_drawer_layout)
        navigationView = findViewById(R.id.nav_menu)





    }


    private fun addNewEvent()


    {

        if(value=="admin@gmail.com")
        {
            addNewBtn.visibility=View.VISIBLE
        }
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


}


