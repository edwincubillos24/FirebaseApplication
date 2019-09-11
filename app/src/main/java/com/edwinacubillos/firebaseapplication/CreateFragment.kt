package com.edwinacubillos.firebaseapplication

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.fragment_create.*
import kotlinx.android.synthetic.main.fragment_create.view.*
import java.io.ByteArrayOutputStream

class CreateFragment : Fragment() {

    private lateinit var root: View
    val REQUEST_IMAGE_CAPTURE = 1

    private var id: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_create, container, false)

        root.bSave.setOnClickListener {
                saveImage()
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

    private fun saveImage() {
        val storage = FirebaseStorage.getInstance()
        val photoRef = storage.reference.child("usuarios").child(eId.text.toString())

        root.iPicture?.isDrawingCacheEnabled = true
        root.iPicture?.buildDrawingCache()
        val bitmap = (root.iPicture?.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        var uploadTask = photoRef.putBytes(data)

        val urlTask = uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            return@Continuation photoRef.downloadUrl
        }).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downloadUri = task.result
                Log.d("Url", downloadUri.toString())
                saveUser(downloadUri.toString())
            } else {
            }
        }
    }

    private fun saveUser(urlFoto: String) {
        val name = eName.text.toString()
        val id = eId.text.toString()
        val email = eMail.text.toString()

        val user = User(
            name,
            id,
            email,
            urlFoto
        )

        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("usuarios")

        myRef.child(id).setValue(user)
    }
}