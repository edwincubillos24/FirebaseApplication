package com.edwinacubillos.firebaseapplication

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_read.view.*

class ReadFragment : Fragment() {

    private var userList: ArrayList<User> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_read, container, false)

        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("usuarios")

        root.recyclerView.setHasFixedSize(true)
        root.recyclerView.layoutManager = LinearLayoutManager(
            activity!!.applicationContext,
            RecyclerView.VERTICAL,
            false
        )
        val userAdapter = RecyclerUserAdapter(
            userList,
            activity!!.applicationContext
        )

        root.recyclerView.adapter = userAdapter

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot: DataSnapshot in dataSnapshot.children) {
                    val user: User? = snapshot.getValue(User::class.java)
                    userList.add(user!!)
                }
                userAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("value", "Failed to read value.", error.toException())
            }
        })



        return root
    }
}