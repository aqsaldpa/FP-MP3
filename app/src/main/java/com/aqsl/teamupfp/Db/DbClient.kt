package com.aqsl.teamupfp.Db

import android.content.Context
import androidx.room.Room

class DbClient private constructor(context:Context){

    var appDatabase: AppDatabase = Room.databaseBuilder(context,AppDatabase::class.java,"absensi_db")
        .fallbackToDestructiveMigration()
        .build()

    companion object{
        private var mInstance : DbClient? = null
        @JvmStatic
        @Synchronized
        fun getInstance (context: Context): DbClient?{
            if (mInstance == null){
                mInstance = DbClient(context)
            }
            return mInstance
        }
    }

}