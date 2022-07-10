package com.aqsl.teamupfp.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "tabel_absen")
class ModelDb : Serializable{

    @PrimaryKey(autoGenerate = true )
    @ColumnInfo(name = "uid")
    var uid = 0

    @ColumnInfo(name = "nama")
    lateinit var nama : String

    @ColumnInfo(name = "foto_selfie")
    lateinit var fotoSelfie: String

    @ColumnInfo(name = "tanggal")
    lateinit var tanggal: String

    @ColumnInfo(name = "lokasi")
    lateinit var lokasi: String

    @ColumnInfo(name = "keterangan")
    lateinit var keterangan: String
}