package com.edwinacubillos.firebaseapplication


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_delete.view.*

/**
 * A simple [Fragment] subclass.
 */
class DeleteFragment : Fragment() {

    val database = FirebaseDatabase.getInstance()
    val myRef = database.getReference("usuarios")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_delete, container, false)

        root.bDelete.setOnClickListener {
            var cedula = root.eCedula.text.toString()

            myRef.child(cedula).removeValue()
                .addOnSuccessListener {

                }.addOnFailureListener {


                }
        }

        return root

    }


}
