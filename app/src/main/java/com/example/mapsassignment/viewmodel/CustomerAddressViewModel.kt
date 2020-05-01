package com.example.mapsassignment.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.mapsassignment.data.AppDatabase
import com.example.mapsassignment.data.Entities.CustomerAddress
import com.example.mapsassignment.repository.AddressRepository

class CustomerAddressViewModel(application: Application) : AndroidViewModel(application){
    private val repository: AddressRepository

    val allAddress: LiveData<List<CustomerAddress>>

    init {
        val addressesDao = AppDatabase.getDatabase(application).customerAddressDao()
        repository = AddressRepository(addressesDao)
        allAddress = repository.allAddresses
    }


}