package com.nifeferanmi.nchat

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.nifeferanmi.nchat.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {

    private val auth : FirebaseAuth = FirebaseAuth.getInstance()
    lateinit var binding: ActivitySignUpBinding
    val firebaseDatabase : FirebaseDatabase = FirebaseDatabase.getInstance()
    val ref : DatabaseReference = firebaseDatabase.reference
    lateinit var sharedPref : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

            binding.signUpBtn.setOnClickListener {

                val email = binding.emailSignUp.text.toString()
                val password = binding.passwordSignUp.text.toString()
                val name = binding.username.text.toString()

                createAccount(name, email, password)

        }

    }

    private fun createAccount(name : String ,email : String , password : String) {

        auth.createUserWithEmailAndPassword(email,password).
                addOnCompleteListener {
                    task ->
                    if (task.isSuccessful){

                        auth.currentUser?.let { addUserToDatabase(name,email, it.uid) }
                        Toast.makeText(this,"account created successfully",Toast.LENGTH_SHORT).show()
                        val intent = Intent(this , MainActivity::class.java)
                        startActivity(intent)
                        finish()

                    }
                    else{
                        Toast.makeText(this , task.exception.toString(),Toast.LENGTH_LONG).show()
                    }
                }

    }

    private fun addUserToDatabase(name: String, email: String, uid: String) {

        ref.child("user").child(uid as String).setValue(Users(email,uid,name))

    }
    fun saveData(){
        val email = binding.emailSignUp.text.toString()
        val username = binding.username.text.toString()
        val password = binding.passwordSignUp.text.toString()
        sharedPref = this.getSharedPreferences("data", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString("email",email)
        editor.putString("username",username)
        editor.putString("password",password)
        editor.apply()
    }
    fun getData(){
        sharedPref = this.getSharedPreferences("data",Context.MODE_PRIVATE)
        val email = sharedPref.getString("email",null)
        val username = sharedPref.getString("username",null)
        val password = sharedPref.getString("password",null)

        binding.emailSignUp.setText(email)
        binding.passwordSignUp.setText(password)
        binding.username.setText(username)
    }

    override fun onPause() {
        saveData()
        super.onPause()
    }

    override fun onResume() {
        getData()
        super.onResume()
    }
}