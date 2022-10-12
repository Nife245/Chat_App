package com.nifeferanmi.nchat

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class UserAdapter(
    var context: Context,
    var users : ArrayList<Users>
) : RecyclerView.Adapter<UserAdapter.UserLayout>() {

    class UserLayout(itemView: View) : RecyclerView.ViewHolder(itemView){

        val name : TextView = itemView.findViewById(R.id.user_name)
        val container : ConstraintLayout = itemView.findViewById(R.id.user_element)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserLayout {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.user_layout,parent,false)

        return UserLayout(layout)
    }

    override fun onBindViewHolder(holder: UserLayout, position: Int) {

        val currentUser = users[position]

        holder.name.text = currentUser.name.toString()
        holder.container.setOnClickListener {
            val intent = Intent(context,ChattngActivity::class.java)
            intent.putExtra("name",currentUser.name.toString())
            intent.putExtra("uid",currentUser.uid.toString())
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return users.size
    }

}