package com.example.mapsassignment.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mapsassignment.data.Daos.CustomerAddressDao
import com.example.mapsassignment.data.Entities.CustomerAddress

@Database(entities = arrayOf(CustomerAddress::class), version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun customerAddressDao() : CustomerAddressDao

    companion object{
        @Volatile
        private  var INSTANCE:AppDatabase? = null

        fun getDatabase(context: Context):AppDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(context.applicationContext,
                AppDatabase::class.java,
                "app_database").build()
                INSTANCE = instance
                return instance
            }
        }
    }
}