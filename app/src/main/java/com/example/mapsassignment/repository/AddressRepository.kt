package com.example.mapsassignment.repository

import androidx.lifecycle.LiveData
import com.example.mapsassignment.data.Daos.CustomerAddressDao
import com.example.mapsassignment.data.Entities.CustomerAddress

class AddressRepository(private val customerAddressDao: CustomerAddressDao){
    val allAddresses: LiveData<List<CustomerAddress>> = customerAddressDao.getAll()

    fun addressby(id:Int):LiveData<CustomerAddress>{
        return customerAddressDao.getAddressById(id)
    }

    suspend fun insert(customerAddress: CustomerAddress){
        customerAddressDao.insert(customerAddress)
    }

    suspend fun deleteAll(){
        customerAddressDao.deleteAll()
    }
}