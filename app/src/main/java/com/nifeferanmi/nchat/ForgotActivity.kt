package com.nifeferanmi.nchat

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.nifeferanmi.nchat.databinding.ActivityForgotBinding

class ForgotActivity : AppCompatActivity() {

    val auth : FirebaseAuth = FirebaseAuth.getInstance()
    lateinit var binding: ActivityForgotBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.recoverPassword.setOnClickListener {

            val email = binding.emailSignUp2.text.toString()

            if (binding.emailSignUp2.text.isNotEmpty()) {
                recoverPassword(email)
            }
            else{
                Toast.makeText(this,"fill in correct email",Toast.LENGTH_LONG).show()
            }
        }

    }

    private fun recoverPassword(email: String) {

        auth.sendPasswordResetEmail(email).addOnCompleteListener {
            task ->
            if (task.isSuccessful){

                Toast.makeText(this,"check your email for password reset link",Toast.LENGTH_LONG).show()
                val intent = Intent(this , MainActivity::class.java)
                startActivity(intent)
                finish()

            }
            else{
                Toast.makeText(this,task.exception.toString(),Toast.LENGTH_LONG).show()
            }
        }

    }
}