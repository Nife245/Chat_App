package com.nifeferanmi.nchat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter(
    var context: Context,
    var messages : ArrayList<Message>
) :RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val ITEM_SENT = 1
    val ITEM_RECEIVE = 2

    class SentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val sentText : TextView = itemView.findViewById(R.id.textViewR)

    }
    class ReceiveViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val receiveText : TextView = itemView.findViewById(R.id.textViewR)

    }

    override fun getItemViewType(position: Int): Int {

        val currentMessage = messages[position]

        if (FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderId)){
            return ITEM_SENT
        }
        else{
            return ITEM_RECEIVE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        lateinit var layout : View
        if (viewType == 1){
             layout = LayoutInflater.from(parent.context).inflate(R.layout.sent,parent,false)
            return SentViewHolder(layout)
        }
        else {
            layout = LayoutInflater.from(parent.context).inflate(R.layout.receive, parent, false)
            return ReceiveViewHolder(layout)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val current = messages[position]

        if (holder.javaClass == SentViewHolder::class.java){

            val holder = holder as SentViewHolder
            holder.sentText.text = current.message

        }
        else if (holder.javaClass == ReceiveViewHolder::class.java){

            val holder = holder as ReceiveViewHolder
            holder.receiveText.text = current.message

        }

    }

    override fun getItemCount(): Int {
        return messages.size
    }

}