package com.example.eventmanagementsystem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.eventmanagementsystem.common.Common

import com.google.firebase.auth.FirebaseAuth

class login : AppCompatActivity() {
    lateinit var  name :  TextView
    lateinit var pass : TextView
    lateinit var btn : TextView
    lateinit var firebase : FirebaseAuth
//     lateinit  var common : Common

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        name = findViewById(R.id.username)
        pass = findViewById(R.id.password)
        btn = findViewById(R.id.loginbtn)

       firebase = FirebaseAuth.getInstance()

        val t : TextView = findViewById(R.id.signinaa)
        t.setOnClickListener{
            val i = Intent(this , Signup::class.java)
            startActivity(i)
        }
        btn.setOnClickListener{
            login()
        }

    }
    private fun login(){

        var email = name.text.toString()
        var pass  = pass.text.toString()


        if(email.isNotEmpty()  && pass.isNotEmpty()){
//            common.username=email
            firebase.signInWithEmailAndPassword(email,pass).addOnCompleteListener(this){
                if (it.isSuccessful){
                    intent.putExtra("key", email);
                    startActivity(Intent(this, HomeActivity::class.java))
                }
                else
                    Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
            }
        }
        else Toast.makeText(this, "Please enter credentials  ", Toast.LENGTH_SHORT).show()
    }
}