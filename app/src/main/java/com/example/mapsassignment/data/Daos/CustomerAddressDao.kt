package com.example.mapsassignment.data.Daos

import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mapsassignment.data.Entities.CustomerAddress

interface CustomerAddressDao {
    @Query("select * from address")
    fun getAll():LiveData<List<CustomerAddress>>

    @Query("select * from address where id = :id")
    fun getAddressById(id:Int):LiveData<CustomerAddress>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(address: CustomerAddress)

    @Query("DELETE FROM ADDRESS")
    suspend fun deleteAll()
}