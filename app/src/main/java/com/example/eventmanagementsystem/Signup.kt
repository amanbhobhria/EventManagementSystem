package com.example.eventmanagementsystem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import com.example.eventmanagementsystem.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class Signup : AppCompatActivity() {
    lateinit var  firebase : FirebaseAuth
    lateinit var binding : ActivitySignupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
         firebase = FirebaseAuth.getInstance()
        val t : TextView = findViewById(R.id.alredy)
        t.setOnClickListener{
            val i = Intent(this , login::class.java)
            startActivity(i)
        }
        binding.createbtn.setOnClickListener{
            createacc()
        }
    }
    private fun createacc(){
        var name = binding.username.text.toString()
        var email = binding.email.text.toString()
        var pass  = binding.password.text.toString()
        var cpass = binding.cpassword.text.toString()

        if(name.isNotEmpty() && email.isNotEmpty() && pass.isNotEmpty() && cpass.isNotEmpty()){
            if(pass == cpass){
                firebase.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(this){
                    if (it.isSuccessful){
                        val intent = Intent(this,login::class.java)
                        startActivity(intent)
                    }
                    else{
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else {
                Toast.makeText(this, "Password and Confirm password does not match", Toast.LENGTH_SHORT).show()
            }
        }
        else Toast.makeText(this, "Please fill all the details ", Toast.LENGTH_SHORT).show()


    }
    override fun onStart() {
        super.onStart()
        if(firebase.currentUser != null){
//            startActivity(Intent(this, HomeActivity::class.java))
//            Toast.makeText(this, "Already Login", Toast.LENGTH_SHORT).show()
        }

    }
}