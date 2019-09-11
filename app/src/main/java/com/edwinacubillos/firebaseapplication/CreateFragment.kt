package com.edwinacubillos.firebaseapplication

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_create.*
import kotlinx.android.synthetic.main.fragment_create.view.*
import java.io.ByteArrayOutputStream

class CreateFragment : Fragment() {

    private lateinit var root: View
    val REQUEST_IMAGE_CAPTURE = 1
    private lateinit var storage: FirebaseStorage

    private var id: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_create, container, false)

        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("usuarios")

        root.bSave.setOnClickListener {
            val name = eName.text.toString()
            id = eId.text.toString()
            val email = eMail.text.toString()

            saveImage(iPicture)


            val user = User(
                name,
                id,
                email,
                "https://firebasestorage.googleapis.com/v0/b/fir-application-68583.appspot.com/o/usuarios%2Fbatman.jpg?alt=media&token=4ccfe216-5568-4097-827b-d046b22763c4"
            )

            myRef.child(id).setValue(user)
                .addOnSuccessListener {

                }
                .addOnFailureListener {

                }
        }

        root.iPicture.setOnClickListener {
            takePhoto()
        }

        return root
    }

    private fun takePhoto() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(activity!!.packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            root.iPicture.setImageBitmap(imageBitmap)
        }
    }

    private fun saveImage(iPicture: ImageView?) {
        storage = FirebaseStorage.getInstance()
        val photoRef = storage.reference.child("usuarios").child("123")

        iPicture?.isDrawingCacheEnabled = true
        iPicture?.buildDrawingCache()
        val bitmap = (iPicture?.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        var uploadTask = photoRef.putBytes(data)

        uploadTask.addOnFailureListener {

            // Handle unsuccessful uploads
        }.addOnSuccessListener {
            Log.d("subida", it.metadata.toString())
            // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
            // ...
        }

    }
}