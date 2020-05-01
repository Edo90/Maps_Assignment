package com.example.mapsassignment.data.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "address")
class CustomerAddress constructor(latitude: Float?, longitude: Float?, address: String){
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0

    @ColumnInfo(name = "latitude")
    var latitude:Float? = latitude

    @ColumnInfo(name = "longitude")
    var longitude:Float? = longitude

    @ColumnInfo(name = "address")
    var address:String = address
}