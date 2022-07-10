package com.aqsl.teamupfp.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.aqsl.teamupfp.Db.DAO.InterfaceDAO
import com.aqsl.teamupfp.Db.DbClient.Companion.getInstance
import com.aqsl.teamupfp.Model.ModelDb
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable

import io.reactivex.rxjava3.schedulers.Schedulers


class AbsenViewModel(application: Application) : AndroidViewModel(application) {
    var databaseDAO: InterfaceDAO? = getInstance(application)?.appDatabase?.databaseDao()

    fun addDataAbsen(
        foto : String, nama: String,
        tanggal : String, lokasi : String, keterangan : String){
        Completable.fromAction{
            val modelDatabase = ModelDb()
            modelDatabase.fotoSelfie = foto
            modelDatabase.nama = nama
            modelDatabase.tanggal = tanggal
            modelDatabase.lokasi = lokasi
            modelDatabase.keterangan = keterangan
            databaseDAO?.insertData(modelDatabase)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }
}