package com.edwinacubillos.firebaseapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_update.view.*

class UpdateFragment : Fragment() {

    val database = FirebaseDatabase.getInstance()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_update, container, false)

        root.bUpdate.setOnClickListener {
            var nombre = root.eName.text.toString()
            var email = root.eMail.text.toString()
            var eid = root.eId.text.toString()

            val myRef = database.getReference("usuarios").child(eid)

            val childUpdates = HashMap<String, Any>()
            childUpdates["email"] = email
            childUpdates["name"] = nombre

            myRef.updateChildren(childUpdates)
                .addOnSuccessListener {

                }.addOnFailureListener {

                }


        }


        return root
    }
}