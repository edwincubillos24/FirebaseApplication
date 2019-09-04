package com.edwinacubillos.firebaseapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_create.*
import kotlinx.android.synthetic.main.fragment_create.view.*

class CreateFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_create, container, false)

        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("usuarios")


        root.bSave.setOnClickListener {
            val name = eName.text.toString()
            val id = eId.text.toString()
            val email = eMail.text.toString()

            val user = User(name, id, email)

            myRef.child(id).setValue(user)
        }

        return root
    }
}