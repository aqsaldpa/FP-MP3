package com.aqsl.teamupfp.Db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aqsl.teamupfp.Db.DAO.InterfaceDAO
import com.aqsl.teamupfp.Model.ModelDb

@Database(entities = [ModelDb::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase(){
    abstract fun databaseDao(): InterfaceDAO?
}