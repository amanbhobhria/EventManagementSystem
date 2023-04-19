package com.example.eventmanagementsystem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class SpalshScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spalsh_screen)



        val h= Handler(Looper.getMainLooper())
        h.postDelayed({
            val i= Intent(this,login::class.java)
            startActivity(i)
            finish()
        },3000)



    }
}