package com.nifeferanmi.nchat

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.nifeferanmi.nchat.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val auth : FirebaseAuth = FirebaseAuth.getInstance()
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)



        binding.loginBtn.setOnClickListener {
            if (binding.emailSignUp.text.isNotEmpty() && binding.passwordSignUp.text.isNotEmpty()){

                val email = binding.emailSignUp.text.toString()
                val password = binding.passwordSignUp.text.toString()

                logIn(email , password)

            }
            else {

                Toast.makeText(this , "fill in correct details",Toast.LENGTH_LONG).show()

            }
        }

        binding.signUpActivity.setOnClickListener {

            val intent = Intent(this , SignUpActivity::class.java)
            startActivity(intent)

        }

        binding.forgotPassword.setOnClickListener {

            val intent = Intent(this, ForgotActivity::class.java)
            startActivity(intent)

        }

    }

    private fun logIn(email: String, password: String) {

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            task ->
            if (task.isSuccessful){

                Toast.makeText(this,"account sign In successfully",Toast.LENGTH_SHORT).show()
                val intent = Intent(this , ChatActivity::class.java)
                startActivity(intent)
                finish()

            }
            else{

                Toast.makeText(this,task.exception.toString(),Toast.LENGTH_LONG).show()

            }
        }
    }

    override fun onResume() {
        super.onResume()

        val user = auth.currentUser

        if (user != null){
            val intent = Intent(this,ChatActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}