package com.nifeferanmi.nchat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.nifeferanmi.nchat.databinding.ActivityChatBinding
import com.nifeferanmi.nchat.databinding.ActivityChattngBinding

class ChattngActivity : AppCompatActivity() {

    lateinit var bind : ActivityChattngBinding
    val messageList = ArrayList<Message>()
    lateinit var adapter : MessageAdapter
    val firebaseDatabase : FirebaseDatabase = FirebaseDatabase.getInstance()
    val ref : DatabaseReference = firebaseDatabase.reference

    var receiverRoom : String? = null
    var senderRoom : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityChattngBinding.inflate(layoutInflater)
        val view = bind.root
        setContentView(view)

        val name = intent.getStringExtra("name")
        val receiverUid = intent.getStringExtra("uid")
        val senderUid = FirebaseAuth.getInstance().currentUser?.uid

        receiverRoom = senderUid + receiverUid
        senderRoom = receiverUid + senderUid

        supportActionBar?.title = name

        adapter = MessageAdapter(this,messageList)
        bind.chat.layoutManager = LinearLayoutManager(this)
        bind.chat.adapter = adapter

        ref.child("chats").child(senderRoom!!).child("messages").
            addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {

                    messageList.clear()

                    for (data in snapshot.children){
                        val message = data.getValue(Message::class.java)

                        if (message != null) {
                            messageList.add(message)
                        }
                    }
                    adapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })

        bind.imageButton.setOnClickListener {

            val message = bind.editTextTextMultiLine.text.toString()
            val messageObject = Message(message,senderUid)

            ref.child("chats").child(senderRoom!!).child("messages").push()
                .setValue(messageObject).addOnSuccessListener {
                    ref.child("chats").child(receiverRoom!!).child("messages").push()
                        .setValue(messageObject)
                }
            bind.editTextTextMultiLine.setText("")

        }

    }
}