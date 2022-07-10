package com.aqsl.teamupfp.Db.DAO

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aqsl.teamupfp.Model.ModelDb

@Dao
interface InterfaceDAO {
    @Query("SELECT * FROM tabel_absen")
    fun getAllHistory(): LiveData<List<ModelDb>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertData(vararg modelDb: ModelDb)

    @Query("DELETE FROM tabel_absen WHERE uid= :uid")
    fun deleteHistoryById(uid: Int)

    @Query("DELETE FROM tabel_absen")
    fun deleteAllHistory()

}