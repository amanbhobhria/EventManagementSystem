package com.example.eventmanagementsystem

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.eventmanagementsystem.R

class ProfileUserActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_user)

        var value="user"
        var nameTxt:TextView=findViewById(R.id.userNameProfile)

        val extras = intent.extras
        if (extras != null) {
            value = extras.getString("key").toString()
        }

        nameTxt.setText(value)


    }
}