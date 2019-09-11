package com.edwinacubillos.firebaseapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.user_item.view.*

class RecyclerUserAdapter : RecyclerView.Adapter<RecyclerUserAdapter.UserViewHolder> {

    private var listUser: ArrayList<User> = ArrayList()
    private var context: Context? = null

    constructor(listUser: ArrayList<User>, context: Context) {
        this.listUser = listUser
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.user_item, parent, false)
        return UserViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        var user = listUser[position]
        holder.loadItem(user)
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun loadItem(user: User) {
            itemView.tName.text = user.name
            itemView.tId.text = user.id
            Picasso.get().load(user.urlfoto).into(itemView.iPicture)
        }
    }
}