package com.edwinacubillos.firebaseapplication

import android.app.Application
import androidx.room.Room

class FirebaseApplication : Application() {

    companion object {
        lateinit var database: UserDatabase
    }

    override fun onCreate() {
        super.onCreate()
        FirebaseApplication.database = Room.databaseBuilder(
            this,
            UserDatabase::class.java,
            "user-db"
        ).build()
    }
}


