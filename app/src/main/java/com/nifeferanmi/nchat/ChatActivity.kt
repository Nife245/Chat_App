package com.nifeferanmi.nchat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.nifeferanmi.nchat.databinding.ActivityChatBinding


class ChatActivity : AppCompatActivity() {

    lateinit var adapter : UserAdapter
    lateinit var binding: ActivityChatBinding
    private val users = ArrayList<Users>()
    private val auth : FirebaseAuth = FirebaseAuth.getInstance()
    private val database : FirebaseDatabase = FirebaseDatabase.getInstance()
    private val ref : DatabaseReference = database.reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = UserAdapter(this,users)
        binding.recyclerView.adapter = adapter

        ref.child("user").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                users.clear()

                for(data in snapshot.children){

                    val currentUser = data.getValue(Users::class.java)
                    if (currentUser != null) {
                        if (auth.currentUser?.uid == currentUser.uid){
                            users.remove(currentUser)
                        }
                        else{
                        users.add(currentUser)
                        }
                    }
                    adapter.notifyDataSetChanged()
                }

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){

            R.id.signout -> {
                auth.signOut()
                val user = auth.currentUser

                if (user == null){
                    val intent = Intent(this,MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }
}