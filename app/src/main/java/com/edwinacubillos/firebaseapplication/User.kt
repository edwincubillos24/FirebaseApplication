package com.edwinacubillos.firebaseapplication

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "user_entity")
class User (
    @PrimaryKey
    var id: String,
    var name: String,
    var email:String,
    var urlfoto: String
) {
    @Ignore
    constructor () : this ("","","","")
}
